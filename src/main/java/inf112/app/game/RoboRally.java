package inf112.app.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.VisUI;
import inf112.app.map.Map;
import inf112.app.objects.Player;

import inf112.app.screens.MainMenuScreen;

public class RoboRally extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    private Player player;
    private Stage stage;

    public Texture backgroundImg;
    public Skin skin;
    public TextureAtlas atlas;


    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        backgroundImg = new Texture(Gdx.files.internal("assets/game-menu.png"));
        atlas = new TextureAtlas(Gdx.files.internal("assets/Skins/robo-rally-ui/Robo-Rally.atlas"));
        skin = new Skin(Gdx.files.internal("assets/Skins/robo-rally-ui/Robo-Rally.json"), atlas);

        try { // Load the VisUI skin. Will throw GdxRuntimeException if already loaded
            VisUI.load(this.skin);
            VisUI.setDefaultTitleAlign(Align.center);
        } catch (GdxRuntimeException ignored){

        }

        StretchViewport viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.apply();

        stage = new Stage(viewport, batch); // Create new stage to share with each screen
        Gdx.input.setInputProcessor(stage); // Define InputProcessor on the stage

        this.setScreen(new MainMenuScreen(this, viewport, stage));
    }

    @Override
    public void dispose() {
        // Dispose of all object if any of the screens closes
        batch.dispose();
        font.dispose();
        stage.dispose();
        VisUI.dispose();
        backgroundImg.dispose();
        atlas.dispose();
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

}
