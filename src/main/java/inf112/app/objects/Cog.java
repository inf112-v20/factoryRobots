package inf112.app.objects;
import inf112.app.map.Direction.Rotation;


/**
 * this class makes the object cog turn robots direction on it
 */
public class Cog implements IBoardElement {
    private Rotation rotation;

    /**
     * constructor to rotate direction of robot
     * @param r
     */
    public Cog(Rotation r){
        this.rotation = r;
    }

    @Override
    public void doAction(Robot robot) {
        robot.turn(rotation);
    }
}