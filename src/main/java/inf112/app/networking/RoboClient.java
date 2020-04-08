package inf112.app.networking;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.app.game.RoboRally;
import inf112.app.map.Map;
import inf112.app.networking.packets.Payload;
import inf112.app.networking.packets.RobotListPacket;
import inf112.app.objects.Robot;
import inf112.app.screens.LoadingGameScreen;

import java.io.IOException;

public class RoboClient extends Listener {
    private final RoboRally game;
    private final StretchViewport viewport;
    private final Stage stage;

    private static Client client;
    private static int tcpPort = 10801;
    private static String ip = "localhost";
    private int id = -1;



    public RoboClient(final RoboRally game, StretchViewport viewport, Stage stage) throws IOException {
        client = new Client();

        client.getKryo().register(Payload.class);
        client.getKryo().register(RobotListPacket.class);

        client.start();
        client.connect(5000, ip, tcpPort);
        client.addListener(this);

        this.game = game;
        this.viewport = viewport;
        this.stage = stage;
    }

    @Override
    public void connected(Connection connection) {
        //System.out.println("Connected to " + connection.getRemoteAddressTCP().toString());
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof Payload){
            Payload p = (Payload) object;
            System.out.println(p.message);

            Payload reply = new Payload();
            reply.message = "q";
            connection.sendTCP(reply);
            System.exit(0);
        } else if(object instanceof RobotListPacket){
            boolean success = processRobotList(connection, (RobotListPacket) object);
            Payload reply = new Payload();
            reply.message = success ?  "ready" : "failed";
            connection.sendTCP(reply);
            if(!success){ connection.close(); }
        }
    }

    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }

    private boolean processRobotList(Connection c, RobotListPacket packet){
        if(this.id == -1){
            System.out.println("Haven't recevied ID from server, something is wrong\nClosing connection");
            return false;
        }
        Map map = Map.getInstance();
        for(Robot r : packet.list){
            if(r.getID() == this.id){
                game.getPlayer().assignRobot(r);
            }
            map.registerRobot(r);
        }
        return true;
    }

    private void interpretPayload(Connection c, Payload payload){
        String[] split = payload.message.split(" ");
        if(split.length == 0){
            System.out.println("Empty payload");
            return;
        }
        try {
            switch (split[0].toLowerCase()) {
                case "id":
                    this.id = Integer.parseInt(split[1]);
                    break;
                case "map":
                    game.setMapName(split[1]);
                    game.setScreen(new LoadingGameScreen(game, viewport, stage));
                    break;
            }
        } catch (IndexOutOfBoundsException e){
            System.out.println("Malformed payload message\n" + e.getMessage());
            c.close();
            return;
        } catch (NumberFormatException e){
            System.out.println("ID message contains invalid id\n" + e.getMessage());
            c.close();
            return;
        }
    }


    public static void main(String[] args) throws Exception{
        RoboClient client = new RoboClient(new RoboRally(),new StretchViewport(1000,1000), new Stage());
        while(true){
            continue;
        }
    }
}
