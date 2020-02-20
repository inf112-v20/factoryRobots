package inf112.app.objects;

import java.util.Objects;

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
}
