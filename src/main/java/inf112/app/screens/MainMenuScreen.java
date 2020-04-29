package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import inf112.app.game.RoboRally;
import inf112.app.util.TableBuilder;

public class MainMenuScreen implements Screen {

    private final RoboRally game;
    private final Stage stage;
    private final StretchViewport viewport;

    public MainMenuScreen(final RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;
    }

    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true); // Centers the table relative to the stage
        VisTextButton singleplayerButton = new VisTextButton("Singleplayer");
        singleplayerButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                game.setScreen(new SinglePlayerScreen(game, viewport, stage));
            }
        });
        VisTextButton joinButton = new VisTextButton("Join Game");
        joinButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                game.setScreen(new JoinGameScreen(game, viewport, stage));
            }
        });
        VisTextButton hostButton = new VisTextButton("Host Game");
        hostButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                game.setScreen(new HostGameScreen(game, viewport, stage));
            }
        });
        VisTextButton settingsButton = new VisTextButton("Settings");
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                game.setScreen(new OptionScreen(game,viewport, stage));
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
        TableBuilder.column(table, singleplayerButton, joinButton, hostButton, settingsButton, exitButton);
        stage.addActor(table);
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