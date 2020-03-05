package inf112.app.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MainMenuScreen implements Screen {

    final RoboRally game;

    OrthographicCamera camera;
    Skin skin = new Skin();
    TextureRegion test;

    public MainMenuScreen(final RoboRally game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

    @Override
    public void show() {
        //test = new TextureRegion( new Texture(Gdx.files.internal("game-menu.png")));

    }

    @Override
    public void render(float v) {
        Color x = Color.LIGHT_GRAY;
        Gdx.gl.glClearColor(x.r, x.g, x.b, x.a );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        /*FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(""));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.draw(game.batch, "Robo Rally", 25, 160);*/
        //game.batch.draw(test,50,-200);

        //game.font.draw(game.batch, "Welcome to Robo Rally!!! ", 500, 500);
        //game.font.draw(game.batch, "Tap anywhere to begin!", 500, 400);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
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

    //...Rest of class omitted for succinctness.

}