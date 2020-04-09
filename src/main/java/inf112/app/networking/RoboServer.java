package inf112.app.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import inf112.app.cards.CardDeck;
import inf112.app.cards.ICard;
import inf112.app.map.Map;
import inf112.app.map.Position;
import inf112.app.networking.packets.Payload;
import inf112.app.networking.packets.RobotListPacket;
import inf112.app.objects.Robot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RoboServer extends Listener {
    private static final int MAX_PLAYER_AMOUNT = 8;
    private int nPlayers;
    private static Server server;
    private static int tcpPort = 10801;

    private ServerState state;
    private boolean terminated = false;

    private ObjectSpace objectSpace;

    private HashMap<Integer, Robot> robotMap;

    private Map map;
    private CardDeck deck;
    private HashMap<Integer,ICard> usedCards;

    private String[] robotNames = new String[]{"","","","","","","",""};
    private Position[] spawnPoints = new Position[MAX_PLAYER_AMOUNT];

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

        ObjectSpace.registerClasses(server.getKryo());
        objectSpace = new ObjectSpace();

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
        objectSpace.removeConnection(connection);
        nPlayers--;
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof Payload) {
            Payload p = (Payload) object;
            System.out.println(p.message);
            switch(p.message){
                case "q":
                    terminated = true;
                    server.stop();
                    System.exit(0);
                default: assert true; //do nothing
            }
        }
    }

    private void interpretPayload(Connection connection, Payload payload){
        String[] split = payload.message.split(" ");
        if(split.length == 0){
            System.out.println("Empty payload");
            return;
        }

        try {
            switch (split[0].toLowerCase()) {
                case "prog":
                    registerProgramming(connection, Arrays.copyOfRange(split,1,split.length));
                    break;
                case "rem":
                    registerUnusedCards(connection, Arrays.copyOfRange(split,1,split.length));
                default:
                    break;
            }
        } catch (NumberFormatException e){
            System.out.println("Move message contains invalid id\n" + e.getMessage());
            connection.close();
            return;
        }
    }

    /**
     * Used when robots have locked in their programming and pass it to the server
     * @param connection
     * @param priorities
     */
    private void registerProgramming(Connection connection, String[] priorities){
        Robot robot = robotMap.get(connection.getID());
        for(int i = 0; i < priorities.length; i++){
            try {
                ICard card = usedCards.get(Integer.parseInt(priorities[i]));
                robot.setProgrammedCard(i, card);
            } catch (NumberFormatException e){
                System.out.println("Couldn't parse priority\n" + e.getMessage());
            }
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
        for(Connection c : server.getConnections()){
            // # TODO : Handle empty card deck
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

    private void registerNewConnection(Connection connection){
        objectSpace.addConnection(connection);

        Payload reply = new Payload();
        reply.message = "id " + connection.getID();
        connection.sendTCP(reply);

        nPlayers++;
    }

    private void launchGame(String mapName){
        state = ServerState.GAME;
        //Create and process robots, assign spawn points
        Robot[] robotList = new Robot[nPlayers];
        int i = 0;
        for(Connection c : server.getConnections()){
            Robot robot = new Robot(spawnPoints[i],robotNames[i]);
            robotMap.put(c.getID(),robot);
            robot.assignID(c.getID());
            robotList[i++] = robot;
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
