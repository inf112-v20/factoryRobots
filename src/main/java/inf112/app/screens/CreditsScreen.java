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

public class CreditsScreen implements Screen {
    private final RoboRally game;
    private final Stage stage;
    private final StretchViewport viewport;

    public CreditsScreen(final RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;
    }

    @Override
    public void show() {
        stage.clear();
        VisTable table = new VisTable();
        table.setFillParent(true);
        VisLabel text = new VisLabel("");
        text.setText("");
        VisTextButton returnButton = new VisTextButton("Return");
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                game.setScreen(game.getLastScreen());
            }
        });
        table.add(text).pad(3).height(viewport.getWorldHeight()/1.5f).width((viewport.getWorldWidth()/4)*3);
        table.row();
        table.add(returnButton).pad(3).height(60).width(600);
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
    public void resize(int i, int i1) {

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
