package inf112.app.objects;

import inf112.app.GdxTestRunner;
import inf112.app.map.Direction;
import inf112.app.map.Map;
import inf112.app.map.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(GdxTestRunner.class)
public class CogTest {

    @Before
    public void setUp() throws Exception {
        Map.setInstance("testMap");
    }

    @Test
    public void robotRotatesCounterClockWiseTest() {
        Robot robot = new Robot(new Position(10,1), "player");
        Direction old = robot.getPos().getDirection().copyOf();
        Map.getInstance().getCellList().getCell(robot.getPos()).doAction(robot);
        Direction current = robot.getPos().getDirection().copyOf();
        assertNotEquals("",old,current);
        assertEquals("",new Direction(Direction.DirEnum.WEST),current);
    }

    @Test
    public void robotRotatesClockWiseTest() {
        Robot robot = new Robot(new Position(12,1), "player");
        Direction old = robot.getPos().getDirection().copyOf();
        Map.getInstance().getCellList().getCell(robot.getPos()).doAction(robot);
        Direction current = robot.getPos().getDirection().copyOf();
        assertNotEquals("",old,current);
        assertEquals("",new Direction(Direction.DirEnum.EAST),current);
    }
}