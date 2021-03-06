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

    private HashMap<Integer, Robot> robotMap;
    private HashMap<Integer, String> playerMap;

    private CardDeck deck;

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
                case "done": //Client is done simulating
                    nDoneSimulating++;
                    if(nDoneSimulating == nPlayers){
                        handOutCards();
                        nDoneSimulating = 0;
                    }
                    break;
                case "username": //Client registers its username with the server
                    playerMap.put(connection.getID(),split[1]);
                    Payload users = assembleUserListPacket();
                    server.sendToAllTCP(users);
                    break;
                case "ready": //Client is ready for the game to be launched
                    nReady++;
                    if(nReady == nPlayers){
                        launchGame();
                    }
                    break;
                case "doneloading": //Client is done loading the gamescreen
                    nDoneLoading++;
                    if(nDoneLoading >= nPlayers){ //assign id's
                        ArrayList<Robot> robots = Map.getInstance().getRobotList();
                        for(Robot r : robots){
                            robotMap.put(r.getID(),r); //Might not need the robot map
                        }
                        handOutCards();
                    }
                    break;
                case "getwinner": //Client is requesting the username of the winner
                    Payload reply = new Payload();
                    for(Robot r : robotMap.values()){
                        if(r.isWinner()){
                            reply.message = "winner " + playerMap.get(r.getID());
                            server.sendToAllTCP(reply);
                        }
                    }
                    break;
                case "powerdown": //Client announces that it's powerdown button has been pressed
                    Payload notification = new Payload();
                    notification.message = "powerdown " + playerMap.get(connection.getID());
                    server.sendToAllExceptTCP(connection.getID(),notification);
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

    /**
     * Collects all the usernames of the clients connected to the server
     * and creates a payload
     * @return payload containing keyword and all the users
     */
    private Payload assembleUserListPacket(){
        Payload users = new Payload();
        StringBuilder userList = new StringBuilder();
        userList.append("users ");
        for(String user : playerMap.values()){
            userList.append(user);
            userList.append(" ");
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
        System.out.println("Connection id: " + c.getID() + " Robot id: " + packet.id);
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
        System.out.print("Client sent: programming ");
        for(int i = 0; i < priorities.length; i++){
            System.out.print(priorities[i] + " ");
            ICard card = deck.getCard(priorities[i]);
            robot.setProgrammedCard(i, card);
        }
        System.out.println("");
    }


    /**
     * Used at the start of the round when distributing cards to the clients
     */
    private void handOutCards(){
        for(Robot r : robotMap.values()){
            r.wipeSlots(r.getProgrammedCards());
        }
        deck.reset();
        for(Connection c : server.getConnections()){
            ArrayList<ICard> cards = deck.getCards(9);
            String message = "cards ";
            for(ICard card : cards){
                message += card.getPoint() + " ";
            }
            Payload payload = new Payload();
            payload.message = message;
            c.sendTCP(payload);
        }
        deck.reset();
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
