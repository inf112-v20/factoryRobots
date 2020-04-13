package inf112.app.networking;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sun.org.apache.xerces.internal.parsers.IntegratedParserConfiguration;
import inf112.app.cards.CardDeck;
import inf112.app.cards.CardSlot;
import inf112.app.cards.ICard;
import inf112.app.game.CardUI;
import inf112.app.game.Player;
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

    private CardDeck deck;



    public RoboClient(final RoboRally game, StretchViewport viewport, Stage stage, CardDeck deck) throws IOException {
        client = new Client();

        client.getKryo().register(Payload.class);
        client.getKryo().register(RobotListPacket.class);

        client.start();
        client.connect(5000, ip, tcpPort);
        client.addListener(this);

        this.game = game;
        this.viewport = viewport;
        this.stage = stage;

        this.deck = deck;
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
            interpretPayload(connection, (Payload) object);
        } else if(object instanceof RobotListPacket){
            processRobotList(connection, (RobotListPacket) object);


        }
    }

    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }

    private void processRobotList(Connection c, RobotListPacket packet){
        if(this.id == -1){
            System.out.println("Haven't recevied ID from server, something is wrong\nClosing connection");
            c.close();
        }
        Map map = Map.getInstance();
        for(Robot r : packet.list){
            if(r.getID() == this.id){
                game.getPlayer().assignRobot(r);
            }
        }
        map.setRobotList(packet.list);
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
                case "cards":
                    for(int i = 1; i<split.length; i++){
                        CardUI ui = CardUI.getInstance();
                        game.getPlayer().getCharacter().wipeSlots(ui.getSideCardSlots());
                        ICard card = deck.getCard(Integer.parseInt(split[i]));
                        ui.addCardToSlot(card,"side",(i-1));
                    }
                    break;
                case "start":
                    Map.getInstance().resetDoneProgramming();
                    //Initiate the round simulation
                    //send reply when done simulating round or if something went wrong
                    // then verify map state
                    break;
                case "incrdone":
                    Map.getInstance().incrementDoneProgramming();
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

    public void sendProgramming(){
        String message = "prog ";
        Robot robot = game.getPlayer().getCharacter();
        for(int i = 0; i < robot.getProgrammedCards().length; i++){
            ICard card = robot.getProgrammedCard(i);
            message += card.getPoint() + " ";
        }
        Payload payload = new Payload();
        payload.message = message;
        client.sendTCP(payload);
        sendRemainingCards();
    }

    private void sendRemainingCards(){
        String message = "rem ";
        for(CardSlot slot : CardUI.getInstance().getSideCardSlots()){
            if(slot.hasCard()){
                message += slot.getCard().getPoint() + " ";
            }
        }
        Payload payload = new Payload();
        payload.message = message;
        client.sendTCP(payload);
        payload.message = "done";
        client.sendTCP(payload);
    }


    public static void main(String[] args) throws Exception{
        RoboClient client = new RoboClient(new RoboRally(),new StretchViewport(1000,1000), new Stage(), new CardDeck());
        while(true){
            continue;
        }
    }
}
