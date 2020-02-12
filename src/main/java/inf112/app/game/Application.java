package inf112.app.game;

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
import inf112.app.map.Map;

public class Application extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;

    //Camera and renderer
    public OrthogonalTiledMapRenderer renderer;
    public OrthographicCamera camera;

    public Map cellMap;

    //Player cells
    private Cell normalPlayer;
    private Cell winningPlayer;
    private Cell loosingPlayer;

    //Initial player position
    private Vector2 playerPos = new Vector2(2,2);

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
        TiledMapTileLayer playerLayer = cellMap.getLayer("player");

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        //Setting player sprite to current position
        playerLayer.setCell(playerX, playerY, normalPlayer);

        //Checking if player is touching hole or flag
        if(cellMap.getLayer("hole").getCell(playerX, playerY) != null){
            playerLayer.setCell(playerX, playerY, loosingPlayer);
        } else if(cellMap.getLayer("flag").getCell(playerX, playerY) != null) {
            playerLayer.setCell(playerX, playerY, winningPlayer);
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

    /**
     * Changes the players coordinates based on keypress
     * @param keycode Code for key that is being released
     * @return true if key is being released, false if not
     */
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                playerPos.add(-1,0);
                break;
            case Input.Keys.RIGHT:
                playerPos.add(1,0);
                break;
            case Input.Keys.UP:
                playerPos.add(0,1);
                break;
            case Input.Keys.DOWN:
                playerPos.add(0,-1);
                break;
            default:
                System.out.println("Unassigned input");
                break;
        }
        return false;
    }

    public Vector2 getPlayerPos(){
        return new Vector2(playerPos.x,playerPos.y);
    }
}
