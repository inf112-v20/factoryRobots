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


    }

    public boolean willRotate(Direction dir){
        if (dir.equals(exit)){
            return false;
        }
        Direction direc = exit.copyOf();
        direc.turn(Rotation.LEFT);
        direc.turn(Rotation.LEFT);

        if (dir.equals(direc)){
            return false;
        } else{
            return true;
        }

    }

    public Rotation rotationDirection(Direction dir){
        int leftRotationAngle = dir.getAngleDeg() - 90;
        if (leftRotationAngle < 0){
            leftRotationAngle = 270;
        }
        if ((dir.getAngleDeg() + 90)%360 == exit.getAngleDeg()){
            return Rotation.RIGHT;
        }else if(leftRotationAngle == exit.getAngleDeg()){
            return Rotation.LEFT;
        }else{
            throw new RuntimeException("Could not determine rotation of conveyor");
        }
    }

    /**
     * implementation of action to be done when robot is on a conveyor belt
     * @param robot
     */
    @Override
    public void doAction(Robot robot) {

        robot.move(getExit());

        Conveyor next = extractConveyorFromCell(robot.getPos());
        if (next == null) {
            return;
        }
        if (next.willRotate(exit)) {
            robot.turn(next.rotationDirection(exit));
        }
        if (speed == 2) {
            robot.move(next.getExit());
            Conveyor afterNext = extractConveyorFromCell(robot.getPos());
            if(afterNext == null){
                return;
            }
            if(afterNext.willRotate(exit)){
                robot.turn(afterNext.rotationDirection(exit));
            }
        }
    }

    /**
     * Methode to find out if robot is positioned on a conveyor belt
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

    public Direction getExit() {
        return new Direction(exit.getDirEnum());
    }
}
