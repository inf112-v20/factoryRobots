package inf112.app.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.VisUI;
import inf112.app.map.Map;

import inf112.app.screens.LoadingMenuScreen;

public class RoboRally extends Game {
    public SpriteBatch batch;

    private Player player;
    protected Stage stage;

    public Texture backgroundImg;
    private TextureAtlas atlas;

    public AssetManager manager;

    private String mapName;


    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();

        backgroundImg = new Texture(Gdx.files.internal("assets/game-menu.png"));
        atlas = new TextureAtlas(Gdx.files.internal("assets/Skins/robo-rally-ui/Robo-Rally.atlas"));
        //Skin skin = new Skin(Gdx.files.internal("assets/Skins/robo-rally-ui/Robo-Rally.json"), atlas);



        StretchViewport viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

}
