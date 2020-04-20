package inf112.app.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
    private AssetManager manager;

    public Sounds(AssetManager manager) {
        this.manager = manager;
    }

    public void buttonSound(){
        manager.get("assets/Sounds/ButtonClick.wav", Sound.class).play(4.0f);
    }

}
