package inf112.app.objects;

public class RepairStation implements IBoardElement {
    private int repairSpeed;

    public RepairStation(int repairSpeed){
        this.repairSpeed = repairSpeed;
    }

    @Override
    public void doAction(Robot robot) {
        robot.removeDamageTokens(repairSpeed);
        Position position = robot.getPos();
        robot.setCheckPoint(position);
    }
}
