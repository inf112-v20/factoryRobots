package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.building.OneColumnTableBuilder;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.building.utilities.CellWidget;
import com.kotcrab.vis.ui.building.utilities.Padding;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import inf112.app.game.RoboRally;

/**
 * The screen shows instruction to play the game
 */
public class HowToPlayScreen implements Screen {
    private final RoboRally game;
    private final Stage stage;
    private final StretchViewport viewport;

    /**
     * Constructor for HowToPlayScreen screen
     * @param game The RoboRally game
     * @param viewport The viewport for the RoboRally game
     * @param stage The stage for the RoboRally game
     */
    public HowToPlayScreen(final RoboRally game, StretchViewport viewport, Stage stage) {
        this.game = game;
        this.viewport = viewport;
        this.stage = stage;
    }

    /**
     * Method that runs after {@link RoboRally#setScreen(Screen)} is called
     */
    @Override
    public void show() {
        stage.clear();
        // Create window to have text within
        VisWindow window = new VisWindow("How To Play");
        window.setMovable(false); // Make in unmovable
        window.setModal(true);
        window.setHeight(Gdx.graphics.getHeight() - 100); // Set initial size of window
        window.setWidth(Gdx.graphics.getWidth() - 100);
        window.centerWindow();

        OneColumnTableBuilder builder = new OneColumnTableBuilder(); // Create builder to format the text
                                                                     // and return button
        VisLabel text = new VisLabel("");
        text.setText("- Reach all the flags in ascending\n  order to win.\n\n" +
                "- Program the robot using the\n  programming cards to move around.\n\n" +
                "- You have three lifes.\n\n" +
                "- You lose a life by:\n   * Receiving 9 damage tokens\n   * Moving into a hole\n" +
                "   * Moving off the board.\n\n" +
                "- You receive a damage token\n  when you are hit by a laser.\n\n" +
                "- The more damage tokens you have,\n  the less cards you get.\n\n" +
                "- After 5 damage tokens,\n  card slots start locking up");

        VisTextButton returnButton = new VisTextButton("Return");
        Padding paddingReturnButton = new Padding(0,0,15,0);
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.sounds.buttonSound();
                game.setScreen(game.getLastScreen());
            }
        });

        int scaleFactor = 1000 / Gdx.graphics.getWidth(); // Find factor to increase  if width != 1000
        builder.append(CellWidget.of(text) // Build the one column table
                .width(850 * scaleFactor).align(Alignment.CENTER).expandY().wrap());

        builder.append(CellWidget.of(returnButton)
                .padding(paddingReturnButton).width(245 * scaleFactor).align(Alignment.BOTTOM).expandY().wrap());
        Table table = builder.build();

        window.add(table).expand().fill();
        stage.addActor(window);

        stage.addListener(new ClickListener() {
            @Override
            public boolean keyUp (InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE){
                    game.sounds.buttonSound();
                    game.setScreen(game.getLastScreen());
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Called when the screen should render itself.
     * @param v The time in seconds since the last render.
     */
    @Override
    public void render(float v) {
        game.batch.begin();
        game.batch.draw(game.backgroundImg,0,0,viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();

        stage.act();
        stage.draw();
    }

    /**
     * Called when the Application is resized.
     * This can happen at any point during a non-paused state but will never happen before a call to create().
     * @param x The new width in pixels
     * @param y The new height in pixels
     */
    @Override
    public void resize(int x, int y) {
        viewport.update(x, y, true);
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
}
