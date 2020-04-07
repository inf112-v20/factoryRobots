package inf112.app.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import inf112.app.map.Map;
import inf112.app.objects.Robot;

import java.io.IOException;

public class RoboServer extends Listener {
    private static Server server;
    private static int tcpPort = 10801;
    private boolean terminated = false;

    private Map map;

    public RoboServer(String mapName) {
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
      //  Map.setInstance(mapName);
        map = Map.getInstance();
    }

    @Override
    public void connected(Connection connection) {
        //System.out.println(connection.getRemoteAddressTCP().toString() + " connected");
        Payload reply = new Payload();
        reply.message = "Welcome to the server";
        connection.sendTCP(reply);
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("A client disconnected");
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof Payload) {
            Payload p = (Payload) object;
            System.out.println(p.message);
            if("q".equals(p.message)){
                terminated = true;
                server.stop();
            }
        } else if (object instanceof Robot){
            map.registerRobot((Robot) object);
        }
    }


    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }

    public static void main(String[] args) throws Exception{
        RoboServer serv = new RoboServer("testMap");
        while(true){
            if(serv.terminated){
                break;
            }
        }
    }
}
