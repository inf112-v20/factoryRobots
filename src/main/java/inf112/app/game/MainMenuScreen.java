package inf112.app.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen implements Screen {

    final RoboRally game;
    OrthographicCamera camera;

    private Texture img;
    protected Stage stage;
    private TextureAtlas atlas;
    protected Skin skin;
    private FitViewport viewport;
    public SpriteBatch batch;

    public MainMenuScreen(final RoboRally game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewport.apply();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas(Gdx.files.internal("assets/robo-rally-ui/Robo-Rally.atlas"));
        skin = new Skin(Gdx.files.internal("assets/robo-rally-ui/Robo-Rally.json"), atlas);
        img = new Texture(Gdx.files.internal("assets/game-menu.png"));

        // Create buttons
        Table table = new Table();
        TextButton playButton = new TextButton("Play", skin);
        TextButton multiPlayer = new TextButton("Multiplayer", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        // Add clicklisteners to the buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Add buttons to the table

        table.add(playButton).pad(10).width(300).height(50);
        table.row();
        table.add(multiPlayer).pad(10).width(300).height(50);
        table.row();
        table.add(optionsButton).pad(10).width(300).height(50);
        table.row();
        table.add(exitButton).pad(10).width(300).height(50);
        //table.debug();
        table.setFillParent(true);

        stage.addActor(table);


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
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

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
    }

}