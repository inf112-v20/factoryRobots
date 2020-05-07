package inf112.app.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Class for holding and triggering sound-effects both in the menu and in-game
 */
public class Sounds {
    private AssetManager manager;

    public Sounds(AssetManager manager) {
        this.manager = manager;
    }

    /**
     * Triggers the button-click sound-effect
     */
    public void buttonSound(){
        manager.get("assets/Sounds/ButtonClick.wav", Sound.class).play(4.0f);
    }

}
