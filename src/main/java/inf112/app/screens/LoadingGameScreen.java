package inf112.app.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import inf112.app.cards.CardDeck;
import inf112.app.game.RoboRally;
import inf112.app.map.Map;
import inf112.app.util.CardDeckLoader;
import inf112.app.util.MapLoader;

public class LoadingGameScreen implements Screen {

    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;

    public LoadingGameScreen(RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;

        game.manager.setLoader(Map.class, new MapLoader(new InternalFileHandleResolver()));
        game.manager.load(game.getMapName(), Map.class);

        game.manager.setLoader(CardDeck.class, new CardDeckLoader(new InternalFileHandleResolver()));
        game.manager.load("deck",CardDeck.class);

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
            game.setScreen(new GameScreen(game));
        }

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int x, int y) {
        viewport.update(x, y, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
