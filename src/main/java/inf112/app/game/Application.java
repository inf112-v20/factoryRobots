package inf112.app.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.app.map.Map;
import inf112.app.objects.Player;

public class Application implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;

    //Camera and renderer
    public OrthogonalTiledMapRenderer renderer;
    public OrthographicCamera camera;

    public Map cellMap;

    private Player player;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        //Creating map
        cellMap = new Map("testMap");

        //Initializing camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, cellMap.getMapSizeX(), cellMap.getMapSizeY());
        camera.position.x = cellMap.getMapSizeX()/2f;
        camera.update();

        //Initializing renderer
        renderer = new OrthogonalTiledMapRenderer(cellMap.getMap(), (1/300f));
        renderer.setView(camera);

        player = new Player(2, 2, cellMap);

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        //Extracted player coordinates
        int playerX = player.getCharacter().getPos().getXCoordinate();
        int playerY = player.getCharacter().getPos().getYCoordinate();
        TiledMapTileLayer playerLayer = cellMap.getLayer("player");

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        //Setting player sprite to current position
        playerLayer.setCell(playerX, playerY, player.getNormal());

        //Checking if player is touching hole or flag
        if(cellMap.getLayer("hole").getCell(playerX, playerY) != null){
            playerLayer.setCell(playerX, playerY, player.getLooser());
        } else if(cellMap.getLayer("flag").getCell(playerX, playerY) != null) {
            playerLayer.setCell(playerX, playerY, player.getWinner());
        }
        renderer.render();
        //Remove last player position
        playerLayer.setCell(playerX, playerY, null);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
