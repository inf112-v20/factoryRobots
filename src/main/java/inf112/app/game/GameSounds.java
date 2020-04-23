package inf112.app.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

public class GameSounds {
    private AssetManager manager;

    public GameSounds(AssetManager manager) {
        this.manager = manager;
    }

    public void laserSound() {
        manager.get("assets/Sounds/LaserSound.wav", Sound.class).play(4.0f);
    }

    public void takeDamage() {
        manager.get("assets/Sounds/TakesDamage.wav", Sound.class).play(4.0f);
    }

    public void deathSound() {
        manager.get("assets/Sounds/DeathNoise.wav", Sound.class).play(4.0f);
    }

    public void checkpoint() {
        manager.get("assets/Sounds/NewCheckPoint.wav", Sound.class).play(4.0f);
    }

}
