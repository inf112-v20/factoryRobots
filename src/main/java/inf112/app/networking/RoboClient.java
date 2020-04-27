package inf112.app.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.app.cards.CardDeck;
import inf112.app.cards.CardSlot;
import inf112.app.cards.ICard;
import inf112.app.game.CardUI;
import inf112.app.game.RoboRally;
import inf112.app.map.Map;
import inf112.app.networking.packets.Payload;
import inf112.app.networking.packets.RobotListPacket;
import inf112.app.networking.packets.RobotStatePacket;
import inf112.app.objects.Robot;
import inf112.app.screens.LoadingGameScreen;
import inf112.app.screens.MultiplayerScreen;
import inf112.app.screens.ServerLobbyScreen;

import java.io.IOException;
import java.util.ArrayList;

public class RoboClient extends Listener {
    private final RoboRally game;
    private final StretchViewport viewport;
    private final Stage stage;

    private static Client client;
    private static int tcpPort = 10801;
    private final String ip;
    public boolean serverAccept = false;
    public boolean serverReject = false;
    public String serverMessage = "";

    private int id = -1;
    private String username;
    private ArrayList<String> userList;
    private ArrayList<Integer> idList;

    private CardDeck deck;

    public RoboClient(final RoboRally game, StretchViewport viewport, Stage stage, String ip, String username) throws IOException {
        this.ip = ip;
        this.username = username;
        userList = new ArrayList<>(RoboRally.MAX_PLAYER_AMOUNT);
        idList = new ArrayList<>(RoboRally.MAX_PLAYER_AMOUNT);

        client = new Client();

        client.getKryo().register(Payload.class);
        client.getKryo().register(RobotListPacket.class);
        client.getKryo().register(RobotStatePacket.class);
        client.getKryo().register(int[].class);

        client.start();
        //IOException if unable to connect
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
            interpretPayload((Payload) object);
        } else if(object instanceof RobotListPacket){
            processRobotList((RobotListPacket) object);
        } else if (object instanceof RobotStatePacket){
            if(!game.isHost){ //if client is hosting, then server has already
                interpretRobotState((RobotStatePacket) object);
            }
        }
    }

    /**
     * Used to decode the information about a players choice
     * and apply it to the clients representation of that robot
     * @param packet The packet carrying the information about the robots state
     */
    private void interpretRobotState(RobotStatePacket packet){
        if(packet.id != id) {
            Robot r = null;
            ArrayList<Robot> roboList = Map.getInstance().getRobotList();
            for (Robot robot : roboList) {
                if (robot.getID() == packet.id) {
                    r = robot;
                }
            }
            r.setPowerDownNextRound(packet.powerdownNextRound);
            for (int i = 0; i < packet.programmedCards.length; i++) {
                ICard card = deck.getCard(packet.programmedCards[i]);
                r.setProgrammedCard(i, card);
            }
            Map.getInstance().incrementDoneProgramming();
        }
    }

    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }

    /**
     * Used to interpret the list of robots that is initially sent by the server
     * when the game launches
     * @param packet containing the list of robots
     */
    private void processRobotList(RobotListPacket packet){
        if(this.id == -1){
            System.out.println("Haven't recevied ID from server, something is wrong\nClosing connection");
            client.close();
        }
        Map map = Map.getInstance();
        for(Robot r : packet.list){
            if(r.getID() == this.id){
                game.getPlayer().assignRobot(r);
            }
        }
        if(!game.isHost){ //The host already registered the robots when the server created them
            map.setRobotList(packet.list);
        }

    }

    /**
     * Lookup method for the roborally protocol
     * Used whenever a payload object is sent. Message contains a keyword
     * and following information based on the keyword
     * @param payload containing the information
     */
    private void interpretPayload(Payload payload){
        String[] split = payload.message.split(" ");
        if(split.length == 0){
            System.out.println("Empty payload");
            return;
        }
        System.out.println("Server sent: " + payload.message); //For debugging TODO Remove
        try {
            switch (split[0].toLowerCase()) {
                case "id": //Server assigns client id
                    this.id = Integer.parseInt(split[1]);
                    break;
                case "map": //Server broadcasts which map will be played
                    game.setMapName(split[1]);
                    int nPlayers = Integer.parseInt(split[2]);
                    game.setNPlayers(nPlayers);
                    Gdx.app.postRunnable(() -> game.setScreen(new LoadingGameScreen(game, viewport, stage)));

                    break;
                case "cards": //Server hands out cards
                    Gdx.app.postRunnable(() -> {
                        if(deck == null){
                            deck = Map.getInstance().getDeck();
                        } else {
                            deck.reset();
                        }
                        CardUI ui = CardUI.getInstance();
                        game.getPlayer().getCharacter().wipeSlots(ui.getSideCardSlots());
                        for(int i = 1; i<split.length; i++){
                            ICard card = deck.getCard(Integer.parseInt(split[i]));
                            assert card != null;
                            ui.addCardToSlot(card,"side",(i-1));
                        }
                    });

                    break;
                case "disc": //Some client disconnected
                    int id = Integer.parseInt(split[1]);
                    for(Robot r : Map.getInstance().getRobotList()){
                        if(r.getID() == id){
                            Map.getInstance().deleteRobot(r);
                        }
                    }
                    break;
                case "users": //Update to the playercount
                    userList.clear();
                    for(int i = 1; i<split.length; i++){
                        userList.add(split[i]);
                    }
                    if(game.getScreen() instanceof ServerLobbyScreen){
                        ((ServerLobbyScreen) game.getScreen()).updatePlayerList(userList);
                    }
                    break;
                case "success": //Server accepted client
                    Payload greeting = new Payload();
                    greeting.message = "username " + this.username;
                    client.sendTCP(greeting);
                    serverAccept = true;
                    break;
                case "running": //Servers gamesession already running
                    serverReject = true;
                    serverMessage = "Gamesession is already running";
                    break;
                case "full": //Server is full
                    serverReject = true;
                    serverMessage = "Server is full";
                    break;
                case "shutdown":
                    if(game.getScreen() instanceof MultiplayerScreen){
                         ((MultiplayerScreen) game.getScreen()).alertUser("Host disconnected");
                    }
                    break;
                case "allready":
                    if(game.getScreen() instanceof ServerLobbyScreen){
                        ((ServerLobbyScreen) game.getScreen()).alertUser("Server launching..");
                    }
                    break;
                case "ids":
                    for(int i = 1; i<split.length; i++){
                        idList.add(Integer.parseInt(split[i]));
                    }
                    break;
                default:
                    System.out.println("Server reply: " + payload.message);
                    break;
            }
        } catch (IndexOutOfBoundsException e){
            System.out.println("Malformed payload message\n" + e.getMessage());
            client.close();
        } catch (NumberFormatException e){
            System.out.println("ID message contains invalid id\n" + e.getMessage());
            client.close();
        }
    }

    /**
     * Used when the player has locked in
     * his choice of programming for the round
     * His robots state is then sent to the server
     * which passes the info to all the other clients as well
     */
    public void sendProgramming(){
        int[] priorities = new int[5];
        Robot robot = game.getPlayer().getCharacter();
        for(int i = 0; i < robot.getProgrammedCards().length; i++){
            ICard card = robot.getProgrammedCard(i);
            priorities[i] = card.getPoint();

        }
        RobotStatePacket payload = new RobotStatePacket();
        payload.programmedCards = priorities;
        payload.id = this.id;
        payload.powerdownNextRound = game.getPlayer().getCharacter().getPowerDownNextRound();
        client.sendTCP(payload);
        sendRemainingCards();
    }

    /**
     * After the programming is sent
     * the server also needs to know which
     * cards the client hasn't used
     */
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
    }

    /**
     * When the {@link inf112.app.screens.GameScreen} is done simulating the round
     * this method is called to let the server know that the client is now ready to receive
     * a new set of cards
     */
    public void sendDone(){
        Payload message = new Payload();
        message.message = "done";
        client.sendTCP(message);
    }

    public void disconnect(){
        client.stop();
    }

    public void sendReady(){
        Payload ready = new Payload();
        ready.message = "ready";
        client.sendTCP(ready);
    }

    public void sendUnready(){
        Payload unready = new Payload();
        unready.message = "unready";
        client.sendTCP(unready);
    }

    public void notifyDoneLoading(){
        Payload loading = new Payload();
        loading.message = "doneloading";
        client.sendTCP(loading);
    }

    public ArrayList<Integer> getIdList() {
        return idList;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getUserList() {
        return userList;
    }
}
