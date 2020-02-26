package inf112.app.objects;

public class Direction {
    private int angleDeg;
    private DirEnum direction;

    /**
     * Creates a new direction object
     * @param dir Enum corresponding to the direction,
     *            NORTH being up and so forth
     */
    public Direction(DirEnum dir){
        direction = dir;
        angleDeg = translateEnumToAngle(dir);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction1 = (Direction) o;
        return Double.compare(direction1.angleDeg, angleDeg) == 0 &&
                direction == direction1.direction;
    }

    public Direction copyOf(){
        return new Direction(direction);
    }

    @Override
    public String toString() {
        return "Direction{" +
                "direction=" + direction +
                '}';
    }

    public DirEnum getDirEnum() {
        return direction;
    }

    public enum Rotation {
        LEFT, RIGHT
    }

    public enum DirEnum {
        NORTH, EAST, SOUTH, WEST
    }
}
