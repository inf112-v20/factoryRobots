package inf112.app.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

public class GameSounds {
    private final RoboRally game;

    public GameSounds(RoboRally game) {
        this.game = game;
    }

    public void takeDamage() {
        game.manager.get("assets/Sounds/TakesDamage.wav", Sound.class).play(2.0f);
    }

    public void deathSound() {
        game.manager.get("assets/Sounds/DeathNoise.wav", Sound.class).play(2.0f);
    }

    public void checkpoint() {
        game.manager.get("assets/Sounds/NewCheckPoint.wav", Sound.class).play(2.0f);
    }

}
