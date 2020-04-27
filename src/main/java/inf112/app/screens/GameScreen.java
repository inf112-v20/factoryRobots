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
import inf112.app.cards.ICard;
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

    private final int laserTime = 25;
    private float tileSize = 300f;
    private float cardWidth = 400f;
    private float viewportWidth = 20, viewPortHeight = 20; //cellmap + 5
    private float initialCameraY;

    private Map cellMap;
    private Player player;
    private Robot testRobot;
    private int phaseNum = 6;
    private boolean ongoingRound = false;

    public GameScreen(final RoboRally game, Stage stage, StretchViewport viewport){
        this.game = game;
        this.stage = stage;
        this.viewport = viewport;

        this.cellMap = Map.getInstance();
        game.manager.unload(game.getMapName());
        game.manager.unload("assets/Lasers.tmx");

        //this.testRobot = new Robot(new Position(4,4),"player"); //TODO remove this

        this.player = game.getPlayer();

        //Set up cameras
        camera = new OrthographicCamera();
        uiCam = new OrthographicCamera();

        //Initialize frame around board
        CardUI ui = CardUI.getInstance();
        ui.initializeCardSlots(game.getPlayer());
        ui.initializeDamageTokens();

        //Create and shuffle deck
        cellMap.setDeck(game.manager.get("deck"));
        game.manager.unload("deck");


        //Cards for testing
        /*for(int i = 0; i<9; i++){
            ui.addCardToSlot(Map.getInstance().getDeck().getCard(),"side",i);
        } */



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

        if(timer.done){
            for(CardSlot slot : game.getPlayer().getCharacter().getProgrammedCards()){
                if(!slot.hasCard()){
                    for(CardSlot available : game.getPlayer().getCharacter().getAvailableCards()){
                        if(available.hasCard()){
                            slot.addCard(available.removeCard(), tiledStage);
                            break;
                        }
                    }
                }
            }
            cellMap.incrementDoneProgramming();
            if(game.client!=null){
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

}
