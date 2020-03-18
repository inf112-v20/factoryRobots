package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.app.game.CardDeck;
import inf112.app.game.RoboRally;
import inf112.app.map.Map;
import inf112.app.objects.Player;
import inf112.app.objects.Position;
import inf112.app.objects.Robot;

import java.util.ArrayList;

public class GameScreen implements Screen {
    final RoboRally game;

    private OrthographicCamera camera;
    private OrthographicCamera uiCam;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthogonalTiledMapRenderer uiRenderer;
    private TiledMapStage stage;

    private CardDeck deck;

    private final int laserTime = 30;
    private float tileSize = 300f;
    private float viewportWidth = 20, viewPortHeight = 20; //cellmap + 5
    private float initialCameraY;

    private Map cellMap;
    private Player player;
    private Robot testRobot;

    public GameScreen(final RoboRally game){
        this.game = game;
        game.setMap("testMap");
        game.setPlayer(2,2);

        this.cellMap = Map.getInstance();
        this.player = game.getPlayer();
        this.testRobot = new Robot(new Position(4,4),"player");

        //Set up cameras
        camera = new OrthographicCamera();
        uiCam = new OrthographicCamera();

        Map cellMap = Map.getInstance();
        cellMap.registerRobot(player.getCharacter());
        cellMap.registerRobot(testRobot);

        //Initialize frame around board
        CardUI ui = CardUI.getInstance();
        ui.initializeCardSlots();

        //Create and shuffle deck
        deck = new CardDeck();

        for(int i = 0; i<9; i++){
            ui.addCardToSlot(deck.getCard(),"side",i);
        }

        //Initialize clicklistener
        stage = new TiledMapStage();
        Gdx.input.setInputProcessor(stage);
        camera.setToOrtho(false, viewportWidth, viewPortHeight);
        uiCam.setToOrtho(false, 8, 9);

        //Set up cameras
        initialCameraY = viewPortHeight - cellMap.getMapSizeY();
        camera.position.y = initialCameraY;
        camera.update();

        //Initialize clicklistener
        stage = new TiledMapStage();

        stage.addListener(new ClickListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return player.keyUp(keycode);
            }
        });



        //Initializing renderers
        mapRenderer = new OrthogonalTiledMapRenderer(cellMap.getMap(), (1/tileSize));
        mapRenderer.setView(camera);
        uiRenderer = new OrthogonalTiledMapRenderer(ui.getTiles(), (1/400f)); //400f = card width
        uiRenderer.setView(uiCam);
        //Setting the clicklistener to have the same frame as the renderers

        stage.getViewport().setCamera(uiCam);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();
        uiCam.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);


        game.batch.begin();

        updateRobots();
        stage.act();

        uiRenderer.render();
        mapRenderer.render();

        //Remove previous robot positions
        cellMap.clearLayer(cellMap.getLayer("player"));

        if(cellMap.getLaserTimer() == laserTime) {
            cellMap.deactivateLasers();
        }
        if(cellMap.lasersActive()){
            cellMap.incrementLaserTimer();
        }
        game.batch.end();
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
        stage.getViewport().setCamera(uiCam);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        game.batch.dispose();
        uiRenderer.dispose();
        mapRenderer.dispose();
    }

    public void updateRobots(){
        ArrayList<Robot> list = cellMap.getRobotList();
        for(Robot r : list){
            updateRobot(r);
        }
    }
}
