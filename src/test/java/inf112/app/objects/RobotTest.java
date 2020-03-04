package inf112.app.objects;

import inf112.app.GdxTestRunner;
import inf112.app.map.Map;
import inf112.app.objects.Direction.Rotation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class RobotTest {
    private Robot robot;
    private Position pos;

    @Before
    public void setUp() throws Exception {
        Map.setInstance("testMap");
        pos = new Position(2,2);
        robot = new Robot(pos);
    }

    @Test
    public void movesCorrectAmountOfStepsTest() {
        Position oldPos = pos.copyOf();
        oldPos.moveInDirection();
        oldPos.moveInDirection();
        robot.move(2);
        assertEquals("Failure, positions should be the same" , oldPos, robot.getPos());
    }

    @Test
    public void robotTurnsAsExpected() {
        Position oldPos = pos.copyOf();
        oldPos.getDirection().turn(Rotation.RIGHT);
        robot.turn(Rotation.RIGHT);
        assertEquals("Failure, positions should be the same", oldPos, robot.getPos());
    }
}