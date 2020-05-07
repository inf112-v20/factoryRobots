package inf112.app.objects;

import inf112.app.map.Position;

/**
 * Class for representing a Repairstation-element on the board
 */
public class RepairStation implements IBoardElement {
    private int repairSpeed;

    /**
     * Creates a RepairStation-instance
     * @param repairSpeed The speed at which the station repairs robots
     */
    public RepairStation(int repairSpeed){
        this.repairSpeed = repairSpeed;
    }

    /**
     * Method that is called when a robot is position on the repairstation at the end of the phase,
     * removes damagetokens according to the speed of the repairstation
     * @param robot The robot that is positioned on the element
     */
    @Override
    public void doAction(Robot robot) {
        robot.removeDamageTokens(repairSpeed);
        Position position = robot.getPos();
        robot.setCheckPoint(position.copyOf());
    }
}
