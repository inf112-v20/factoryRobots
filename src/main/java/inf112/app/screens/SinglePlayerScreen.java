package inf112.app.screens;

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

/**
 * Host a single player game from this screen
 */
public class SinglePlayerScreen implements Screen {
    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;
    private int botsNumber = 1;

    /**
     * Constructor for SinglePlayerScreen screen
     * @param game The RoboRally game
     * @param viewport The viewport for the RoboRally game
     * @param stage The stage for the RoboRally game
     */
    public SinglePlayerScreen(RoboRally game, StretchViewport viewport, Stage stage) {
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
        VisTextButton selectCourseButton = new VisTextButton("Select Course");

        selectCourseButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                game.setScreen(new CourseSelector(game, viewport, stage));
            }
        });

        VisTextButton botsButton = new VisTextButton("Number of Bots: " + botsNumber);
        botsButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                if ( botsNumber >= 7 ){
                    botsNumber = 1;
                }
                else botsNumber++;
                botsButton.setText("Number of Bots: " + botsNumber);
                game.setNPlayers(botsNumber + 1);
            }
        });

        VisTable buttonTable = new VisTable();

        VisTextButton cancelButton = new VisTextButton("Cancel");
        VisTextButton startButton = new VisTextButton("Start");
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                game.setScreen(new MainMenuScreen(game, viewport, stage));
            }
        });
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                game.setScreen(new LoadingGameScreen(game, viewport, stage));
            }
        });

        buttonTable.add(cancelButton).pad(3).height(60).width(300);
        buttonTable.add(startButton).pad(3).height(60).width(300);

        table.add(selectCourseButton).pad(3).height(60).width(600);
        table.row();
        table.add(botsButton).pad(3).height(60).width(600);
        table.row();
        table.add(buttonTable).pad(3).height(60).width(600);

        stage.addActor(table);

        stage.addListener(new ClickListener() {
            @Override
            public boolean keyUp (InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE){
                    game.sounds.buttonSound();
                    game.setScreen(new MainMenuScreen(game, viewport, stage));
                    return true;
                }
                else if (keycode == Input.Keys.ENTER){
                    game.sounds.buttonSound();
                    game.setScreen(new LoadingGameScreen(game, viewport, stage));
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
