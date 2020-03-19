package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import inf112.app.game.RoboRally;

public class MainMenuScreen implements Screen {

    final RoboRally game;
    protected Stage stage;
    protected Skin skin;
    StretchViewport viewport;

    public MainMenuScreen(final RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;
        skin = game.skin;

    }

    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true); // Centers the table relative to the stage
        VisTextButton singleplayerButton = new VisTextButton("singleplayer");
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
        table.add(singleplayerButton);
        table.row();
        table.add(multiplayerButton);
        table.row();
        table.add(settingsButton);
        table.row();
        table.add(exitButton);
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