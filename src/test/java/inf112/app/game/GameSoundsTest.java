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
public class GameSoundsTest {
    AssetManager manager;

    @Before
    public void setUp() throws GdxRuntimeException {
        manager = new AssetManager();
        manager.load("assets/Sounds/TakesDamage.wav", Sound.class);
        manager.load("assets/Sounds/DeathNoise.wav", Sound.class);
        manager.load("assets/Sounds/NewCheckPoint.wav", Sound.class);
        manager.finishLoading();
    }

    @Test
    public void takeDamage() {
        manager.get("assets/Sounds/TakesDamage.wav", Sound.class).play(2.0f);
    }

    @Test
    public void deathSound() {
        manager.get("assets/Sounds/DeathNoise.wav", Sound.class).play(2.0f);
    }

    @Test
    public void checkpoint() {
        manager.get("assets/Sounds/NewCheckPoint.wav", Sound.class).play(2.0f);
    }
}