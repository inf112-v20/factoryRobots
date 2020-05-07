package inf112.app.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.VisUI;
import inf112.app.map.Map;

import inf112.app.networking.RoboClient;
import inf112.app.networking.RoboServer;
import inf112.app.screens.GameScreen;
import inf112.app.screens.LoadingMenuScreen;
import inf112.app.screens.PauseGameScreen;

import java.io.IOException;

public class RoboRally extends Game {
    public SpriteBatch batch;
    public static final int MAX_PLAYER_AMOUNT = 8;

    public static final boolean DEBUG = true;

    private Player player;
    private String playerName = "Anonymous";

    private int numberOfPlayersInSession;
    public static final String[] robotNames =
            new String[]{"1Comb","2Comb","3Comb","4Comb","5Comb","6Comb","7Comb","8Comb"};

    protected Stage stage;
    protected StretchViewport viewport;
    protected Screen lastScreen;
    protected Screen currentScreen;

    public RoboClient client;
    private RoboServer server;
    public boolean isHost = false;

    public Texture backgroundImg;

    public AssetManager manager;

    private String mapName = "Maps/DizzyDashB"; // If the user doesn't select a map.

    public Music backgroundMusic;
    public Music gameMusic;
    public Sounds sounds;

    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        sounds = new Sounds(manager);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/Sounds/BackGroundSong.wav"));
        backgroundMusic.setVolume(0.1f);
        backgroundMusic.play();
        backgroundMusic.setLooping(true);

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/Sounds/CombatMusic.wav"));
        gameMusic.setVolume(0.1f);
        gameMusic.setLooping(true);

        client = null;

        backgroundImg = new Texture(Gdx.files.internal("assets/game-menu.png"));

        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.apply();

        stage = new Stage(viewport, batch); // Create new stage to share with each screen
        Gdx.input.setInputProcessor(stage); // Define InputProcessor on the stage
        this.setScreen(new LoadingMenuScreen(this, viewport, stage));

        numberOfPlayersInSession = 2;
        setPlayer();
    }

    @Override
    public void dispose() {
        // Dispose of all object if any of the screens closes
        batch.dispose();
        stage.dispose();
        VisUI.dispose();
        backgroundImg.dispose();
        backgroundMusic.dispose();
        gameMusic.dispose();
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
        this.backgroundMusic.pause();
        this.gameMusic.pause();
        this.setScreen(new PauseGameScreen(this, viewport, stage));
    }

    /**
     * Resumes the last used screen
     */
    @Override
    public void resume() {
        if (currentScreen != null){
            this.setScreen(this.currentScreen);
        }
        if (currentScreen.getClass() == GameScreen.class) {
            gameMusic.play();
        }
        else {
            backgroundMusic.play();
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
    public void setPlayer(){
        player = new Player();
    }

    public Player getPlayer(){
        return this.player;
    }

    public Screen getLastScreen(){
        return this.lastScreen;
    }

    /**
     * Set the game screen
     * This overrides the super method to check that the screen parameter is not of PauseGameScreen Class.
     * Also sets the current screen and last screen
     * @param screen
     */
    @Override
    public void setScreen(Screen screen) {
        if (screen.getClass() != PauseGameScreen.class) {
            if (this.screen != null && this.screen.getClass() != PauseGameScreen.class) {
                lastScreen = this.screen; // Set last screen
            }
            currentScreen = screen; // Set current screen
        }
        super.setScreen(screen);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void launchServer() {
        if(server != null){
            System.out.println("Server already launched");
            return;
        }
        this.server = new RoboServer(this);
        try{
            this.client = new RoboClient(this,viewport,stage,"localhost", playerName);
        } catch (IOException e){
            System.out.println("Unable to connect to localhost");
        }

    }

    public void shutdownServer() {
        server.shutdown();
        server = null;
    }

    public void setNPlayers(int nPlayers) {
        numberOfPlayersInSession = nPlayers;
    }

    public int getNumberOfPlayersInSession() {
        return numberOfPlayersInSession;
    }

}
