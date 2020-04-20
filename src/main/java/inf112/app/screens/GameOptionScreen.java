package inf112.app.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import inf112.app.game.RoboRally;

public class GameOptionScreen implements Screen {
    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;

    private boolean multiplayer;

    public GameOptionScreen(RoboRally game, StretchViewport viewport, Stage stage, Boolean multiplayer) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;
        this.multiplayer = multiplayer;
    }

    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true); // Centers the table relative to the stage
        if (multiplayer) {
            VisTextButton playersButton = new VisTextButton("Select Players");
            playersButton.addListener(new ChangeListener() {
                @Override
                public void changed (ChangeEvent event, Actor actor) {
                    //
                }
            });
            table.add(playersButton).pad(3).height(60).width(350);
            table.row();
        }

        else {
            VisTextButton botsNumber = new VisTextButton("Select Bots");
            botsNumber.addListener(new ChangeListener() {
                @Override
                public void changed (ChangeEvent event, Actor actor) {
                    //
                }
            });
            table.add(botsNumber).pad(3).height(60).width(350);
            table.row();
        }

        VisTextButton returnButton = new VisTextButton("Return");
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(game.getLastScreen());
            }
        });

        table.add(returnButton).pad(3).height(60).width(350);
        table.row();
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
