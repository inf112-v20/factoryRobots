package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import inf112.app.game.RoboRally;
import inf112.app.util.TableBuilder;

/**
 * Options screen to toggle sound
 */
public class OptionScreen implements Screen {
    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;

    /**
     * Constructor for OptionScreen screen
     * @param game The RoboRally game
     * @param viewport The viewport for the RoboRally game
     * @param stage The stage for the RoboRally game
     */
    public OptionScreen(RoboRally game, StretchViewport viewport, Stage stage) {
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
        table.setFillParent(true); // Centers the table relative to the stage
        VisTextButton soundButton = new VisTextButton("Sound");
        soundButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                if (game.backgroundMusic.isPlaying()) {
                    game.backgroundMusic.pause();
                } else {
                    game.backgroundMusic.play();
                }
            }
        });
        VisTextButton returnButton = new VisTextButton("Return");
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                game.setScreen(game.getLastScreen());
            }
        });
        VisTextButton exitButton = new VisTextButton("Exit");
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                Gdx.app.exit();
            }
        });
        TableBuilder.column(table, soundButton, returnButton, exitButton);
        stage.addActor(table);

        stage.addListener(new ClickListener() {
            @Override
            public boolean keyUp (InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE){
                    game.sounds.buttonSound();
                    game.setScreen(game.getLastScreen());
                    return true;
                }
                return false;
            }
        });

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
