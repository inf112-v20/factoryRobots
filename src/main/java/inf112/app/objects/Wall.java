package inf112.app.objects;
import inf112.app.map.Direction;
import inf112.app.map.Position;
import inf112.app.map.Direction.Rotation;

/**
 * Class representing a Wall block on the map
 */
public class Wall implements IBoardElement, ILaserInteractor {
    private Direction[] facing;
    private boolean hasLaser;
    private Position position;
    private Laser laser;

    /**
     * Creates a Wall object instance
     * @param face1 The direction in the tile the wall is facing
     * @param face2 The second direction in the tile the wall is facing, since walls can be L-shaped
     *              If it is just a straight wall then face2 should be the same as face1
     * @param hasLaser boolean designating if the wall has a laser mounted to the wall
     * @param doubleLaser boolean designating if the laser mounted to the wall is a double laser
     * @param x x-coordinate of the wall
     * @param y y-coordinate of the wall
     */
    public Wall(int face1, int face2, boolean hasLaser, boolean doubleLaser, int x, int y){
        facing = new Direction[2];
        facing[0] = new Direction(face1);
        facing[1] = new Direction(face2);

        Direction laserDir = facing[0].copyOf();
        laserDir.turn(Rotation.LEFT);
        laserDir.turn(Rotation.LEFT);

        this.hasLaser = hasLaser;
        this.position = new Position(x,y,laserDir);
        if(hasLaser){
            this.laser = new Laser(this,doubleLaser);
        } else {
            this.laser = null;
        }
    }

    /**
     * @return true if the wall has a laser mounted to it, false if not
     */
    public boolean isLaser() {
        return hasLaser;
    }

    /**
     * Checks if the wall is blocking in a particular direction
     * @param currentPos true if the check is made from the current tile, i.e. the tile holding the wall,
     *                   false if the wall is on the tile the object is moving onto
     * @param direction Direction to check if there is a wall, assuming an immutable direction object,
     *                  make sure you pass the direction using the {@link Direction#copyOf()} method
     * @return true if the wall is blocking in the direction, false if not
     */
    public boolean blocks(boolean currentPos, Direction direction){
        if(currentPos){
            return direction.equals(facing[0]) || direction.equals(facing[1]);
        }
        direction.turn(Rotation.LEFT);
        direction.turn(Rotation.LEFT);
        return direction.equals(facing[0]) || direction.equals(facing[1]);
    }

    @Override
    public void doAction(Robot robot) {
        //do nothing
    }

    public Position getPos(){
        return position;
    }

    /**
     * If the wall has a laser mounted to it, then the method will trigger the laser to fire
     */
    @Override
    public void fireLaser() {
        if(hasLaser){
            laser.fire();
        }
    }
}
