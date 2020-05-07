package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.building.OneColumnTableBuilder;
import com.kotcrab.vis.ui.building.utilities.Padding;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import inf112.app.game.RoboRally;

/**
 * This screen is called when the application loses focus. After focus is regained it will call
 * the last used screen.
 */
public class PauseGameScreen implements Screen {
    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;

    /**
     * Constructor for PauseGameScreen screen
     * @param game The RoboRally game
     * @param viewport The viewport for the RoboRally game
     * @param stage The stage for the RoboRally game
     */
    public PauseGameScreen(RoboRally game, StretchViewport viewport, Stage stage) {
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
        VisLabel title = new VisLabel("The game is paused!");
        VisImageTextButton resumeButton = new VisImageTextButton("Resume", "default");
        table.add(title);

        OneColumnTableBuilder builder = new OneColumnTableBuilder(); // Create a TableBuilder to insert into the window
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                game.resume();
            }
        });
        VisImageTextButton exitButton = new VisImageTextButton("Exit", "default");
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                Gdx.app.exit();
            }
        });
        Padding padding = new Padding(50,0,0,0);
        builder.append(resumeButton,exitButton).setTablePadding(padding);
        table.row();
        table.add(builder.build());

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
     * Not used
     */
    @Override
    public void dispose() {
        // Not used
    }
}
