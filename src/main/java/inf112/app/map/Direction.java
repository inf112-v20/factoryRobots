package inf112.app.map;

public class Direction {
    private int angleDeg;
    private DirEnum direction;

    /**
     * Creates a new direction by a dir enum
     * @param dir Enum corresponding to the direction,
     *            NORTH being up and so forth
     */
    public Direction(DirEnum dir){
        direction = dir;
        angleDeg = translateEnumToAngle(dir);
    }

    /**
     * Creates a new direction object by an angle
     * @param angle The angle of direction. Either 0, 90, 180, or 270
     */
    public Direction(int angle){
        angleDeg = angle;
        direction = translateAngleToEnum(angle);
    }

    /**
     * Translates enum representation for direction to angle in degrees
     * @param dir Enum corresponding to the direction
     * @return The direction in degrees, 0 being up/north
     */
    public static int translateEnumToAngle(DirEnum dir){
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
     * Translates angle in degrees to enum representation
     * @param angle Direction in degrees 0 being north/up
     * @return The direction as an enum
     */
    public static DirEnum translateAngleToEnum(double angle){
        switch((int) angle){
            case 0: return DirEnum.NORTH;
            case 90: return DirEnum.EAST;
            case 180: return DirEnum.SOUTH;
            case 270: return DirEnum.WEST;
            default: throw new IllegalArgumentException("Invalid angle, has to satisfy (angle%90) == 0");
        }

    }

    /**
     * Changes the direction by a matter of 90 degrees
     * @param rot Rotation enum denoting LEFT or RIGHT relative to the object
     */
    public void turn(Rotation rot){
        if (rot == Rotation.LEFT) {
            angleDeg = (angleDeg - 90);
            if(angleDeg < 0){
                angleDeg += 360;
            }
            direction = translateAngleToEnum(angleDeg);
        } else if (rot == Rotation.RIGHT) {
            angleDeg = (angleDeg + 90) % 360;
            direction = translateAngleToEnum(angleDeg);
        } else {
            throw new IllegalArgumentException("Rotation is not valid rotation enum");
        }
    }

    /**
     * Retrieve the angle of the direction
     * @return The angle of the direction
     */
    public int getAngleDeg() {
        return angleDeg;
    }


    /**
     * Checks if the direction is the same
     * @param o The direction to compare with
     * @return True if the same, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction1 = (Direction) o;
        return Double.compare(direction1.angleDeg, angleDeg) == 0 &&
                direction == direction1.direction;
    }

    /**
     * Makes a copy of the direction
     * @return A copy of the provided direction
     */
    public Direction copyOf(){
        return new Direction(direction);
    }

    /**
     * Format the direction
     * @return Formatted string of the direction
     */
    @Override
    public String toString() {
        return "Direction{" +
                "direction=" + direction +
                '}';
    }

    /**
     * Retrieve the direction Enum. Either North, East, South, or West
     * @return The direction Enum
     */
    public DirEnum getDirEnum() {
        return direction;
    }

    /**
     * Enum for rotation
     */
    public enum Rotation {
        LEFT, RIGHT
    }

    /**
     * Enum for cardinal directions
     */
    public enum DirEnum {
        NORTH, EAST, SOUTH, WEST
    }
}
