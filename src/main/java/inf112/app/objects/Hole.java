package inf112.app.objects;

public class Hole implements IBoardElement{
    @Override
    public void doAction(Robot robot) {
        robot.backToCheckPoint();
    }
}
