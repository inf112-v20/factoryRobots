package inf112.app.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import inf112.app.game.RoboRally;
import inf112.app.networking.RoboClient;
import inf112.app.networking.RoboServer;

import java.io.IOException;

public class HostGameScreen implements Screen {
    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;
    private final RoboServer server;

    public HostGameScreen(RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;
        this.server = new RoboServer();
        try{
            game.client = new RoboClient(game,viewport,stage,"localhost");
        } catch (IOException e){
            System.out.println("Unable to connect to localhost");
        }

    }

    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true); // Centers the table relative to the stage
        VisTextButton courseButton = new VisTextButton("Select Course");
        courseButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new CourseSelector(game, viewport, stage));
            }
        });
        VisTextButton optionButton = new VisTextButton("Game Options"); // TODO change out button to use same screen
        optionButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new GameOptionScreen(game, viewport, stage, true));
            }
        });
        VisTextButton cancelButton = new VisTextButton("Return");
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game, viewport, stage));
            }
        });
        VisTextButton startButton = new VisTextButton("Start");
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new ServerLobbyScreen(game, viewport, stage));
            }
        });
        VisTable buttonTable = new VisTable();
        buttonTable.add(cancelButton).pad(3).height(60).width(350);
        buttonTable.add(startButton).pad(3).height(60).width(350);

        table.add(courseButton).pad(3).height(60).width(700);
        table.row();
        table.add(optionButton).pad(3).height(60).width(700);
        table.row();
        table.add(buttonTable).pad(3).height(60).width(700);
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
