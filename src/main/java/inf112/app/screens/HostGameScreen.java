package inf112.app.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;
import inf112.app.game.RoboRally;
import inf112.app.util.TableBuilder;
import inf112.app.networking.RoboClient;
import inf112.app.networking.RoboServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class HostGameScreen implements Screen {
    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;
    private VisValidatableTextField playerName;

    public HostGameScreen(RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;
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
                game.sounds.buttonSound();
                game.setScreen(new CourseSelector(game, viewport, stage));
            }
        });
        VisTextButton flagButton = new VisTextButton("Number of Flags: 3");
        flagButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                // TODO
            }
        });
        VisTextButton cancelButton = new VisTextButton("Return");
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                if(!playerName.isEmpty()){
                    game.setPlayerName(playerName.getText());
                }
                game.setScreen(new MainMenuScreen(game, viewport, stage));
            }
        });
        VisTextButton startButton = new VisTextButton("Start");
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                if(!playerName.isEmpty()){
                    game.setPlayerName(playerName.getText());
                }
                game.launchServer();
                String ip = "";
                try{
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress("1.1.1.1", 80));
                    ip = socket.getLocalAddress().getHostAddress();
                } catch (IOException e){
                    System.out.println("Couldn't obtain ip");
                }
                game.isHost = true;
                game.setScreen(new ServerLobbyScreen(game, viewport, stage, ip));
            }
        });

        playerName = new VisValidatableTextField(); // TODO implement validator
        VisLabel name = new VisLabel("Player Name: ");
        name.setAlignment(Align.center); // Align text to center
        playerName.setAlignment(Align.center);
        TableBuilder.column(table, name, playerName, flagButton);
        VisTable buttonTable = new VisTable();
        TableBuilder.row(buttonTable, cancelButton, startButton);
        TableBuilder.column(table, courseButton, buttonTable);
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
