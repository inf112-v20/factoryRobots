package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
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

public class HelloWorld implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;

    //Map and layers
    public TiledMap map;
    public TiledMapTileLayer board;
    public TiledMapTileLayer hole;
    public TiledMapTileLayer flag;
    public TiledMapTileLayer player;

    //Camera and renderer
    public OrthogonalTiledMapRenderer renderer;
    public OrthographicCamera camera;
    //Map dimensions
    private final int MAP_SIZE_X = 5, MAP_SIZE_Y = 5;

    //Player cells
    private Cell normalPlayer;
    private Cell winningPlayer;
    private Cell loosingPlayer;

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
        board = (TiledMapTileLayer) map.getLayers().get("Board");
        hole = (TiledMapTileLayer) map.getLayers().get("Hole");
        flag = (TiledMapTileLayer) map.getLayers().get("Flag");
        player = (TiledMapTileLayer) map.getLayers().get("Player");
        //Initializing camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MAP_SIZE_X, MAP_SIZE_Y);
        camera.position.x = 2.5f;
        camera.update();

        //Initializing renderer
        renderer = new OrthogonalTiledMapRenderer(map, (1/300f));
        renderer.setView(camera);

        Texture spriteMap = new Texture("assets/player.png");
        TextureRegion[][] sprites = TextureRegion.split(spriteMap,300,300);

        normalPlayer = new Cell().setTile(new StaticTiledMapTile(sprites[0][0]));
        winningPlayer = new Cell().setTile(new StaticTiledMapTile(sprites[0][1]));
        loosingPlayer = new Cell().setTile(new StaticTiledMapTile(sprites[0][2]));

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        player.setCell(0,0,normalPlayer);
        renderer.render();


        /*batch.begin();
        font.draw(batch, "Hello World", 200, 200);
        batch.end(); */
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
