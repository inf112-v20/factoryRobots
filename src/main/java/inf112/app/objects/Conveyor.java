package inf112.app.objects;

/**
 * This class is a representation of the
 * Conveyor belts on the board
 */
public class Conveyor implements IBoardElement {
    private Direction[] entries;
    private Direction exit;
    private int speed;
    private boolean rotate;
    private Direction.Rotation rotation;

    /**
     * constructor for the conveyor belts
     * @param entry1 first entry point
     * @param entry2 second entry point
     * @param exit
     * @param speed
     */
    public Conveyor(int entry1, int entry2, int exit, int speed){
        entries = new Direction[2];
        entries[0] = new Direction(entry1);
        entries[1] = new Direction(entry2);
        this.exit = new Direction(exit);
        this.speed = speed;

    }

    public Conveyor(int entry1, int entry2, int exit, int speed, boolean rotate, Direction.Rotation r){
        entries = new Direction[2];
        entries[0] = new Direction(entry1);
        entries[1] = new Direction(entry2);
        this.exit = new Direction(exit);
        this.speed = speed;
        this.rotate = rotate;
        this.rotation = r;

    }

    public void conveyorAction(Player player){
        if (rotate){
            player.turn(rotation);
            player.move(speed);
        }else {
            player.move(speed);
        }
    }

}
