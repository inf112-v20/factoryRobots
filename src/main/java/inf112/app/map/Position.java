package inf112.app.map;

import java.util.Objects;

import inf112.app.map.Direction.DirEnum;

/**
 * Class for representing the objects position on the grid
 * as well as its direction
 */
public class Position {
    private int xCoordinate;
    private int yCoordinate;
    private Direction direction;

    /**
     * Constructor where you only pass coordinates,
     * sets the facing direction to the standard: NORTH/UP
     * @param xCoordinate Horizontal coordinate from the left
     * @param yCoordinate Vertical coordinate from the bottom
     */
    public Position(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.direction = new Direction(DirEnum.NORTH);
    }

    /**
     * Constructor where you can set the direction as well as coordinates
     * @param xCoordinate Horizontal coordinate from the left
     * @param yCoordinate Vertical coordinate from the bottom
     * @param direction Which direction the object should be facing
     */
    public Position(int xCoordinate, int yCoordinate, Direction direction) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.direction = direction;
    }

    /**
     * Moves the object in the direction it is currently facing
     * @throws IllegalArgumentException if the enum is unrecognized
     */
    public void moveInDirection(){
        switch(direction.getDirEnum()){
            case NORTH:
                moveNorth();
                break;
            case EAST:
                moveEast();
                break;
            case SOUTH:
                moveSouth();
                break;
            case WEST:
                moveWest();
                break;
            default: throw new IllegalArgumentException("Unrecognized direction enum");
        }
    }

    /**
     * Moves the object down/south
     */
    private void moveSouth() {
        int currentY = getYCoordinate();
        setYCoordinate(currentY - 1);
    }

    /**
     * Moves the object left/west
     */
    private void moveWest() {
        int currentX = getXCoordinate();
        setXCoordinate(currentX - 1);
    }
    /**
     * Moves the object right/east
     */
    private void moveEast() {
        int currentX = getXCoordinate();
        setXCoordinate(currentX + 1);
    }
    /**
     * Moves the object up/north
     */
    private void moveNorth(){
        int currentY = getYCoordinate();
        setYCoordinate(currentY + 1);
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return xCoordinate == position.xCoordinate &&
                yCoordinate == position.yCoordinate &&
                getDirection().equals(position.getDirection());
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate, getDirection());
    }

    public Position copyOf(){
        return new Position(xCoordinate,yCoordinate,direction.copyOf());
    }

    @Override
    public String toString() {
        return "Position{" +
                "xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", direction=" + direction +
                '}';
    }
}
