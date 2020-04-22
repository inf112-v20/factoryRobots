package inf112.app.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.VisUI;
import inf112.app.map.Map;

import inf112.app.screens.LoadingMenuScreen;
import inf112.app.screens.PauseGameScreen;
import org.mockito.internal.matchers.Null;

public class RoboRally extends Game {
    public SpriteBatch batch;

    private Player player;
    protected Stage stage;
    protected StretchViewport viewport;
    protected Screen lastScreen;

    public Texture backgroundImg;

    public AssetManager manager;

    private String mapName = "Maps/testMap.tmx"; // If the user doesn't select a map.

    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();

        backgroundImg = new Texture(Gdx.files.internal("assets/game-menu.png"));

        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.apply();

        stage = new Stage(viewport, batch); // Create new stage to share with each screen
        Gdx.input.setInputProcessor(stage); // Define InputProcessor on the stage
        this.setScreen(new LoadingMenuScreen(this, viewport, stage));
    }

    @Override
    public void dispose() {
        // Dispose of all object if any of the screens closes
        batch.dispose();
        stage.dispose();
        VisUI.dispose();
        backgroundImg.dispose();
        manager.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    /**
     * Sets the screen to PauseGameScreen
     */
    @Override
    public void pause() {
        if (!(this.getScreen() instanceof PauseGameScreen)){
           // lastScreen = this.getScreen();
        }
        this.setScreen(new PauseGameScreen(this, viewport, stage));
    }

    /**
     * Resumes the last used screen
     */
    @Override
    public void resume() {
        if (lastScreen != null){
            this.setScreen(this.lastScreen);
        }
    }

    public void setMapName(String mapName){
        this.mapName = mapName;
    }

    public String getMapName(){
        return this.mapName;
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

    public Screen getLastScreen(){
        return this.lastScreen;
    }

    @Override
    public void setScreen(Screen screen) {
        // TODO Fix pause game screen
        if (screen.getClass() != PauseGameScreen.class) {
            lastScreen = this.screen;
        }
        super.setScreen(screen);
    }
}
