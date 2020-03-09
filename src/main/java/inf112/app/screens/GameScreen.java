package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.app.game.RoboRally;
import inf112.app.map.Map;

public class GameScreen implements Screen {
    final RoboRally game;
    OrthographicCamera camera;
    OrthographicCamera uiCam;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthogonalTiledMapRenderer uiRenderer;
    private TiledMapStage stage;

    private float tileSize = 300f;
    private float viewportWidth = 20, viewPortHeight = 20; //cellmap + 5
    private float initialCameraY;

    public GameScreen(final RoboRally game){
        this.game = game;
        camera = new OrthographicCamera();
        uiCam = new OrthographicCamera();

        Map cellMap = Map.getInstance();

        //Initialize clicklistener
        stage = new TiledMapStage();
        Gdx.input.setInputProcessor(stage);
        camera.setToOrtho(false, viewportWidth, viewPortHeight);
        uiCam.setToOrtho(false, 8, 9);


        initialCameraY = viewPortHeight - cellMap.getMapSizeY();
        camera.position.y = initialCameraY;
        camera.update();

        CardUI ui = CardUI.getInstance();

        //Initializing renderers
        mapRenderer = new OrthogonalTiledMapRenderer(cellMap.getMap(), (1/tileSize));
        mapRenderer.setView(camera);
        uiRenderer = new OrthogonalTiledMapRenderer(ui.getTiles(), (1/400f)); //400f = card width
        uiRenderer.setView(uiCam);
        //Setting the clicklistener to have the same frame as the renderers
        stage.getViewport().setCamera(uiCam);

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

        updatePlayer();
        stage.act();
        stage.draw();



        uiRenderer.render();
        mapRenderer.render();

        //Remove last player position
        game.cellMap.getLayer("player").setCell(game.player.getCharacter().getPos().getXCoordinate(),
                game.player.getCharacter().getPos().getYCoordinate(), null);

        game.batch.end();


    }

    private void updatePlayer(){
        int playerX = game.player.getCharacter().getPos().getXCoordinate();
        int playerY = game.player.getCharacter().getPos().getYCoordinate();
        TiledMapTileLayer playerLayer = game.cellMap.getLayer("player");

        //Setting player sprite to current position
        playerLayer.setCell(playerX, playerY, game.player.getCharacter().getNormal());
        //Checking if player is touching hole or flag
        if(game.cellMap.getLayer("hole").getCell(playerX, playerY) != null){
            playerLayer.setCell(playerX, playerY, game.player.getCharacter().getLooser());
        } else if(game.cellMap.getLayer("flag").getCell(playerX, playerY) != null) {
            playerLayer.setCell(playerX, playerY, game.player.getCharacter().getWinner());
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

    }
}
