package inf112.app.objects;

import inf112.app.map.Direction;
import inf112.app.map.Map;
import inf112.app.map.Position;
import inf112.app.map.Direction.Rotation;

import java.util.ArrayList;

/**
 * This class is a representation of the
 * conveyor belts on the board
 */
public class Conveyor implements IBoardElement {
    private final Direction[] entries;
    private final Direction exit;
    private final int speed;

    /**
     * Constructor for the conveyor belts
     * @param entry1 first entry point
     * @param entry2 second entry point
     * @param exit Direction of the exit
     * @param speed Integer designating the speed of the belt
     */
    public Conveyor(int entry1, int entry2, int exit, int speed){
        entries = new Direction[2];
        entries[0] = new Direction(entry1);
        entries[1] = new Direction(entry2);
        this.exit = new Direction(exit);
        this.speed = speed;


    }

    /**
     * Method to find out if the robot will rotate on the conveyor
     * @param dir The direction the robot is coming from
     * @return true if it will rotate, false if not
     */
    public boolean willRotate(Direction dir){
        if (dir.equals(exit)){
            return false;
        }
        Direction direc = exit.copyOf();
        direc.turn(Rotation.LEFT);
        direc.turn(Rotation.LEFT);


        return !dir.equals(direc);
    }

    /**
     * Method to find out witch direction the belt will rotate a robot
     * @param dir The direction the robot is facing
     * @return The direction of the rotation
     */
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
     * Method applying the conveyor belts action to a given robot,
     * moves and rotates the robot based on the parameters of the belt
     * @param robot Robot to be manipulated
     */
    @Override
    public void doAction(Robot robot) {

        robot.move(getExit());
        boolean hasRotated = false;

        Conveyor next = extractConveyorFromCell(robot.getPos());
        if (next == null) {
            return;
        }
        if (next.willRotate(exit)) {
            robot.turn(next.rotationDirection(exit));
            hasRotated = true;
        }
        if (speed == 2) {
            robot.move(next.getExit());
            Conveyor afterNext = extractConveyorFromCell(robot.getPos());
            if(afterNext == null){
                return;
            }
            if(afterNext.willRotate(exit) && !hasRotated){
                robot.turn(afterNext.rotationDirection(exit));
            }
        }
    }

    /**
     * Method to find out if there is a conveyor at a given {@link Position} on the map
     * @param pos Position to check the contents of
     * @return The potential conveyor, null if there isn't one
     */
    public static Conveyor extractConveyorFromCell(Position pos){
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

    /**
     * @return The direction the exit of the conveyor is facing 
     */
    public Direction getExit() {
        return new Direction(exit.getDirEnum());
    }
}
