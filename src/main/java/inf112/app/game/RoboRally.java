package inf112.app.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.app.map.Map;
import inf112.app.objects.Player;

public class RoboRally extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    //Camera and renderer
    //public OrthogonalTiledMapRenderer renderer;
    //public OrthographicCamera camera;

    public Map cellMap;

    public Player player;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        //Creating map
        Map.setInstance("testMap");
        cellMap = Map.getInstance();

        this.setScreen(new MainMenuScreen(this));
        player = new Player(2, 2);

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        super.render();
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
