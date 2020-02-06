package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class Application extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;

    //Map and layers
    public TiledMap map;
    public TiledMapTileLayer boardLayer;
    public TiledMapTileLayer holeLayer;
    public TiledMapTileLayer flagLayer;
    public TiledMapTileLayer playerLayer;

    //Camera and renderer
    public OrthogonalTiledMapRenderer renderer;
    public OrthographicCamera camera;

    //Map dimensions
    private final int MAP_SIZE_X = 5, MAP_SIZE_Y = 5;

    //Player cells
    private Cell normalPlayer;
    private Cell winningPlayer;
    private Cell loosingPlayer;

    //Initial player position
    private Vector2 playerPos = new Vector2(0,0);

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        //Loading map
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("assets/map1.tmx");

        //Loading layers
        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");
        holeLayer = (TiledMapTileLayer) map.getLayers().get("Hole");
        flagLayer = (TiledMapTileLayer) map.getLayers().get("Flag");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");

        //Initializing camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MAP_SIZE_X, MAP_SIZE_Y);
        camera.position.x = 2.5f;
        camera.update();

        //Initializing renderer
        renderer = new OrthogonalTiledMapRenderer(map, (1/300f));
        renderer.setView(camera);

        //Loading and splitting player sprites
        Texture spriteMap = new Texture("assets/player.png");
        TextureRegion[][] sprites = TextureRegion.split(spriteMap,300,300);

        //Assigning individual sprites
        normalPlayer = new Cell().setTile(new StaticTiledMapTile(sprites[0][0]));
        loosingPlayer = new Cell().setTile(new StaticTiledMapTile(sprites[0][1]));
        winningPlayer = new Cell().setTile(new StaticTiledMapTile(sprites[0][2]));

        //Initializing input processor
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        //Extracted player coordinates
        int playerX = (int) playerPos.x;
        int playerY = (int) playerPos.y;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        //Setting player sprite to current position
        playerLayer.setCell(playerX, playerY, normalPlayer);

        //Checking if player is touching hole or flag
        if(holeLayer.getCell(playerX, playerY) != null){
            playerLayer.setCell(playerX, playerY, loosingPlayer);
        } else if(flagLayer.getCell(playerX, playerY) != null) {
            playerLayer.setCell(playerX, playerY, winningPlayer);
        }
        renderer.render();
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

    /**
     * Changes the players coordinates based on keypress
     * @param keycode Code for key that is being released
     * @return true if key is being released, false if not
     */
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                playerLayer.setCell((int) playerPos.x, (int) playerPos.y, null);
                playerPos.add(-1,0);
                break;
            case Input.Keys.RIGHT:
                playerLayer.setCell((int) playerPos.x, (int) playerPos.y, null);
                playerPos.add(1,0);
                break;
            case Input.Keys.UP:
                playerLayer.setCell((int) playerPos.x, (int) playerPos.y, null);
                playerPos.add(0,1);
                break;
            case Input.Keys.DOWN:
                playerLayer.setCell((int) playerPos.x, (int) playerPos.y, null);
                playerPos.add(0,-1);
                break;
        }
        return false;
    }

}
