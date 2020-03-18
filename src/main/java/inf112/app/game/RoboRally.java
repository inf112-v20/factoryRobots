package inf112.app.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import inf112.app.map.Map;
import inf112.app.objects.Player;

import inf112.app.screens.MainMenuScreen;

public class RoboRally extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    private Player player;

    public Texture backgroundImg;
    public Skin skin;
    public TextureAtlas atlas;

    public AssetManager manager;
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        backgroundImg = new Texture(Gdx.files.internal("assets/game-menu.png"));
        atlas = new TextureAtlas(Gdx.files.internal("assets/robo-rally-ui-2/robo-rally.atlas"));
        skin = new Skin(Gdx.files.internal("assets/robo-rally-ui-2/robo-rally.json"), atlas);

        this.setScreen(new MainMenuScreen(this));
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
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public void setMap(String name){
        Map.setInstance(name);
    }
    public void setPlayer(int x, int y){
        player = new Player(x, y);
    }
    public Player getPlayer(){
        return this.player;
    }
    public AssetManager getManager() {
        return manager;
    }

}
