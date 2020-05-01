package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import inf112.app.cards.CardSlot;
import inf112.app.game.*;
import inf112.app.map.Map;
import inf112.app.objects.Robot;

import java.util.ArrayList;

public class GameScreen implements Screen, MultiplayerScreen {
    private final RoboRally game;
    private Stage stage;
    private StretchViewport viewport;

    private OrthographicCamera camera;
    private OrthographicCamera uiCam;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthogonalTiledMapRenderer uiRenderer;
    private TiledMapStage tiledStage;

    private Rounds currentRound = new Rounds();

    private boolean timerRunning = false;
    private Timer timer;
    private VisLabel alert = new VisLabel("");
    //How long to wait between the phases
    private int waitThresh = 2;
    private float phaseTimer = 0f;
    private boolean timeForNextPhase = false;
    private boolean firedLasers = false;

    private float tileSize = 300f;
    private float cardWidth = 400f;
    private float viewportWidth = 20, viewPortHeight = 20;
    private float initialCameraY;

    private Map cellMap;
    private Player player;
    private int phaseNum = 6;
    private boolean ongoingRound = false;

    public GameScreen(final RoboRally game, Stage stage, StretchViewport viewport){
        this.game = game;
        this.stage = stage;
        this.viewport = viewport;

        this.cellMap = Map.getInstance();
        game.manager.unload(game.getMapName());
        game.manager.unload("assets/Lasers.tmx");

        this.player = game.getPlayer();

        //Set up cameras
        camera = new OrthographicCamera();
        uiCam = new OrthographicCamera();

        //Initialize frame around board
        CardUI ui = CardUI.getInstance();
        ui.initializeCardSlots(game.getPlayer());
        ui.initializeDamageTokens();
        ui.setHealthLight(player.getCharacter().getLives());

        //Create and shuffle deck
        cellMap.setDeck(game.manager.get("deck"));
        game.manager.unload("deck");

        camera.setToOrtho(false, viewportWidth,viewPortHeight);
        uiCam.setToOrtho(false, 8, 9);

        //Set up cameras
        initialCameraY = viewPortHeight - cellMap.getMapSizeY();
        camera.position.y = initialCameraY;
        camera.update();

        //Initialize clicklistener
        tiledStage = new TiledMapStage(game);

        tiledStage.addListener(new ClickListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return player.keyUp(keycode);
            }
        });

        ui.setTiledStage(tiledStage);

        //Initializing renderers
        mapRenderer = new OrthogonalTiledMapRenderer(cellMap.getMap(),1/tileSize);
        mapRenderer.setView(camera);
        uiRenderer = new OrthogonalTiledMapRenderer(ui.getCardUITiles(), (1/cardWidth));
        uiRenderer.setView(uiCam);

        //Setting the clicklistener to have the same frame as the renderers
        tiledStage.getViewport().setCamera(uiCam);

        Gdx.input.setInputProcessor(tiledStage);
    }

    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true);
        this.timer = new Timer(-1,alert); //set count to float > 0 to test timer
        table.add(alert);
        stage.addActor(table);
        if(game.client == null){
            currentRound.dealCards(tiledStage);
        }
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        phaseTimer += Gdx.graphics.getDeltaTime();

        // tell the camera to update its matrices.
        camera.update();
        uiCam.update();

        updateRobots();

        uiRenderer.render();
        mapRenderer.render();

        tiledStage.act();


        game.batch.begin();

        if(timerRunning){
            timer.drawTime();
        }

        game.batch.end();

        stage.act();
        stage.draw();

        //Remove previous robot positions
        cellMap.clearLayer(cellMap.getLayer("player"));

        if(phaseTimer > waitThresh){
            timeForNextPhase = true;
            cellMap.deactivateLasers();
        }

        if(phaseTimer > waitThresh/2 && !firedLasers && ongoingRound){
            firedLasers = true;
            cellMap.fireLasers();
        }

        if(cellMap.checkForTimerActivation() && !timerRunning && !ongoingRound){
            timerRunning = true;
            timer.start();
        }

        if(timer.done && !game.getPlayer().getCharacter().doneProgramming()){
            assignRandomProgram(game.getPlayer().getCharacter());
            if(game.client!=null){ //TODO for all robots
                game.client.sendProgramming();
            }
        }


        if(cellMap.checkIfAllRobotsReady()){
            tiledStage.setCardPushable(false);

            ongoingRound = true;
            phaseNum = 1;
            phaseTimer = 0;
            Gdx.graphics.getDeltaTime();

            currentRound.putBackPlayers();
            cellMap.resetDoneProgramming();
            timerRunning = false;
            timer.done = false;
            alert.setText("");
        }
        if(ongoingRound && timeForNextPhase){
            if(phaseNum > 5){
                ongoingRound = false;
                tiledStage.releaseButtons();
                phaseNum = 1;
                if(game.client != null){
                    game.client.sendDone();
                } else {
                    currentRound.dealCards(tiledStage);
                }
            } else {
                currentRound.doPhase(phaseNum);
                phaseNum++;
                long waitTime = System.currentTimeMillis();
                long thresh = waitTime + waitThresh;
                while(waitTime <= thresh){
                    waitTime = System.currentTimeMillis();
                }
            }
            timeForNextPhase = false;
            firedLasers = false;
            phaseTimer = 0;
            for(Robot r : cellMap.getRobotList()){
                if(r.isWinner()){
                    if(game.client != null){
                        game.client.getWinner();
                    } else {
                        if(r.equals(game.getPlayer().getCharacter())){
                            alertUser("You won the game!");
                        } else {
                            alertUser("Computer won the game..");
                        }
                    }
                    timer.disable();
                }
            }
            if(player.getCharacter().isDead()){
                alertUser("You died");
            }
        }
    }

    private void updateRobot(Robot robot){
        int robotX = robot.getPos().getXCoordinate();
        int robotY = robot.getPos().getYCoordinate();
        TiledMapTileLayer robotLayer = cellMap.getLayer("player");

        //Setting player sprite to current position
        robotLayer.setCell(robotX, robotY, robot.getNormal());
        //Checking if player is touching hole or flag
        if(cellMap.getLayer("hole").getCell(robotX, robotY) != null){
            robotLayer.setCell(robotX, robotY, robot.getLooser());
            robot.backToCheckPoint();
        } else if(cellMap.getLayer("flag").getCell(robotX, robotY) != null) {
            robotLayer.setCell(robotX, robotY, robot.getWinner());
        }
    }

    @Override
    public void resize(int width, int height) {
       tiledStage.resize(width,height);
    }

    /**
     * Pauses the game. This is currently handled by an window listener instead of this function
     */
    @Override
    public void pause() {
        // Not used
    }

    /**
     * Resumes the game. This is currently handled by an window listener instead of this function
     */
    @Override
    public void resume() {
        // Not used
    }

    /**
     * Not used
     */
    @Override
    public void hide() {
        // Not used
    }

    @Override
    public void dispose() {
        tiledStage.dispose();
        game.batch.dispose();
        uiRenderer.dispose();
        mapRenderer.dispose();
        if(game.client != null){
            game.client.disconnect();
        }
        if(game.isHost){
            game.shutdownServer();
        }
    }

    public void updateRobots(){
        ArrayList<Robot> list = cellMap.getRobotList();
        for(Robot r : list){
            updateRobot(r);
        }
    }

    public void alertUser(String info){
        alert.setText(info);
    }

    private void assignRandomProgram(Robot r){
        for(CardSlot slot : r.getProgrammedCards()) {
            if (!slot.hasCard()) {
                for (CardSlot available : r.getAvailableCards()) {
                    if (available.hasCard()) {
                        slot.addCard(available.removeCard(), tiledStage);
                        break;
                    }
                }
            }
            cellMap.incrementDoneProgramming();
        }
    }
}
