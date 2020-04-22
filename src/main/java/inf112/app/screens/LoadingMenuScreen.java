package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import inf112.app.game.RoboRally;

public class LoadingMenuScreen implements Screen {

    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;

    public LoadingMenuScreen(RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("assets/Skins/robo-rally-ui/Robo-Rally.atlas"));
        Skin skin = new Skin(Gdx.files.internal("assets/Skins/robo-rally-ui/Robo-Rally.json"), atlas);

        try { // Load the VisUI skin. Will throw GdxRuntimeException if already loaded
            VisUI.load(skin);
            VisUI.setDefaultTitleAlign(Align.center);
        } catch (GdxRuntimeException ignored){

        }
        // Load all maps into the AssetManager
        game.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        FileHandle files = Gdx.files.internal("assets/Maps");
        assert files.exists();
        for (FileHandle file : files.list()){
            if(file.name().endsWith(".tmx")){
                game.manager.load(file.toString(), TiledMap.class);
            }
        }
        // Load the LaserSprite into the AssetManager
        // to optimize loading time in LoadingGameScreen
        game.manager.load("assets/CardUI2.tmx", TiledMap.class);
        game.manager.load("assets/Lasers.tmx", TiledMap.class);
        game.manager.load("assets/GameButtons/Buttons.tmx",TiledMap.class);

        // Sounds
        game.manager.load("assets/Sounds/LazerSound.wav", Sound.class);
        game.manager.load("assets/Sounds/ButtonClick.wav", Sound.class);
        game.manager.load("assets/Sounds/NewCheckPoint.wav", Sound.class);
        game.manager.load("assets/Sounds/TakesDamage.wav", Sound.class);
        game.manager.load("assets/Sounds/DeathNoise.wav", Sound.class);

    }

    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true);
        table.add(new VisLabel("Loading..."));
        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        game.batch.begin();
        game.batch.draw(game.backgroundImg,0,0,viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();
        game.manager.update();
        if (game.manager.isFinished()) { // Load some, will return true if done loading
            game.setScreen(new MainMenuScreen(game, viewport, stage));
        }

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int x, int y) {
        viewport.update(x, y, true);
    }

    /**
     * Pauses the game. This is currently handled by an window listener instead of this function
     */
    @Override
    public void pause() {
        // Not used
    }

    /**
     * Resumes the game. This is currently handled by an window listener instead of this function
     */
    @Override
    public void resume() {
        // Not used
    }

    /**
     * Not used
     */
    @Override
    public void hide() {
        // Not used
    }

    /**
     * RoboRally disposes of objects at termination. Gamescreen clears the AssetManager
     */
    @Override
    public void dispose() {
        // Not used
    }
}
