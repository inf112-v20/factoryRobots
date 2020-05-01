package inf112.app.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import inf112.app.cards.CardDeck;
import inf112.app.cards.ICard;
import inf112.app.game.RoboRally;
import inf112.app.map.Map;
import inf112.app.networking.packets.Payload;
import inf112.app.networking.packets.RobotListPacket;
import inf112.app.networking.packets.RobotStatePacket;
import inf112.app.objects.Robot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RoboServer extends Listener {
    private static final int MAX_PLAYER_AMOUNT = 8;
    private final RoboRally game;

    private int nPlayers;
    private int nDoneSimulating;
    private int nReady;
    private int nDoneLoading;

    private static Server server;
    private static int tcpPort = 10801;

    private ServerState state;
    private boolean terminated = false;

    private HashMap<Integer, Robot> robotMap;
    private HashMap<Integer, String> playerMap;

    private CardDeck deck;
    private HashMap<Integer,ICard> usedCards;

    public RoboServer(final RoboRally game) {
        this.game = game;
        server = new Server();
        System.out.println("Launching server..");

        server.getKryo().register(Payload.class);
        server.getKryo().register(RobotListPacket.class);
        server.getKryo().register(RobotStatePacket.class);
        server.getKryo().register(int[].class);

        try {
            server.bind(tcpPort);
        } catch (IOException e) {
            //Should never happen
            System.out.println("Binding the server failed or " +
                    "obtaining local ip failed:\n" + e.getMessage());
        }


        server.start();

        server.addListener(this);
        nPlayers = 0;
        nDoneSimulating = 0;
        nDoneLoading = 0;

        robotMap = new HashMap<>(MAX_PLAYER_AMOUNT);
        playerMap = new HashMap<>(MAX_PLAYER_AMOUNT);

        state = ServerState.LOBBY;
        deck = new CardDeck();
        usedCards = new HashMap<>(deck.getSize());

    }

    @Override
    public void connected(Connection connection) {
        Payload reply = new Payload();
        //Need to register nevertheless as disconnect can't distinguish accepted and rejected connections
        registerNewConnection(connection);
        //Only accept if not full and game hasn't started
        if(state == ServerState.LOBBY && nPlayers < MAX_PLAYER_AMOUNT){
            reply.message = "success";
            connection.sendTCP(reply);
        } else {
            reply.message = nPlayers < MAX_PLAYER_AMOUNT ?
                    "running" : "full";
            connection.sendTCP(reply);
            connection.close();
        }
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("A client disconnected");
        nPlayers--;
        robotMap.remove(connection.getID());
        playerMap.remove(connection.getID());
        if(state == ServerState.GAME){
            Payload playerDisc = new Payload();
            playerDisc.message = "disc " + connection.getID();
            server.sendToAllTCP(playerDisc);
        } else if (state == ServerState.LOBBY){
            Payload users = assembleUserListPacket();
            server.sendToAllTCP(users);
        }
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof Payload) {
            interpretPayload(connection, (Payload) object);
        } else if (object instanceof RobotStatePacket){
            interpretRobotState(connection, (RobotStatePacket) object);
        }
    }

    /**
     * Lookup method for the roborally protocol
     * Used whenever a payload object is sent. Message contains a keyword
     * and following information based on the keyword
     * @param connection payload is received from
     * @param payload containing the information
     */
    private void interpretPayload(Connection connection, Payload payload){
        String[] split = payload.message.split(" ");
        if(split.length == 0){
            System.out.println("Empty payload");
            return;
        }
        System.out.println("Client sent: " + payload.message); //For debugging TODO remove
        try {
            switch (split[0].toLowerCase()) {
                case "rem":
                    registerUnusedCards(connection, Arrays.copyOfRange(split,1,split.length));
                    break;
                case "done":
                    nDoneSimulating++;
                    if(nDoneSimulating == nPlayers){
                        handOutCards();
                        nDoneSimulating = 0;
                    }
                    break;
                case "username":
                    playerMap.put(connection.getID(),split[1]);
                    Payload users = assembleUserListPacket();
                    server.sendToAllTCP(users);
                    break;
                case "ready":
                    nReady++;
                    if(nReady == nPlayers){
                        launchGame();
                    }
                    break;
                case "unready":
                    nReady--;
                    break;
                case "doneloading":
                    nDoneLoading++;
                    if(nDoneLoading >= nPlayers){ //assign id's
                        ArrayList<Robot> robots = Map.getInstance().getRobotList();
                        for(Robot r : robots){
                            robotMap.put(r.getID(),r); //Might not need the robot map
                        }
                        handOutCards();
                    }
                    break;
                case "getwinner":
                    Payload reply = new Payload();
                    for(Robot r : robotMap.values()){
                        if(r.isWinner()){
                            reply.message = "winner " + playerMap.get(r.getID());
                            server.sendToAllTCP(reply);
                        }
                    }
                    break;
                default:
                    System.out.println("Payload from client " + connection.getID() + ": " + payload.message);
                    break;
            }
        } catch (NumberFormatException e){
            System.out.println("Rem message contains strings that can't be parsed\n" + e.getMessage());
            connection.close();
        }
    }

    private Payload assembleUserListPacket(){
        Payload users = new Payload();
        StringBuilder userList = new StringBuilder();
        userList.append("users ");
        for(String user : playerMap.values()){
            userList.append(user + " ");
        }
        users.message = userList.toString();
        return users;
    }

    /**
     * Used to decode the information about a players choice
     * and apply it to the servers representation of that robot.
     * It also forwards this information to all the other connected clients
     * @param c Connection the information is received from
     * @param packet The packet carrying the information about the robots state
     */
    private void interpretRobotState(Connection c, RobotStatePacket packet){
        if(c.getID() != packet.id){
            System.out.println("Client trying to manipulate robot that it doesn't own\nClosing connection");
            c.close();
            return;
        }
        Robot r = robotMap.get(packet.id);
        r.setPowerDownNextRound(packet.powerdownNextRound);
        registerProgramming(r,packet.programmedCards);
        server.sendToAllExceptTCP(c.getID(),packet);
    }

    /**
     * Used when robots have locked in their programming and pass it to the server
     * @param priorities
     */
    private void registerProgramming(Robot robot, int[] priorities){
        for(int i = 0; i < priorities.length; i++){
            ICard card = usedCards.get(priorities[i]);
            robot.setProgrammedCard(i, card);
        }
    }

    /**
     * Used when robots have passed their programming and want to return
     * the cards remaining in their possession
     * @param connection
     * @param priorities
     */
    private void registerUnusedCards(Connection connection, String[] priorities){
        for(String s : priorities){
            int priority = Integer.parseInt(s);
            ICard card = usedCards.get(priority);
            if(card == null){
                System.out.println("Priority doesn't match, possible cheater");
                connection.close();
                return;
            }
            deck.addCard(card);
            usedCards.remove(priority);
        }
    }

    /**
     * Used at the start of the round when distributing cards to the clients
     */
    private void handOutCards(){
        for(ICard card : usedCards.values()){
            deck.addCard(card);
        }
        usedCards.clear();
        deck.shuffle();
        for(Connection c : server.getConnections()){
            ArrayList<ICard> cards = deck.getCards(9-robotMap.get(c.getID()).getDamageTokens());
            String message = "cards ";
            for(ICard card : cards){
                usedCards.put(card.getPoint(),card);
                message += card.getPoint() + " ";
            }
            Payload payload = new Payload();
            payload.message = message;
            c.sendTCP(payload);
        }
    }


    @Override
    public void idle(Connection connection) {
    }

    /**
     * Used when a new client connects to assign the client an ID
     * and update the server state
     * @param connection of the client that just connected
     */
    private void registerNewConnection(Connection connection){
        Payload reply = new Payload();
        reply.message = "id " + connection.getID();
        connection.sendTCP(reply);

        nPlayers++;
    }

    /**
     * Called when the host wants to launch the game
     * Creates robots for the users and sends info about the maps
     * as well as the robots
     */
    public void launchGame(){
        state = ServerState.GAME;
        //send all player ids to clients
        Payload ids = new Payload();
        ids.message = "ids ";
        for(Connection c : server.getConnections()){
            ids.message = ids.message + c.getID() + " ";
        }
        server.sendToAllTCP(ids);

        //send mapname to all clients so they can load
        Payload mapInfo = new Payload();
        mapInfo.message = "map " + game.getMapName() + " " + nPlayers;
        server.sendToAllTCP(mapInfo);

    }

   /* public void buildAndSendRobotList(){
        //Create and process robots, assign spawn points
        ArrayList<Robot> robotList = new ArrayList<>(nPlayers);
        int i = 0;
        for(Connection c : server.getConnections()){
            Robot robot = new Robot(spawnPoints[i],robotNames[i]);
            robotMap.put(c.getID(),robot);
            robot.assignID(c.getID());
            robotList.add(robot);
        }
        //send list of robots
        RobotListPacket robots = new RobotListPacket();
        robots.list = robotList;
        server.sendToAllTCP(robots);
    } */

    /**
     * DO NOT USE THIS
     * USE {@link RoboRally#shutdownServer()}
     */
    public void shutdown(){
        Payload termination = new Payload();
        termination.message = "shutdown";
        server.sendToAllTCP(termination);
        server.stop();
    }

    public enum ServerState{
        LOBBY, GAME
    }
}
