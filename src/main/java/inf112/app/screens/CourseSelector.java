package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.OneRowTableBuilder;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.building.utilities.CellWidget;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import inf112.app.game.RoboRally;

public class CourseSelector implements Screen {
    RoboRally game;
    protected Stage stage;
    Skin skin;

    OrthographicCamera mapCamera;
    OrthogonalTiledMapRenderer mapRenderer;

    StretchViewport menuViewport;
    Viewport mapViewport;

    VisWindow window;

    public CourseSelector(final RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.menuViewport = viewport;
        this.stage = stage;
        skin = game.skin;

        mapCamera = new OrthographicCamera(); // Create a new camera for the TiledMap
        mapCamera.setToOrtho(false, menuViewport.getWorldWidth()/2f
                ,menuViewport.getWorldHeight()/2f); // Center the camera

        mapViewport = new ScreenViewport(mapCamera);
        mapViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mapViewport.apply(); // Fit camera to viewport

        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load("assets/testMap.tmx");

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/9f); // Create a renderer for rendering of the
                                                                          // TiledMap
        mapRenderer.setView(mapCamera);

    }

    @Override
    public void show() {
        stage.clear();
        window = new VisWindow("Select Course");
        window.setMovable(false); // Make in unmovable
        window.setModal(true);
        window.setHeight(650); // Set initial size of window
        window.setWidth(800);
        window.centerWindow();

        OneRowTableBuilder builder = new OneRowTableBuilder(); // Create a TableBuilder to insert into the window
        VisImageButton leftArrow = new VisImageButton("arrow-left");
        leftArrow.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                return false;
            }
        });
        VisImageButton rightArrow = new VisImageButton("arrow-right");
        rightArrow.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                return false;
            }
        });
        VisImageTextButton selectButton = new VisImageTextButton("Select","default");
        selectButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new GameScreen(game));
            }
        });
        VisImageTextButton returnButton = new VisImageTextButton("Return","default");
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game, menuViewport, stage));
            }
        });

        builder.append(CellWidget.of(leftArrow).align(Alignment.LEFT).expandX().wrap());
        builder.append(CellWidget.of(selectButton).align(Alignment.BOTTOM).expandY().wrap());
        builder.append(CellWidget.of(returnButton).align(Alignment.BOTTOM).expandY().wrap());
        builder.append(CellWidget.of(rightArrow).align(Alignment.RIGHT).expandX().wrap());

        Table table = builder.build(); // Create table from the TableBuilder
        window.add(table).expand().fill();
        stage.addActor(window); // Add window to the stage for rendering

    }

    @Override
    public void render(float v) {
        mapCamera.update();

        game.batch.begin();
        game.batch.draw(game.backgroundImg,0,0,menuViewport.getWorldWidth(),menuViewport.getWorldHeight());
        game.batch.end();

        stage.act();
        stage.draw();
        mapRenderer.render();
    }

    @Override
    public void resize(int x, int y) {
        menuViewport.update(x, y, true);
        mapViewport.update(x,y,true);
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
