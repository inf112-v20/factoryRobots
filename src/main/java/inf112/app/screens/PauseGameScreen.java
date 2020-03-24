package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.building.OneColumnTableBuilder;
import com.kotcrab.vis.ui.building.utilities.Padding;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import inf112.app.game.RoboRally;

public class PauseGameScreen implements Screen {
    private final Stage stage;

    private final RoboRally game;
    private final StretchViewport viewport;

    public PauseGameScreen(RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;
    }

    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true);
        VisLabel title = new VisLabel("The game is paused!");
        VisImageTextButton resumeButton = new VisImageTextButton("Resume", "default");
        table.add(title);

        OneColumnTableBuilder builder = new OneColumnTableBuilder(); // Create a TableBuilder to insert into the window
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.resume();
            }
        });
        VisImageTextButton exitButton = new VisImageTextButton("Exit", "default");
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        Padding padding = new Padding(50,0,0,0);
        builder.append(resumeButton,exitButton).setTablePadding(padding);
        table.row();
        table.add(builder.build());

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
