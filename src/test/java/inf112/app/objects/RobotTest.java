package inf112.app.objects;

import com.badlogic.gdx.utils.SerializationException;
import inf112.app.GdxTestRunner;
import inf112.app.map.Direction;
import inf112.app.map.Map;
import inf112.app.map.Position;
import inf112.app.map.Direction.Rotation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class RobotTest {

    @Before
    public void setUp() throws SerializationException {
        Map.setInstance("testMap");
    }


    @Test
    public void movesCorrectAmountOfStepsTest() {
        Position pos = new Position(2,2);
        Robot robot = new Robot(pos,"player");

        Position oldPos = pos.copyOf();
        oldPos.moveInDirection();
        oldPos.moveInDirection();
        robot.move(2);
        assertEquals("Failure, positions should be the same" , oldPos, robot.getPos());
    }

    @Test
    public void robotTurnsAsExpected() {
        Position pos = new Position(2,2);
        Robot robot = new Robot(pos,"player");

        Position oldPos = pos.copyOf();
        oldPos.getDirection().turn(Rotation.RIGHT);
        robot.turn(Rotation.RIGHT);
        assertEquals("Failure, positions should be the same", oldPos, robot.getPos());
    }

    @Test
    public void backToCheckPoint(){
        Map.clearInstance();
        Map.setInstance("testMap"); // Reset map because multiple robots caused issues
        Position pos = new Position(2,2);
        Robot robot = new Robot(pos,"player");

        robot.move(5);
        robot.backToCheckPoint();
        assertEquals(new Position(2,2, new Direction(Direction.DirEnum.NORTH)), robot.getPos());
    }

    @Test
    public void getVisitedFlag(){
        Position pos = new Position(2,2);
        Robot robot = new Robot(pos,"player");

        Flag firstFlag = new Flag(1);
        robot.setVisitedFlag(firstFlag);
        assertEquals("Failure: Should be the first flag", firstFlag, robot.getVisitedFlag());
    }

    @Test
    public void moveAndPush(){
        Position pos = new Position(2,2);
        Robot robot = new Robot(pos,"player");

        Robot movedRobot1 = new Robot(new Position(2,3),"player");
        Robot movedRobot2 = new Robot(new Position(2,4),"player");
        Robot movedRobot3 = new Robot(new Position(2,5),"player");
        Robot movedRobot4 = new Robot(new Position(2,6),"player");

        // Test straight path with four robots
        robot.moveAndPush(movedRobot1, new Direction(Direction.DirEnum.NORTH));
        assertEquals("Failure: Robot should have been pushed by previous robot",
                new Position(2,4), movedRobot1.getPos());
        assertEquals("Failure: Robot should have been pushed by previous robot",
                new Position(2,5), movedRobot2.getPos());
        assertEquals("Failure: Robot should have been pushed by previous robot",
                new Position(2,6), movedRobot3.getPos());
        assertEquals("Failure: Robot should have been pushed by previous robot",
                new Position(2,7) ,movedRobot4.getPos());

        // Test wall

        Robot wallRobot = new Robot(new Position(4,6),"player");
        Robot wallRobot2 = new Robot(new Position(4,7),"player");

        wallRobot.moveAndPush(wallRobot2,new Direction(Direction.DirEnum.NORTH));
        assertEquals("Failure: Robot cannot be pushed past a wall",
                new Position(4,7), wallRobot2.getPos());
    }

    @Test
    public void turn(){
        Position pos = new Position(2,2);
        Robot robot = new Robot(pos,"player");

        robot.turn(Rotation.LEFT);
        assertEquals("Failure: Robot didn't turn in the correct direction",
                new Direction(Direction.DirEnum.WEST), robot.getPos().getDirection());

        robot.turn(Rotation.LEFT);
        assertEquals("Failure: Robot didn't turn in the correct direction",
                new Direction(Direction.DirEnum.SOUTH) ,robot.getPos().getDirection());

        robot.turn(Rotation.LEFT);
        assertEquals("Failure: Robot didn't turn in the correct direction",
                new Direction(Direction.DirEnum.EAST), robot.getPos().getDirection());

        robot.turn(Rotation.LEFT);
        assertEquals("Failure: Robot didn't turn in the correct direction",
                new Direction(Direction.DirEnum.NORTH) ,robot.getPos().getDirection());

        robot.turn(Rotation.RIGHT);
        assertEquals("Failure: Robot didn't turn in the correct direction",
                new Direction(Direction.DirEnum.EAST), robot.getPos().getDirection());

        robot.turn(Rotation.RIGHT);
        assertEquals("Failure: Robot didn't turn in the correct direction",
                    new Direction(Direction.DirEnum.SOUTH), robot.getPos().getDirection());

        robot.turn(Rotation.RIGHT);
        assertEquals("Failure: Robot didn't turn in the correct direction",
                new Direction(Direction.DirEnum.WEST), robot.getPos().getDirection());

        robot.turn(Rotation.RIGHT);
        assertEquals("Failure: Robot didn't turn in the correct direction",
                new Direction(Direction.DirEnum.NORTH), robot.getPos().getDirection());
    }

    @Test
    public void move(){
        Map.clearInstance();
        Map.setInstance("testMap"); // Reset map because multiple robots caused issues
        Position pos = new Position(5,5);
        Robot robot = new Robot(pos,"1Comb");
        pos = robot.getPos();

        // Test standard moves
        robot.move(new Direction(Direction.DirEnum.NORTH));
        assertEquals("Failure: Robot didn't move in the correct direction",
                new Position(5,6), pos);

        robot.move(new Direction(Direction.DirEnum.WEST));
        assertEquals("Failure: Robot didn't move in the correct direction",
                new Position(4,6), pos);

        robot.move(new Direction(Direction.DirEnum.EAST));
        assertEquals("Failure: Robot didn't move in the correct direction",
                new Position(5,6), pos);

        robot.move(new Direction(Direction.DirEnum.SOUTH));
        assertEquals("Failure: Robot didn't move in the correct direction",
                new Position(5,5), pos);

        // Test illegal move

        robot.move(20);
        assertEquals("Failure: Robot shouldn't move outside the board",
                new Position(5,14), pos);
    }
}