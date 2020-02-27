package inf112.app.objects;

import com.badlogic.gdx.Game;
import inf112.app.map.Map;
import inf112.app.objects.Direction.Rotation;

/**
 * this class is a representation of the robots
 * on the board
 */
public class Robot extends Game {
    private Map map;
    private Position pos;

    /**
     * constructor for the robot
     * @param pos
     * @param map
     */
    public Robot(Position pos, Map map){
        this.pos = pos;
        this.map = map;
    }

    @Override
    public void create() {

    }

    /**
     * method to change the possition of the robot
     * @param steps
     */
    public void move(int steps){
        while(steps!=0){
            steps -= 1;
            if(map.validMove(pos)){
                pos.moveInDirection();
            }
        }
    }

    /**
     * method to change the direction of the robot
     * @param r
     */
    public void turn(Rotation r){
        pos.getDirection().turn(r);
    }

    /**
     * @return the position of the robot
     */
    public Position getPos() {
        return pos;
    }
}
