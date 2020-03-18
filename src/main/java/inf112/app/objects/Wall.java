package inf112.app.objects;
import inf112.app.map.Map;
import inf112.app.objects.Direction.Rotation;

import java.util.NoSuchElementException;

/**
 * Class representing a Wall block on the map
 */
public class Wall implements IBoardElement, ILaserInteractor {
    private Direction[] facing;
    private boolean hasLaser;
    private Position position;
    private Laser laser;

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
        fireLaser();
    }

    public Position getPos(){
        return position;
    }

    @Override
    public void fireLaser() {
        if(hasLaser){
            laser.fire();
        }
    }
}
