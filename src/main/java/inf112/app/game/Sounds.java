package inf112.app.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
    private AssetManager manager;

    public Sounds(AssetManager manager) {
        this.manager = manager;
    }

    public void buttonSound(){
        try {
            manager.get("assets/Sounds/ButtonClick.wav", Sound.class).play(4.0f);
        } catch (NullPointerException ignored){ // Catch exception for test classes

        }
    }

    public void checkpoint() {
        try {
            manager.get("assets/Sounds/NewCheckPoint.wav", Sound.class).play(4.0f);
        } catch (NullPointerException ignored){ // Catch exception for test classes

        }
    }

    public void takeDamage() {
        try {
            manager.get("assets/Sounds/TakesDamage.wav", Sound.class).play(4.0f);
        } catch (NullPointerException ignored){ // Catch exception for test classes

        }
    }

    public void deathSound() {
        try {
            manager.get("assets/Sounds/DeathNoise.wav", Sound.class).play(4.0f);
        } catch (NullPointerException ignored){ // Catch exception for test classes

        }
    }

    public void laserSound() {
        try {
            manager.get("assets/Sounds/LaserSound.wav", Sound.class).play(4.0f);
        } catch (NullPointerException ignored){ // Catch exception for test classes

        }
    }
}
