package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.building.OneRowTableBuilder;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.building.utilities.CellWidget;
import com.kotcrab.vis.ui.building.utilities.Padding;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import inf112.app.game.RoboRally;

import java.util.Arrays;
import java.util.LinkedList;

public class CourseSelector implements Screen {
    private final RoboRally game;
    private final Stage stage;

    private final OrthographicCamera mapCamera;
    private OrthogonalTiledMapRenderer mapRenderer;

    private final StretchViewport menuViewport;
    private final Viewport mapViewport;

    private LinkedList<FileHandle> mapList;
    private int index;

    VisWindow window;

    public CourseSelector(final RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.menuViewport = viewport;
        this.stage = stage;

        this.mapList = new LinkedList<>();
        FileHandle[] files = Gdx.files.internal("assets/Maps").list();
        mapList.addAll(Arrays.asList(files));

        this.index = 0;

        mapCamera = new OrthographicCamera(); // Create a new camera for the TiledMap
        mapCamera.setToOrtho(false, menuViewport.getWorldWidth()/2f
                ,menuViewport.getWorldHeight()/2f); // Center the camera

        mapViewport = new ScreenViewport(mapCamera);
        mapViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mapViewport.apply(); // Fit camera to viewport

        mapRenderer = new OrthogonalTiledMapRenderer(game.manager.get("assets/Maps/testMap.tmx"), 1/9f); // Create a renderer for rendering of the
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

        leftArrow.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                mapRenderer.setMap(game.manager.get(getPreviousMap()));
            }
        });

        VisImageButton rightArrow = new VisImageButton("arrow-right");
        rightArrow.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                mapRenderer.setMap(game.manager.get(getNextMap()));
            }
        });

        VisImageTextButton selectButton = new VisImageTextButton("Select","default");
        selectButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                selectCourse();
                game.setScreen(new LoadingGameScreen(game, menuViewport, stage));
            }
        });
        VisImageTextButton returnButton = new VisImageTextButton("Return","default");
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game, menuViewport, stage));
            }
        });
        Padding paddingArrow = new Padding(0,0,50,0); // Add padding to buttons
        Padding paddingSelectButton = new Padding(0,2,0,5);
        Padding paddingReturnButton = new Padding(0,5,0,10);
        builder.append(CellWidget.of(leftArrow)
                .padding(paddingArrow).align(Alignment.LEFT).expandX().wrap());
        builder.append(CellWidget.of(selectButton)
                .padding(paddingSelectButton).width(245).align(Alignment.BOTTOM).expandY().wrap());
        builder.append(CellWidget.of(returnButton)
                .padding(paddingReturnButton).width(245).align(Alignment.BOTTOM).expandY().wrap());
        builder.append(CellWidget.of(rightArrow)
                .padding(paddingArrow).align(Alignment.RIGHT).expandX().wrap());

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
        mapList.forEach(asset -> game.manager.unload(asset.toString()));
        game.manager.finishLoading();
    }

    private String getPreviousMap(){
        if (index < 1){
            index = mapList.size()-1;
            return mapList.get(index).toString();
        }
        return mapList.get(--index).toString();

    }

    private String getNextMap(){
        if (index >= mapList.size() - 1){
            index = 0;
            return mapList.get(index).toString();
        }
        return mapList.get(++index).toString();

    }

    private void selectCourse(){
        game.setMapName("Maps/" + mapList.get(index).nameWithoutExtension());
    }
}
