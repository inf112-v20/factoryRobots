package inf112.app.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import inf112.app.game.RoboRally;
import inf112.app.util.TableBuilder;

import java.util.ArrayList;

public class ServerLobbyScreen implements Screen, MultiplayerScreen {
    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;

    private String serverIP;
    private VisLabel alert = new VisLabel("");
    ArrayList<String> userList = new ArrayList<>(8);

    public ServerLobbyScreen(RoboRally game, StretchViewport viewport, Stage stage, String ip) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;

        serverIP = ip;
        if(game.client.getUserList() != null){
            this.userList = game.client.getUserList();
        }

    }

    public void updatePlayerList(ArrayList<String> userList){
        this.userList = userList;
        this.show();

    }

    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true); // Centers the table relative to the stage

        // Button table
        VisTable buttonTable = new VisTable();
        VisTextButton cancelButton = new VisTextButton("Cancel");
        VisTextButton readyButton = new VisTextButton("Ready");
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if(game.isHost){
                    game.shutdownServer();
                    game.isHost = false;
                }
                game.client.disconnect();
                game.client = null;
                game.setScreen(new MainMenuScreen(game, viewport, stage));
            }
        });
        readyButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.client.sendReady();
            }
        });
        TableBuilder.row(buttonTable, cancelButton, readyButton);

        VisTextButton[] buttonList = new VisTextButton[8];
        for (int i = 0; i < userList.size(); i++) {
            buttonList[i] = new VisTextButton(userList.get(i), "text");
        }
        for (int j = userList.size(); j < 8; j++){
            buttonList[j] = new VisTextButton("Waiting...","text");
        }

        TableBuilder.column(table, buttonList);
        table.add(buttonTable);
        table.row();
        table.add(alert);
        stage.addActor(table);
        //bottom left corner
        stage.addActor(new VisLabel("Server ip: " + serverIP));
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
        alert.setText(info);
    }
}
