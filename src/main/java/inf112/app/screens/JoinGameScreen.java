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
import inf112.app.networking.RoboClient;

import java.io.IOException;
import inf112.app.util.TableBuilder;

public class JoinGameScreen implements Screen {
    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;
    private VisLabel message;

    public JoinGameScreen(RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;

        message = new VisLabel("");
    }

    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true); // Centers the table relative to the stage
        VisValidatableTextField ipField = new VisValidatableTextField(); // TODO implement validator
        VisValidatableTextField playerName = new VisValidatableTextField(); // TODO implement validator
        VisTextButton cancelButton = new VisTextButton("cancel");
        VisTextButton acceptButton = new VisTextButton("accept");

        VisTable buttonTable = new VisTable();
        TableBuilder.row(buttonTable, cancelButton, acceptButton);

        acceptButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                // ipField.toString(); -> Ip address
                // TODO Validate input and join game
                // TODO Make input more intuitive with error labels and colors
                RoboClient client = null;
                try{
                    String ip = ipField.getText();
                    client = new RoboClient(game,viewport,stage,ip,playerName.getText());
                    message.setText("Connecting...");
                    while(!client.serverReject && !client.serverAccept){
                        continue;   //Wait for server reply
                    }
                    if(client.serverReject){
                        message.setText(client.serverMessage);
                        client = null;
                    } else if (client.serverAccept){
                        game.client = client;
                        game.setScreen(new ServerLobbyScreen(game,viewport,stage,ip));
                    }
                }catch (IOException e){
                    System.out.println("Unable to connect to " + ipField.getText());
                    message.setText("Unable to connect to " + ipField.getText());
                }

            }
        });

        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game, viewport, stage));
            }
        });
        message.setAlignment(Align.center);
        table.add(message);
        table.row();
        VisLabel name = new VisLabel("Player Name: ");
        name.setAlignment(Align.center); // Align text to center
        playerName.setAlignment(Align.center);
        TableBuilder.column(table, name, playerName);

        VisLabel info = new VisLabel("Ip Address: ");
        info.setAlignment(Align.center); // Align text to center
        ipField.setAlignment(Align.center);
        TableBuilder.column(table, info, ipField, buttonTable);
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
