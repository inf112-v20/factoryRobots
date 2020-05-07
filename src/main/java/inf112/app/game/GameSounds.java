package inf112.app.game;

import com.badlogic.gdx.audio.Sound;

/**
 * Class for holding and triggering in-game sound effects
 */
public class GameSounds {
    private final RoboRally game;

    public GameSounds(RoboRally game) {
        this.game = game;
    }

    /**
     * Method for triggering the take-damage sound-effect
     */
    public void takeDamage() {
        game.manager.get("assets/Sounds/TakesDamage.wav", Sound.class).play(2.0f);
    }

    /**
     * Method for triggering the robot-death sound-effect
     */
    public void deathSound() {
        game.manager.get("assets/Sounds/DeathNoise.wav", Sound.class).play(2.0f);
    }

    /**
     * Method for triggering the new checkpoint sound-effect
     */
    public void checkpoint() {
        game.manager.get("assets/Sounds/NewCheckPoint.wav", Sound.class).play(2.0f);
    }

}
