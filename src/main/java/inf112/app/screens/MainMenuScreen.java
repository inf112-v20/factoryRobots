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
                game.setScreen(new CourseSelector(game, viewport, stage));
            }
        });
        VisTextButton multiplayerButton = new VisTextButton("Multiplayer");
        multiplayerButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new CourseSelector(game, viewport, stage));
            }
        });
        VisTextButton settingsButton = new VisTextButton("Settings");
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new OptionScreen(game,viewport, stage));
            }
        });
        VisTextButton exitButton = new VisTextButton("Exit");
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        table.add(singleplayerButton).pad(3).height(60).width(350);
        table.row();
        table.add(multiplayerButton).pad(3).height(60).width(350);
        table.row();
        table.add(settingsButton).pad(3).height(60).width(350);
        table.row();
        table.add(exitButton).pad(3).height(60).width(350);
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