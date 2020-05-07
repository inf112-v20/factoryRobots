package inf112.app.objects;
import inf112.app.map.Direction.Rotation;


/**
 * Class representing a cog element on the map
 */
public class Cog implements IBoardElement {
    private final Rotation rotation;

    /**
     * @param r The rotation direction of the cog
     */
    public Cog(Rotation r){
        this.rotation = r;
    }

    /**
     * Rotates the robot in the direction dictated by the {@link #rotation} field
     * @param robot Robot to be rotated
     */
    @Override
    public void doAction(Robot robot) {
        robot.turn(rotation);
    }
}