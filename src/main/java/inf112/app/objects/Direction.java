package inf112.app.objects;

public class Direction {
    private double angleDeg;

    /**
     * Creates a new direction object
     * @param dir Enum corresponding to the direction,
     *            NORTH being up and so forth
     */
    public Direction(DirEnum dir){
        angleDeg = translateDir(dir);
    }

    /**
     * Translates enum representation for direction to angle in degrees
     * @param dir Enum corresponding to the direction
     * @return The direction in degrees, 0 being up/north
     */
    public static double translateDir(DirEnum dir){
        switch(dir){
            case NORTH:
                return 0;
            case WEST:
                return 270;
            case EAST:
                return 90;
            case SOUTH:
                return 180;
            default:
                throw new IllegalArgumentException("Direction is not a recognized enum");
        }
    }

    /**
     * Changes the direction by a matter of 90 degrees
     * @param rot Rotation enum denoting LEFT or RIGHT relative to the object
     */
    public void turn(Rotation rot){
        if (rot == Rotation.LEFT) {
            angleDeg = (angleDeg - 90) % 360;
        } else if (rot == Rotation.RIGHT) {
            angleDeg = (angleDeg + 90) % 360;
        } else {
            throw new IllegalArgumentException("Rotation is not valid rotation enum");
        }
    }
}
