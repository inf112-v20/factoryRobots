package inf112.app.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import inf112.app.map.Map;
import inf112.app.objects.Robot;

import java.io.IOException;

public class RoboServer extends Listener {
    private static final int MAX_PLAYER_AMOUNT = 8;
    private int nPlayers;
    private static Server server;
    private static int tcpPort = 10801;

    private ServerState state;
    private boolean terminated = false;
    private int nextAvailableRobotID;

    private ObjectSpace objectSpace;
    private Connection[] clients;

    private Map map;

    public RoboServer() {
        server = new Server();
        System.out.println("Launching server..");

        server.getKryo().register(Payload.class);
        server.getKryo().register(Robot.class);

        try {
            server.bind(tcpPort);
        } catch (IOException e) {
            System.out.println("Invalid port, binding the server failed:\n" + e.getMessage());
        }


        server.start();

        server.addListener(this);
       // map = Map.getInstance();
        nextAvailableRobotID = 0;
        nPlayers = 8;

        ObjectSpace.registerClasses(server.getKryo());
        objectSpace = new ObjectSpace();
        clients = new Connection[MAX_PLAYER_AMOUNT];
        state = ServerState.LOBBY;
    }

    @Override
    public void connected(Connection connection) {
        Payload reply = new Payload();
        //Only accept if not full and game hasn't started
        if(state == ServerState.LOBBY && nPlayers < MAX_PLAYER_AMOUNT){
            registerNewConnection(connection);
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
                    break;
                default: assert true; //do nothing
            }
        } else if (object instanceof Robot){ //Rather assign the clients a robot when sending the robotlist
            if (state == ServerState.LOBBY){
                Robot r = (Robot) object;
                processNewRobot(connection,r);
            } else {
                Payload reply = new Payload();
                reply.message = "Can not register new Robot at this time";
                connection.sendTCP(reply);
            }
        }
    }


    @Override
    public void idle(Connection connection) {
    }

    private void registerNewConnection(Connection connection){
        objectSpace.addConnection(connection);
        clients[nextAvailableRobotID] = connection;

        Payload reply = new Payload();
        reply.message = "id " + nextAvailableRobotID;
        connection.sendTCP(reply);
        
        nextAvailableRobotID++;
        nPlayers++;
    }

    private void processNewRobot(Connection connection, Robot r){
        map.registerRobot(r);

        //Assigning robotID server side
        if(r.assignID(nextAvailableRobotID)){
            nextAvailableRobotID++;
            nPlayers++;
        }
        //Registering connection and remote robot
        objectSpace.register(r.getID(),r);

        //Assign the client robot it's ID as well
        ObjectSpace.getRemoteObject(connection,r.getID(),Robot.class).assignID(r.getID());
    }

    private void launchGame(){
        state = ServerState.GAME;
        //assign robots spawnpoints



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
