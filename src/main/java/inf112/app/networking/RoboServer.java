package inf112.app.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import inf112.app.cards.CardDeck;
import inf112.app.map.Map;
import inf112.app.map.Position;
import inf112.app.networking.packets.Payload;
import inf112.app.networking.packets.RobotListPacket;
import inf112.app.networking.packets.RobotPacket;
import inf112.app.objects.Robot;

import java.io.IOException;
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
    private CardDeck deck = new CardDeck();

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
                default:
                    break;
            }
        } catch (NumberFormatException e){
            System.out.println("Move message contains invalid id\n" + e.getMessage());
            connection.close();
            return;
        }
    }

    private boolean updateRobot(Connection connection, RobotPacket robot){
        Robot unwrapped = robot.robot;
        if(unwrapped.getID() == connection.getID()){
            System.out.println("Robot doesn't belong to the client");
            return false;
        }
        if(robotMap.get(connection.getID()) == null){
            System.out.println("Robot not registered");
            return false;
        }
        robotMap.put(connection.getID(),unwrapped);
        return true;
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
/*
    private void processNewRobot(Connection connection, Robot r){
        map.registerRobot(r);
        //Registering connection and remote robot
        objectSpace.register(r.getID(),r);
        //Assign the client robot it's ID as well
        ObjectSpace.getRemoteObject(connection,r.getID(),Robot.class).assignID(r.getID());
    } */

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
