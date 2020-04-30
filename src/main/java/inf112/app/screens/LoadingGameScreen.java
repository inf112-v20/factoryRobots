package inf112.app.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import inf112.app.cards.CardDeck;
import inf112.app.game.CardUI;
import inf112.app.game.RoboRally;
import inf112.app.map.Map;
import inf112.app.objects.Robot;
import inf112.app.util.CardDeckLoader;
import inf112.app.util.CardUILoader;
import inf112.app.util.MapLoader;

public class LoadingGameScreen implements Screen {

    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;

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
            //Loading robots when map is loaded
            Robot[] list = new Robot[game.getNumberOfPlayersInSession()];
            for(int i = 0; i<list.length; i++){
                list[i] = new Robot(Map.getInstance().getSpawnpoint(i), RoboRally.robotNames[i]);
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
     * Remove unused maps from the asset manager
     */
    @Override
    public void dispose() {
         /* Array<String> assetNames = game.manager.getAssetNames();
        assetNames.forEach(asset -> {
            if (asset.contains("Maps") && !asset.equals(mapList.get(index).toString())){
                game.manager.unload(asset);
            }
        });*/
    }
}
