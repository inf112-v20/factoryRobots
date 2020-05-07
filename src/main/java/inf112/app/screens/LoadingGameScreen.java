package inf112.app.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import inf112.app.cards.CardDeck;
import inf112.app.game.CardUI;
import inf112.app.game.GameSounds;
import inf112.app.game.RoboRally;
import inf112.app.map.Map;
import inf112.app.objects.Robot;
import inf112.app.util.CardDeckLoader;
import inf112.app.util.CardUILoader;
import inf112.app.util.MapLoader;

/**
 * This screen is shown when a game is launched. It loads all necessary assets into the
 * asset manager and waits until they are loaded.
 */
public class LoadingGameScreen implements Screen {

    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;


    /**
     * Constructor for LoadingGameScreen screen
     * @param game The RoboRally game
     * @param viewport The viewport for the RoboRally game
     * @param stage The stage for the RoboRally game
     */
    public LoadingGameScreen(RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;
        // Load the map in the background
        game.manager.setLoader(Map.class, new MapLoader(new InternalFileHandleResolver(), this.game));
        game.manager.load(game.getMapName(), Map.class);
        // Load the deck in the background
        game.manager.setLoader(CardDeck.class, new CardDeckLoader(new InternalFileHandleResolver()));
        game.manager.load("deck",CardDeck.class);

        // Load the CardUI in the background
        game.manager.setLoader(CardUI.class, new CardUILoader(new InternalFileHandleResolver(), this.game));
        game.manager.load("cardUI",CardUI.class);

    }

    /**
     * Method that runs after {@link RoboRally#setScreen(Screen)} is called
     */
    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true);
        table.add(new VisLabel("Loading..."));
        stage.addActor(table);
    }

    /**
     * Called when the screen should render itself.
     * @param v The time in seconds since the last render.
     */
    @Override
    public void render(float v) {
        game.batch.begin();
        game.batch.draw(game.backgroundImg,0,0,viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();
        game.manager.update();
        if (game.manager.isFinished()) { // Load some, will return true if done loading
            //Loading robots when map is loaded
            GameSounds gameSounds = new GameSounds(game);
            Robot[] list = new Robot[game.getNumberOfPlayersInSession()];
            for(int i = 0; i<list.length; i++){
                list[i] = new Robot(Map.getInstance().getSpawnpoint(i), RoboRally.robotNames[i]);
                list[i].assignGameSounds(gameSounds);
                if(game.client != null){    //Id is only used in multiplayer session
                    list[i].assignID(game.client.getIdList().get(i));
                    if(game.client.getIdList().get(i) == game.client.getId()){ //If id matches assign robot to player
                        game.getPlayer().assignRobot(list[i]);
                        CardUI.getInstance().setPlayerRobotGraphic(game.manager.get("assets/Robots/thumbnails/" + (i+1) + "0.png"));
                    }
                } else {
                    game.getPlayer().assignRobot(list[0]);
                    CardUI.getInstance().setPlayerRobotGraphic(game.manager.get("assets/Robots/thumbnails/10.png"));
                }
            }
            if(game.client != null){
                game.client.notifyDoneLoading();
            }
            game.setScreen(new GameScreen(game,stage,viewport));
        }

        stage.act();
        stage.draw();

    }

    /**
     * Called when the Application is resized.
     * This can happen at any point during a non-paused state but will never happen before a call to create().
     * @param x The new width in pixels
     * @param y The new height in pixels
     */
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
        // Not Used
    }
}
