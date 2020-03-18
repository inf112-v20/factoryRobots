package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.app.game.RoboRally;
import inf112.app.map.Map;
import sun.font.CoreMetrics;

public class MainMenuScreen implements Screen {

    final RoboRally game;
    OrthographicCamera camera;

    private Texture img;
    protected Stage stage;
    private TextureAtlas atlas;
    protected Skin skin;
    public SpriteBatch batch;

    public MainMenuScreen(final RoboRally game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        batch = new SpriteBatch();
        FitViewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.apply();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas(Gdx.files.internal("assets/Skins/robo-rally-ui/Robo-Rally.atlas"));
        skin = new Skin(Gdx.files.internal("assets/Skins/robo-rally-ui/Robo-Rally.json"), atlas);
        img = new Texture(Gdx.files.internal("assets/game-menu.png"));
        showMenuScreen();
    }

    public void showMenuScreen(){
        stage.clear();
        TextButton playButton = new TextButton("Play", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        // Add clicklisteners to the buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPlayScreen();
            }
        });
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showOptionsScreen();
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Add buttons to the table
        stage.addActor(createTable(playButton, optionsButton, exitButton));
    }
    public void showOptionsScreen(){
        stage.clear();

        TextButton soundButton = new TextButton("Sound", skin);
        TextButton returnButton = new TextButton("Return", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        soundButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // We need to implement sound first
            }
        });
        returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showMenuScreen();
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(createTable(soundButton,returnButton, exitButton));
    }

    public void showPlayScreen(){
        stage.clear();
        TextButton singlePlayer = new TextButton("Singleplayer", skin);
        TextButton multiPlayer = new TextButton("Multiplayer", skin);
        TextButton returnButton = new TextButton("Return", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        singlePlayer.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showCourseSelector(false);
            }
        });
        multiPlayer.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showCourseSelector(true);
            }
        });
        returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showMenuScreen();
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(createTable(singlePlayer, multiPlayer, returnButton, exitButton));
    }
    public void showCourseSelector(boolean multiplayer){
        stage.clear();
        dispose();
        game.setScreen(new CourseSelector(game));

        /*TextButton standardCourseButton = new TextButton("Test Course", skin);
        TextButton returnButton = new TextButton("Return", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        standardCourseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                // We need to implement multiplayer first
                game.setScreen(new GameScreen(game));
            }
        });
        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load("assets/testMap.tmx");
        OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(map, (1/300f));
        mapRenderer.setView(camera);
        mapRenderer.render();

        /*returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPlayScreen();
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(createTable(standardCourseButton, returnButton, exitButton));*/
    }


    @Override
    public void render(float v) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(img,0,0);
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        skin.dispose();
        atlas.dispose();
        batch.dispose();
        stage.dispose();
    }

    public Table createTable(TextButton... buttons){
        Table table = new Table();
        for (TextButton button : buttons){
            table.add(button).pad(10).width(330).height(60);
            table.row();
        }
        table.setFillParent(true);
        return table;

    }

}