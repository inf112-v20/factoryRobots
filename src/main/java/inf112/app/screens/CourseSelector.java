package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.app.game.RoboRally;

public class CourseSelector implements Screen {
    OrthographicCamera menuCamera;
    OrthographicCamera mapCamera;
    OrthogonalTiledMapRenderer mapRenderer;
    RoboRally game;
    protected Stage stage;
    public SpriteBatch batch;
    Texture img;
    Skin skin;
    TextureAtlas atlas;

    public CourseSelector(final RoboRally game) {
        this.game = game;
        menuCamera = new OrthographicCamera();
        menuCamera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        FitViewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.apply();
        stage = new Stage();
        stage.setViewport(viewport);


        mapCamera = new OrthographicCamera();
        mapCamera.setToOrtho(false, 30,30);

        //batch = new SpriteBatch();

        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load("assets/testMap.tmx");

        mapRenderer = new OrthogonalTiledMapRenderer(map, (1/300f));
        mapRenderer.setView(mapCamera);
        img = new Texture(Gdx.files.internal("assets/game-menu.png"));
        atlas = new TextureAtlas(Gdx.files.internal("assets/robo-rally-ui-2/robo-rally.atlas"));
        skin = new Skin(Gdx.files.internal("assets/robo-rally-ui-2/robo-rally.json"), atlas);
        //skin = new Skin(Gdx.files.internal("default_skin/uiskin.json"), atlas);


    }

    @Override
    public void show() {
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        /*Dialog dialog = new Dialog("Warning", skin, "default") {
            public void result(Object obj) {
                System.out.println("result "+obj);
            }
        };
        /*dialog.text("blabla");
        dialog.getContentTable().row();
        dialog.getContentTable().add("aowemcw");
        dialog.button("Test");
        dialog.button("test2");
        dialog.show(stage);
        dialog.debug();
        dialog.setSize(300.0f, 200.0f);*/
        //dialog.setPosition(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, Align.center);
        //stage.addActor(dialog);

    }

    @Override
    public void render(float v) {
        menuCamera.update();
        mapCamera.update();

        /*batch.begin();


        //batch.draw(img,0,0);


        batch.end();*/
        stage.act();
        stage.draw();
        //mapRenderer.render();
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
        img.dispose();
        batch.dispose();
        stage.dispose();

    }
}
