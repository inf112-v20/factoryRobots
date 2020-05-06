package inf112.app.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;
import inf112.app.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(GdxTestRunner.class)
public class SoundsTest {
    AssetManager manager;
    @Before
    public void setUp() throws GdxRuntimeException {
        manager = new AssetManager();
        manager.load("assets/Sounds/ButtonClick.wav", Sound.class);
        manager.finishLoading();
    }

    @Test
    public void buttonSound() {
        manager.get("assets/Sounds/ButtonClick.wav", Sound.class).play(4.0f);
    }
}