package inf112.app.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import inf112.app.game.RoboRally;

public class SinglePlayerScreen implements Screen {
    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;
    private int botsNumber = 1;

    public SinglePlayerScreen(RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;
    }

    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true); // Centers the table relative to the stage
        VisTextButton selectCourseButton = new VisTextButton("Select Course");

        selectCourseButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new CourseSelector(game, viewport, stage));
            }
        });

        VisTextButton botsButton = new VisTextButton("Number of Bots: " + botsNumber);
        botsButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if ( botsNumber >= 7 ){
                    botsNumber = 1;
                }
                else botsNumber++;
                botsButton.setText("Number of Bots: " + botsNumber);
            }
        });

        VisTable buttonTable = new VisTable();

        VisTextButton cancelButton = new VisTextButton("Cancel");
        VisTextButton startButton = new VisTextButton("Start");
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game, viewport, stage));
            }
        });
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
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
