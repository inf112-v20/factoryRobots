package inf112.app.objects;

import com.badlogic.gdx.Game;
import inf112.app.map.Map;
import inf112.app.objects.Direction.Rotation;

public class Robot extends Game {
    private Map map;
    private Position pos;

    public Robot(Position pos, Map map){
        this.pos = pos;
        this.map = map;
    }

    @Override
    public void create() {

    }

    public void move(int steps){
        while(steps!=0){
            steps -= 1;
            if(map.validMove(pos)){
                pos.moveInDirection();
            }
        }
    }

    public void turn(Rotation r){
        pos.getDirection().turn(r);
    }

    public Position getPos() {
        return pos;
    }
}
