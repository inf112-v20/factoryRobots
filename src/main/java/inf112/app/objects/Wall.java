package inf112.app.objects;
import inf112.app.objects.Direction.Rotation;

/**
 * Class representing a Wall block on the map
 */
public class Wall implements IBoardElement{
    private Direction[] facing;
    private boolean hasLaser;
    private boolean hasDoubleLaser;

    public Wall(int face1, int face2, boolean laser, boolean doubleLaser){
        facing = new Direction[2];
        facing[0] = new Direction(face1);
        facing[1] = new Direction(face2);

        hasLaser = laser;
        hasDoubleLaser = doubleLaser;
    }

    public Direction[] getFacing() {
        return facing;
    }

    public boolean isLaser() {
        return hasLaser;
    }

    public boolean isDoubleLaser() {
        return hasDoubleLaser;
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
}
