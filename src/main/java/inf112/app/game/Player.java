package inf112.app.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import inf112.app.map.Map;
import inf112.app.map.Position;
import inf112.app.map.Direction.Rotation;
import inf112.app.objects.Robot;

/**
 * Class for the player which the clients user controls,
 * not to be confused by the player character, which is the {@link Robot} class
 */
public class Player extends InputAdapter {
    private Robot character;


    /**
     * The single constructor for Player
     * @param x X-coordinate of the player character
     * @param y Y-coordinate of the player character
     */
    public Player(int x, int y){
        character = new Robot(new Position(x,y),"player");
    }

    public Player(){
        //Do nothing
    }

    public void assignRobot(Robot robot){
        character = robot;
    }

    public Robot getCharacter(){
        return character;
    }

    /**
     * Handles keyboard in-game keyboard input, used for testing and debugging
     * If {@link RoboRally#DEBUG} is false then nothing will happen
     * @param keycode Code for key that is being released
     * @return true if key is being released, false if not
     */
    @Override
    public boolean keyUp(int keycode) {
        if(!RoboRally.DEBUG){
            return false;
        }
        switch (keycode) {
            case Input.Keys.LEFT:
                character.turn(Rotation.LEFT);
                break;
            case Input.Keys.RIGHT:
                character.turn(Rotation.RIGHT);
                break;
            case Input.Keys.UP:
                character.move(1);
                break;
            case Input.Keys.SPACE:
                Map.getInstance().getCellList().getCell(character.getPos()).doAction(character);
                break;
            case Input.Keys.L:
                Map.getInstance().fireLasers();
                break;
            default:
                System.out.println("Unassigned input");
                break;
        }
        return false;
    }
}
