package inf112.app.objects;

import java.util.Objects;
import inf112.app.objects.Direction.DirEnum;

public class Position {
    private int xCoordinate;
    private int yCoordinate;
    private Direction direction;

    public Position(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.direction = new Direction(DirEnum.NORTH);
    }

    public Position(int xCoordinate, int yCoordinate, Direction direction) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.direction = direction;
    }

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

    private void moveSouth() {
        int currentY = getYCoordinate();
        setYCoordinate(currentY - 1);
    }

    private void moveWest() {
        int currentX = getXCoordinate();
        setXCoordinate(currentX - 1);
    }

    private void moveEast() {
        int currentX = getXCoordinate();
        setXCoordinate(currentX + 1);
    }

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
        return new Position(xCoordinate,yCoordinate,direction);
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
