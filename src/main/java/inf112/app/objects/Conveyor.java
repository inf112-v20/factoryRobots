package inf112.app.objects;

import inf112.app.map.Map;
import inf112.app.objects.Direction.Rotation;

import java.util.ArrayList;

/**
 * This class is a representation of the
 * Conveyor belts on the board
 */
public class Conveyor implements IBoardElement {
    private Direction[] entries;
    private Direction exit;
    private int speed;
    private boolean rotate;
    private Rotation rotation;

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
        this.rotate = false;

    }
    public Conveyor(int entry1, int entry2, int exit, int speed, boolean rotate, Rotation r){
        entries = new Direction[2];
        entries[0] = new Direction(entry1);
        entries[1] = new Direction(entry2);
        this.exit = new Direction(exit);
        this.speed = speed;
        this.rotate = rotate;
        this.rotation = r;
    }

    /**
     * implementation of action to be done when robot is on a conveyor belt
     * @param player
     */
    @Override
    public void doAction(Player player) {
        player.getCharacter().move(exit);

        Conveyor next = extractConveyorFromCell(player.getCharacter().getPos());
        if (next == null) {
            return;
        }
        if (next.rotate) {
            player.getCharacter().turn(next.rotation);
        }
        if (speed == 2) {
            player.getCharacter().move(next.exit);
            Conveyor afterNext = extractConveyorFromCell(player.getCharacter().getPos());
            if(next == null){
                return;
            }
            if(afterNext.rotate){
                player.getCharacter().turn(afterNext.rotation);
            }
        }
    }

    /**
     *
     * @param pos
     * @return
     */
    private Conveyor extractConveyorFromCell(Position pos){
        Map map = Map.getInstance();
        ArrayList<IBoardElement> objectList = map.getCellList().getCell(pos).getInventory().getElements();
        Conveyor next = null;
        for (IBoardElement e : objectList) {
            if (e instanceof Conveyor) {
                next = (Conveyor) e;
            }
        }
        return next;
    }

}
