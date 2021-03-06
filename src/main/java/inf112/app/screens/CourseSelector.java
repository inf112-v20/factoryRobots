package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

import java.util.LinkedList;

/**
 * CourseSelector gives the player the possibility to select map from the menu.
 * It renders all the maps in assets/Maps to the GUI
 */
public class CourseSelector implements Screen {
    private final RoboRally game;
    private final Stage stage;

    private final OrthographicCamera mapCamera;
    private OrthogonalTiledMapRenderer mapRenderer;

    private final StretchViewport menuViewport;
    private final Viewport mapViewport;

    private LinkedList<FileHandle> mapList;
    private int index;

    private VisWindow window;

    /**
     * Constructor for CourseSelector screen
     * @param game The RoboRally game
     * @param viewport The viewport for the RoboRally game
     * @param stage The stage for the RoboRally game
     */
    public CourseSelector(final RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.menuViewport = viewport;
        this.stage = stage;
        mapList = new LinkedList<>();
        FileHandle[] files = Gdx.files.internal("assets/Maps").list();
        for(FileHandle file : files){
            if(file.name().endsWith(".tmx")){
                mapList.add(file);
            }
        }

        // Center the renderer
        this.index = 0;
        double screenSizeX = Gdx.graphics.getWidth();
        double screenSizeY = Gdx.graphics.getHeight();

        // Select the smallest size and create a square from that
        double screenSize = Math.min(screenSizeX, screenSizeY);

        double screenSizeDelta = 1000 / screenSize;

        double paddingFactor = 200 / screenSizeDelta;

        double factor = 5.6 * (screenSizeDelta);
        float unitScale = 1 / ((float) factor);

        int finalSize = (int) (screenSize - paddingFactor);

        mapCamera = new OrthographicCamera(); // Create a new camera for the TiledMap
        mapCamera.setToOrtho(false, finalSize, finalSize); // Center the camera

        mapViewport = new ScreenViewport(mapCamera);

        mapViewport.update((int) screenSizeX, (int) screenSizeY);
        mapViewport.apply(); // Fit camera to viewport

        mapRenderer = new OrthogonalTiledMapRenderer(game.manager.get(files[0].toString()), unitScale); // Create a renderer for rendering of the
        // TiledMap
        mapRenderer.setView(mapCamera);
    }

    /**
     * Method that runs after {@link RoboRally#setScreen(Screen)} is called
     */
    @Override
    public void show() {

        stage.clear();
        window = new VisWindow("Select Course");
        window.setMovable(false); // Make in unmovable
        window.setModal(true);
        window.setHeight(Gdx.graphics.getHeight()); // Set initial size of window
        window.setWidth(Gdx.graphics.getWidth());
        window.centerWindow();
        //window.setPosition((menuViewport.getWorldWidth() / 2.0F), (menuViewport.getWorldHeight() / 2.0F));

        OneRowTableBuilder builder = new OneRowTableBuilder(); // Create a TableBuilder to insert into the window
        VisImageButton leftArrow = new VisImageButton("arrow-left");

        leftArrow.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                mapRenderer.setMap(game.manager.get(getPreviousMap()));
            }
        });
        VisImageButton rightArrow = new VisImageButton("arrow-right");
        rightArrow.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                mapRenderer.setMap(game.manager.get(getNextMap()));
            }
        });

        VisImageTextButton selectButton = new VisImageTextButton("Select","default");
        selectButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                selectCourse();
                //dispose();
                game.setScreen(game.getLastScreen());
                //game.setScreen(new LoadingGameScreen(game, menuViewport, stage));
            }
        });
        VisImageTextButton returnButton = new VisImageTextButton("Return","default");
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                game.setScreen(game.getLastScreen());
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

        // Add input listeners for faster menu traversal
        stage.addListener(new ClickListener() {
            @Override
            public boolean keyUp (InputEvent event, int keycode) {
                switch(keycode){
                    case(Input.Keys.LEFT):
                        game.sounds.buttonSound();
                        mapRenderer.setMap(game.manager.get(getPreviousMap()));
                        break;
                    case(Input.Keys.RIGHT):
                        game.sounds.buttonSound();
                        mapRenderer.setMap(game.manager.get(getNextMap()));
                        break;
                    case(Input.Keys.ESCAPE):
                        game.sounds.buttonSound();
                        game.setScreen(game.getLastScreen());
                        break;
                    case(Input.Keys.ENTER):
                        game.sounds.buttonSound();
                        selectCourse();
                        game.setScreen(game.getLastScreen());
                        break;
                    default:
                        System.out.println("Unassigned input");
                        return false;
                }
                return true;
            }
        });


    }

    /**
     * Called when the screen should render itself.
     * @param v The time in seconds since the last render.
     */
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

    /**
     * Called when the Application is resized.
     * This can happen at any point during a non-paused state but will never happen before a call to create().
     * @param x The new width in pixels
     * @param y The new height in pixels
     */
    @Override
    public void resize(int x, int y) {
        menuViewport.update(x, y, true);
        mapViewport.update(x,y,true);
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

    /**
     *
     * @return The previous map in the map list
     */
    private String getPreviousMap(){
        if (index < 1){
            index = mapList.size()-1;
            return mapList.get(index).toString();
        }
        return mapList.get(--index).toString();

    }

    /**
     *
     * @return The next map in the map list
     */
    private String getNextMap(){
        if (index >= mapList.size() - 1){
            index = 0;
            return mapList.get(index).toString();
        }
        return mapList.get(++index).toString();

    }

    /**
     * Set the course in Game. Uses the current map list index to fetch map name
     */
    private void selectCourse(){
        game.setMapName("Maps/" + mapList.get(index).nameWithoutExtension());
    }
}
