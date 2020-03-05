package inf112.app.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.app.map.Map;

public class GameScreen implements Screen {
    final RoboRally game;
    OrthographicCamera camera;

    OrthogonalTiledMapRenderer renderer;

    public GameScreen(final RoboRally game){
        this.game = game;
        camera = new OrthographicCamera();
        Map cellMap = Map.getInstance();
        camera.setToOrtho(false, cellMap.getMapSizeX(), cellMap.getMapSizeY());
        camera.position.x = cellMap.getMapSizeX()/2f;
        camera.update();

        //Initializing renderer
        renderer = new OrthogonalTiledMapRenderer(cellMap.getMap(), (1/300f));
        renderer.setView(camera);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();

        int playerX = game.player.getCharacter().getPos().getXCoordinate();
        int playerY = game.player.getCharacter().getPos().getYCoordinate();
        TiledMapTileLayer playerLayer = game.cellMap.getLayer("player");

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        //Setting player sprite to current position
        playerLayer.setCell(playerX, playerY, game.player.getCharacter().getNormal());
        //Checking if player is touching hole or flag
        if(game.cellMap.getLayer("hole").getCell(playerX, playerY) != null){
            playerLayer.setCell(playerX, playerY, game.player.getCharacter().getLooser());
        } else if(game.cellMap.getLayer("flag").getCell(playerX, playerY) != null) {
            playerLayer.setCell(playerX, playerY, game.player.getCharacter().getWinner());
        }
        renderer.render();
        //Remove last player position
        playerLayer.setCell(playerX, playerY, null);

        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {

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

    }
}
