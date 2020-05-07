package inf112.app.objects;

import java.util.Objects;

/**
 * Class for representing flag elements on the map
 */
public class Flag implements IBoardElement {
    private final int num;

    /**
     * Basic constructor, creates an instance of a Flag object
     * @param num Which number the flag has
     */
    public Flag(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flag)) return false;
        Flag flag = (Flag) o;
        return getNum() == flag.getNum();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNum());
    }

    /**
     * Method triggered when a robot is lands on a flag after a phase is complete
     * If the flag is next in line to be visited by the robot, then this is added
     * as the robots last visited flag. Regardless of this, the position of the flag
     * is set as the new checkpoint for the robot.
     * @param robot Robot on the flags position
     */
    @Override
    public void doAction(Robot robot) {
        Flag lastVisitedFlag = robot.getVisitedFlag();
        if(lastVisitedFlag == null){
            if(num == 1){
                robot.setVisitedFlag(this);
            }
        } else {
            int lastVisitedFlagNum = robot.getVisitedFlag().getNum();
            if(lastVisitedFlagNum == num-1){
                robot.setVisitedFlag(this);
            }
        }
        robot.setCheckPoint(robot.getPos().copyOf());
    }
}
