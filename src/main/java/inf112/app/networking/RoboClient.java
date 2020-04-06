package inf112.app.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class RoboClient extends Listener {
    private static Client client;
    private static int tcpPort = 10801;
    private static String ip = "localhost";

    public RoboClient() throws IOException {
        client = new Client();

        client.getKryo().register(Payload.class);

        client.start();
        client.connect(5000,ip,tcpPort);

        client.addListener(this);

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
        }
    }

    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }


    public static void main(String[] args) throws Exception{
        RoboClient client = new RoboClient();
        while(true){
            continue;
        }
    }
}
