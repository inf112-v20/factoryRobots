package inf112.app.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import inf112.app.game.RoboRally;

public class EndGameScreen implements Screen {
    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;

    public EndGameScreen(RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;
    }

    @Override
    public void show() {
        stage.clear();
    }

    @Override
    public void render(float v) {
        game.batch.begin();
        game.batch.draw(game.backgroundImg,0,0,viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();

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

    }

    /**
     * Resumes the game. This is currently handled by an window listener instead of this function
     */
    @Override
    public void resume() {

    }

    /**
     * Not used
     */
    @Override
    public void hide() {

    }

    /**
     * Not used
     */
    @Override
    public void dispose() {

    }
}
