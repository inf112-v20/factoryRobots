package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.StandardTableBuilder;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.*;
import inf112.app.game.RoboRally;

public class TestScreen implements Screen {
    RoboRally game;
    OrthographicCamera camera;
    Viewport viewport;
    Stage stage;
    Skin skin;

    public TestScreen(final RoboRally game) {
        this.game = game;
        this.skin = game.skin;
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.apply();

        this.stage = new Stage(viewport);
        Gdx.input.setInputProcessor(this.stage);
        VisUI.load(this.skin);
        VisUI.setDefaultTitleAlign(Align.center);

    }

    @Override
    public void show() {
        VisWindow window = new VisWindow("Select Course");
        window.setMovable(false);
        window.setModal(true);
        window.setWidth(550);
        window.setHeight(550);
        window.centerWindow();

        StandardTableBuilder builder = new StandardTableBuilder();
        builder.append(new VisLabel("Test"));
        builder.row();
        Table table = builder.build();

        window.add(table);
        stage.addActor(window);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(game.backgroundImg,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.end();

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int x, int y) {
        camera.position.set((float) x / 2, y / 2.0f, 0.0f);
        stage.getViewport().update(x, y, true);
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
        game.batch.dispose();
        stage.dispose();

    }
    public Table createTable(TextButton... buttons){
        Table table = new Table();
        for (TextButton button : buttons){
            table.add(button).pad(10).width(330).height(60);
            table.row();
        }
        table.debug();
        table.setFillParent(true);
        return table;
    }
}
