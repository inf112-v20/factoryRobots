package inf112.app.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import inf112.app.game.RoboRally;
import inf112.app.util.TableBuilder;

import java.util.ArrayList;

/**
 * Lobby screen to show which player has joined the lobby. Also gives the host the ability to launch the game
 */
public class ServerLobbyScreen implements Screen, MultiplayerScreen {
    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;

    private String serverIP;
    private VisLabel alert = new VisLabel("");
    ArrayList<String> userList = new ArrayList<>(8);

    /**
     * Constructor for ServerLobbyScreen screen
     * @param game The RoboRally game
     * @param viewport The viewport for the RoboRally game
     * @param stage The stage for the RoboRally game
     */
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

    /**
     * Method that runs after {@link RoboRally#setScreen(Screen)} is called
     */
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
                cancelLobby();
            }
        });
        readyButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
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

        stage.addListener(new ClickListener() {
            @Override
            public boolean keyUp (InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE){
                    cancelLobby();
                    return true;
                }
                else if (keycode == Input.Keys.ENTER){
                    game.sounds.buttonSound();
                    game.client.sendReady();
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

    @Override
    public void alertUser(String info) {
        alert.setText(info);
    }

    /**
     * cancels the lobby and shuts down the server
     */
    private void cancelLobby(){
        game.sounds.buttonSound();
        if(game.isHost){
            game.shutdownServer();
            game.isHost = false;
        }
        game.client.disconnect();
        game.client = null;
        game.setScreen(new MainMenuScreen(game, viewport, stage));
    }
}
