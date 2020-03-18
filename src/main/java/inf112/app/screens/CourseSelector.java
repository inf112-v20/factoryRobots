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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisWindow;
import inf112.app.game.RoboRally;

public class CourseSelector implements Screen {
    RoboRally game;
    protected Stage stage;
    Skin skin;

    OrthographicCamera menuCamera;
    OrthographicCamera mapCamera;
    OrthogonalTiledMapRenderer mapRenderer;

    Viewport menuViewport;
    Viewport mapViewport;

    VisWindow window;

    public CourseSelector(final RoboRally game) {
        this.game = game;
        this.skin = game.skin;

        VisUI.load(this.skin);
        VisUI.setDefaultTitleAlign(Align.center);

        menuCamera = new OrthographicCamera();
        menuViewport = new ScreenViewport(menuCamera);
        menuViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        menuViewport.apply();

        mapCamera = new OrthographicCamera();
        mapCamera.setToOrtho(false, Gdx.graphics.getWidth()/2f
                ,Gdx.graphics.getHeight()/1.7f);


        mapViewport = new ScreenViewport(mapCamera);
        mapViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mapViewport.apply();

        this.stage = new Stage(menuViewport);
        stage.setDebugAll(true);
        Gdx.input.setInputProcessor(this.stage);

        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load("assets/testMap.tmx");

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/9f);
        mapRenderer.setView(mapCamera);

    }

    @Override
    public void show() {
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        window = new VisWindow("Select Course");
        window.setMovable(false);
        window.setModal(true);
        centerWindowTable();

        /*StandardTableBuilder builder = new StandardTableBuilder();
        builder.append(new VisLabel("Test"));
        builder.row();
        Table table = builder.build();

        window.add(table);*/
        stage.addActor(window);

    }

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        menuCamera.update();
        mapCamera.update();
        game.batch.setProjectionMatrix(menuCamera.combined);

        game.batch.begin();
        game.batch.draw(game.backgroundImg,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.end();

        stage.act();
        stage.draw();
        mapRenderer.render();
    }

    @Override
    public void resize(int x, int y) {
        menuCamera.position.set((float) x / 2, y / 2.0f, 0.0f);
        menuViewport.update(x, y, true);
        centerWindowTable();
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
        VisUI.dispose();
    }
    private void centerWindowTable(){
        window.setHeight(Gdx.graphics.getHeight()*(2.1f/3f));
        window.setWidth(Gdx.graphics.getWidth()*(3/4f));
        window.setPosition((stage.getWidth() - window.getWidth()) / 2F
               , (stage.getHeight() - window.getHeight()) / 3.2F);
    }
}
