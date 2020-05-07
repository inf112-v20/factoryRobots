package inf112.app.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import inf112.app.game.RoboRally;

/**
 * CreditsScreen shows the game creation credits
 */
public class CreditsScreen implements Screen {
    private final RoboRally game;
    private final Stage stage;
    private final StretchViewport viewport;

    /**
     * Constructor for CreditsScreen screen
     * @param game The RoboRally game
     * @param viewport The viewport for the RoboRally game
     * @param stage The stage for the RoboRally game
     */
    public CreditsScreen(final RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;
    }

    /**
     * Method that runs after {@link RoboRally#setScreen(Screen)} is called
     */
    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true);
        VisLabel text = new VisLabel("");
        text.setText("Made by:\n" +
                "Håvard, Halvor,\nRagnhild, Stian and Steffen\n\n" +
                "Robots by @halsalladam\n\n" +
                "Music:" +
                "\n   * Menu: Bit Rush - LOL\n" +
                "   * In-game: Route 3 - Mikel");
        VisTextButton returnButton = new VisTextButton("Return");
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                game.setScreen(game.getLastScreen());
            }
        });
        table.add(text).pad(3).height(viewport.getWorldHeight()/1.5f).width((viewport.getWorldWidth()/4)*3);
        table.row();
        table.add(returnButton).pad(3).height(60).width(600);
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
