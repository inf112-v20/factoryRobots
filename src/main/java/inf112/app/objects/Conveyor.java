package inf112.app.objects;

public class Conveyor implements IBoardElement {
    private Direction[] entries;
    private Direction exit;
    private int speed;

    public Conveyor(int entry1, int entry2, int exit, int speed){
        entries = new Direction[2];
        entries[0] = new Direction(entry1);
        entries[1] = new Direction(entry2);
        this.exit = new Direction(exit);
        this.speed = speed;

    }
}
