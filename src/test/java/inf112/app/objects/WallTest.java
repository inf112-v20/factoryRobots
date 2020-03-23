package inf112.app.objects;

import inf112.app.GdxTestRunner;
import inf112.app.map.Direction;
import inf112.app.map.Map;
import inf112.app.map.Position;
import inf112.app.map.Direction.Rotation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(GdxTestRunner.class)
public class WallTest {

    private Position pos;
    private Robot robot;

    @Before
    public void setUp() throws Exception {
        Map.setInstance("testMap");
        pos = new Position(3,7);
        robot = new Robot(pos,"player");
    }

    @Test
    public void blockingTest() {
        Position expected = robot.getPos().copyOf();
        expected.setDirection(new Direction(180));

        robot.turn(Rotation.RIGHT);
        robot.move(1);
        robot.turn(Rotation.RIGHT);
        robot.move(1);
        assertEquals("Fail, robot has moved",expected,robot.getPos());
    }

    @Test
    public void blockingTest2(){
        pos.setXCoordinate(4);
        pos.setDirection(new Direction(270));

        Position expected = pos.copyOf();
        expected.setDirection(new Direction(180));

        robot.move(1);
        robot.turn(Rotation.RIGHT);
        robot.move(1);
        robot.turn(Rotation.RIGHT);
        robot.move(1);
        robot.turn(Rotation.RIGHT);

        assertEquals("Fail, robot has moved",expected,robot.getPos());
    }
}