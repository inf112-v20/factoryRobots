package inf112.app.objects;

import com.badlogic.gdx.math.Vector2;
import inf112.app.map.Map;
import inf112.app.objects.Direction.Rotation;

/**
 * This class is a representation of the robots
 * on the board
 */
public class Robot implements ILaserInteractor {
    private Map map;
    private Position pos;
    private Vector2 vectorPos;

    public Robot(Position pos, Map map){
        this.pos = pos;
        this.map = map;
        vectorPos = new Vector2(pos.getXCoordinate(),pos.getYCoordinate());
    }

    /**
     * Method to move the robot forward in the direction it is facing
     * @param steps Number of steps the robot should take
     */
    public void move(int steps){
        while(steps!=0){
            steps -= 1;
            if(map.validMove(pos)){
                pos.moveInDirection();
            }
        }
        vectorPos.set(pos.getXCoordinate(), pos.getYCoordinate());
    }

    /**
     * Method to change the direction of the robot
     * @param r Enum for which direction the robot should turn, either LEFT or RIGHT
     */
    public void turn(Rotation r){
        pos.getDirection().turn(r);
    }

    public Position getPos() {
        return pos;
    }
}
