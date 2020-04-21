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
import inf112.app.game.RoboRally;

import java.util.ArrayList;

public class ServerLobbyScreen implements Screen, MultiplayerScreen {
    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;

    private String serverIP;
    private VisLabel alert = new VisLabel("");

    public ServerLobbyScreen(RoboRally game, StretchViewport viewport, Stage stage, String ip) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;

        serverIP = ip;
    }

    public void updatePlayerList(ArrayList<String> userList){
        // This is called from the client whenever the server sends an updated playerlist
        // TODO Implement check for new players
        // TODO Update "Waiting..." labels with player names
    }

    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true); // Centers the table relative to the stage

        // Button table
        VisTable buttonTable = new VisTable();
        VisTextButton cancelButton = new VisTextButton("Cancel");
        VisTextButton readyButton = new VisTextButton("Ready"); //Changed to ready button so we don't need a different screen for host and client
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if(game.isHost){
                    game.shutdownServer();
                }
                game.client.disconnect();
                game.client = null;
                game.setScreen(new MainMenuScreen(game, viewport, stage));
            }
        });
        readyButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                // TODO validate that course have been set. Maybe use default map
                game.setScreen(new LoadingGameScreen(game, viewport, stage));
            }
        });
        buttonTable.add(cancelButton).pad(3).height(60).width(350);
        buttonTable.add(readyButton).pad(3).height(60).width(350);

        table.row();
        for (int i = 0; i < 8; i++){
            VisTextButton button = new VisTextButton("Waiting...","text");
            table.add(button).pad(3).height(60).width(700);
            table.row();
        }

        table.add(buttonTable);
        table.row();
        table.add(alert);
        stage.addActor(table);

        stage.addActor(new VisLabel("Server ip: " + serverIP)); //bottom left corner
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

    @Override
    public void alertUser(String info) {
        alert.setText(info); //TODO implement so that this is displayed
    }
}
