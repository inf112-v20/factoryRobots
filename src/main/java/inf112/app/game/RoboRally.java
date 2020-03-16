package inf112.app.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.app.map.Map;
import inf112.app.objects.Player;

import inf112.app.screens.MainMenuScreen;

public class RoboRally extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    private Map cellMap;
    private Player player;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

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
    public void resize(int width, int height) {
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
}
