package inf112.app.objects;

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
}
