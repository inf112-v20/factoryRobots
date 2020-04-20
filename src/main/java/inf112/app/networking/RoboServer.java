package inf112.app.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import inf112.app.cards.CardDeck;
import inf112.app.cards.ICard;
import inf112.app.map.Position;
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
    private int nPlayers;
    private int nDoneSimulating;
    private static Server server;
    private static int tcpPort = 10801;

    private ServerState state;
    private boolean terminated = false;

    private HashMap<Integer, Robot> robotMap;

    private CardDeck deck;
    private HashMap<Integer,ICard> usedCards;

    private String[] robotNames = new String[]{"1","2","3","4","5","6","7","8"};
    private Position[] spawnPoints = new Position[MAX_PLAYER_AMOUNT]; // #TODO: get spawnpoints

    public RoboServer() {
        server = new Server();
        System.out.println("Launching server..");

        server.getKryo().register(Payload.class);
        server.getKryo().register(RobotListPacket.class);

        try {
            server.bind(tcpPort);
        } catch (IOException e) {
            //Should never happen
            System.out.println("Invalid port, binding the server failed:\n" + e.getMessage());
        }

        server.start();

        server.addListener(this);
        nPlayers = 0;
        nDoneSimulating = 0;

        robotMap = new HashMap<>(MAX_PLAYER_AMOUNT);

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
            reply.message = "Successfully joined the server!";
            connection.sendTCP(reply);
        } else {
            reply.message = nPlayers < MAX_PLAYER_AMOUNT ?
                    "Server unavailable, game already running" : "Server is full";
            connection.sendTCP(reply);
            connection.close();
        }
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("A client disconnected");
        nPlayers--;
        if(state == ServerState.GAME){
            Payload playerDisc = new Payload();
            playerDisc.message = "disc " + connection.getID();
            server.sendToAllTCP(playerDisc);
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
                default:
                    System.out.println("Payload from client " + connection.getID() + ": " + payload.message);
                    break;
            }
        } catch (NumberFormatException e){
            System.out.println("Rem message contains strings that can't be parsed\n" + e.getMessage());
            connection.close();
        }
    }
/* Might not need this
    private RobotStatePacket assembleRobotStatePacket(Robot r) {
        RobotStatePacket packet = new RobotStatePacket();
        packet.id = r.getID();
        packet.powerdownNextRound = r.getPowerDownNextRound();
        int[] priorities = new int[5];
        int i = 0;
        for(CardSlot slot : r.getProgrammedCards()){
            if(slot.getCard() != null){
                priorities[i] = slot.getCard().getPoint();
            }
            i++;
        }
        packet.programmedCards = priorities;
        return packet;
    } */

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
        server.sendToAllTCP(packet);
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
        for(Connection c : server.getConnections()){
            ArrayList<ICard> cards = deck.getCards(9);
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
     * @param mapName Name of the map
     */
    private void launchGame(String mapName){
        state = ServerState.GAME;
        //Create and process robots, assign spawn points
        ArrayList<Robot> robotList = new ArrayList<>(nPlayers);
        int i = 0;
        for(Connection c : server.getConnections()){
            Robot robot = new Robot(spawnPoints[i],robotNames[i]);
            robotMap.put(c.getID(),robot);
            robot.assignID(c.getID());
            robotList.add(robot);
        }
        //send mapname to all clients so they can load
        Payload mapInfo = new Payload();
        mapInfo.message = "map " + mapName;
        server.sendToAllTCP(mapInfo);

        //send list of robots
        RobotListPacket robots = new RobotListPacket();
        robots.list = robotList;
        server.sendToAllTCP(robots);
    }

    /**
     * Used for testing
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        RoboServer serv = new RoboServer();
        while(true){
            if(serv.terminated){
                break;
            }
        }
    }

    public enum ServerState{
        LOBBY, GAME
    }
}
