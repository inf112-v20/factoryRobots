package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import inf112.app.cards.CardSlot;
import inf112.app.game.*;
import inf112.app.map.Map;
import inf112.app.objects.Robot;
import inf112.app.util.TableBuilder;

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

    private Rounds currentRound;

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
    private ArrayList<Robot> toBeRemoved = new ArrayList<>(8);
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
        currentRound = new Rounds();

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
                game.sounds.buttonSound();
                return player.keyUp(keycode);
            }
        });
        tiledStage.addListener(new ClickListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == 131){ // Escape
                    game.sounds.buttonSound();
                    showEscapeDialog();
                    return true;
                }
                return false;
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

        if(game.client == null){
            currentRound.dealCards(tiledStage);
        }
        this.timer = new Timer(-1, alert); //set count to float > 0 to test timer

    }

    @Override
    public void show() {
        if(game.backgroundMusic.isPlaying()){
            game.backgroundMusic.stop();
            game.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/Sounds/CombatMusic.wav"));
            game.backgroundMusic.setVolume(0.2f);
            game.backgroundMusic.setLooping(true);
            game.backgroundMusic.play();
        } else {
            game.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/Sounds/CombatMusic.wav"));
            game.backgroundMusic.setVolume(0.2f);
            game.backgroundMusic.setLooping(true);
        }


        stage.clear();
        Gdx.input.setInputProcessor(tiledStage);
        VisTable table = new VisTable();
        table.setFillParent(true);
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

        //Stop laser and start next phase
        if(phaseTimer > waitThresh && ongoingRound){
            timeForNextPhase = true;
            cellMap.deactivateLasers();
        }
        //Fire laser halfway into waiting for the next phase
        if(phaseTimer > waitThresh/2 && !firedLasers && ongoingRound){
            firedLasers = true;
            cellMap.fireLasers();
            if(phaseNum > 5) {
                for (Robot r : cellMap.getRobotList()) {
                    if (r.getPowerDownNextRound()) {
                        r.setPowerDown(true);
                        r.setPowerDownNextRound(false);
                    } else if (r.getPowerDown()) {
                        r.setPowerDown(false);
                    }
                }
            }
        }
        //If all robots -1 is ready, then start the timer
        if(cellMap.checkForTimerActivation() && !timerRunning && !ongoingRound){
            timerRunning = true;
            timer.start();
        }
        //Assign random program and lock in if user is not done programming when timer runs out
        if(timer.done && !player.getCharacter().doneProgramming()){
            assignRandomProgram(player.getCharacter());
            tiledStage.getLockInButton().lockButton();
            if(game.client!=null){
                game.client.sendProgramming();
            }
        }

        //initate new round when all robots are ready
        if(cellMap.checkIfAllRobotsReady()){
            tiledStage.setCardPushable(false);

            ongoingRound = true;
            phaseNum = 1;
            phaseTimer = 0;
            firedLasers = true;
            Gdx.graphics.getDeltaTime();

            cellMap.resetDoneProgramming();
            timerRunning = false;
            timer.done = false;
            alert.setText("");
        }
        //Do phases when round is ongoing
        if(ongoingRound && timeForNextPhase){
            //After 5 phases stop the round and reset
            if(phaseNum > 5){
                ongoingRound = false;
                tiledStage.releaseButtons();
                phaseNum = 1;
                //If anyone pressed the powerdown, set them to powerdown
                for(Robot r : cellMap.getRobotList()){
                    if(r.getPowerDown()){
                        r.setDoneProgramming(true);
                        cellMap.incrementDoneProgramming();
                        if(game.client == null){
                            initiateAI();
                        }
                        //Make buttons and cards unclickable
                        if(r.equals(player.getCharacter())){
                            tiledStage.setCardPushable(false);
                            tiledStage.getLockInButton().lockButton();
                        }
                    }
                }
                //Notify server that client is done simulating the round
                //If singleplayer, just deal new cards
                if(game.client != null){
                    game.client.sendDone();
                    cellMap.getDeck().reset();
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
            //Reset phase variables
            timeForNextPhase = false;
            firedLasers = false;
            phaseTimer = 0;

            //Check if anyone died or won
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
                    } //TODO stop after 1 winner
                    timer.disable();
                } else if(r.isDead()){
                    if(r.equals(player.getCharacter())){
                        alertUser("You died");
                        if(!game.isHost){
                            timer.disable();
                        }
                    }
                    toBeRemoved.add(r);
                }               //TODO reset robot til checkpoint når den er på kanten av mappet
            }
            //Delete dead robots
            if(!toBeRemoved.isEmpty()){
                for(Robot r : toBeRemoved){
                    cellMap.deleteRobot(r);
                }
                toBeRemoved.clear();
            }
        }
    }

    private void updateRobot(Robot robot){
        int robotX = robot.getPos().getXCoordinate();
        int robotY = robot.getPos().getYCoordinate();
        TiledMapTileLayer robotLayer = cellMap.getLayer("player");

        //Setting player sprite to current position
        if(robot.isDead()){
            return;
        }
        robotLayer.setCell(robotX, robotY, robot.getNormal());
        //Checking if player is touching hole or flag
        if(robot.fellIntoHole){
            robotLayer.setCell(robotX, robotY, robot.getLooser());
            robot.backToCheckPoint();
            robot.takeLife();
            robot.fellIntoHole = false;
        } else if (robot.isHit()){
            robotLayer.setCell(robotX, robotY, robot.getLooser());
        } else if(cellMap.getLayer("flag").getCell(robotX, robotY) != null) {
            robotLayer.setCell(robotX, robotY, robot.getWinner());
        } else if(robot.isWinner()){
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
        }
        cellMap.incrementDoneProgramming();
        r.setDoneProgramming(true);
    }

    public void initiateAI(){
        for(Robot r : cellMap.getRobotList()){
            if(!r.equals(player.getCharacter())){
                assignRandomProgram(r);
            }
        }
    }
    private void showEscapeDialog() {
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new ClickListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == 131){ // Escape
                    game.sounds.buttonSound();
                    show();
                    return true;
                }
                return false;
            }
        });
        VisTable table = new VisTable();
        table.setFillParent(true); // Centers the table relative to the stage
        VisTextButton soundButton = new VisTextButton("Sound");
        soundButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                if (game.backgroundMusic.isPlaying()) {
                    game.backgroundMusic.pause();
                } else {
                    game.backgroundMusic.play();
                }
            }
        });
        VisTextButton returnButton = new VisTextButton("Return");
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                show();
            }
        });
        VisTextButton exitButton = new VisTextButton("Exit");
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                Gdx.app.exit();
            }
        });
        TableBuilder.column(table, soundButton, returnButton, exitButton);
        stage.addActor(table);
    }
}
