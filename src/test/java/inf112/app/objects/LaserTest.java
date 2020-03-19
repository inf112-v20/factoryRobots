package inf112.app.objects;

import inf112.app.GdxTestRunner;
import inf112.app.map.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(GdxTestRunner.class)
public class LaserTest {
    @Before
    public void setUp() throws Exception {
        Map.setInstance("testMap");
        Map.getInstance().clearBots();
    }

    @Test
    public void laserGivesDamageTest(){
        Robot robot = new Robot(new Position(2,8),"player");
        int expectedTokens = robot.getDamageTokens();
        expectedTokens += 1;
        Map.getInstance().fireLasers();
        assertEquals("Fail, damage tokens are not equal to expected",expectedTokens, robot.getDamageTokens());
        Map.getInstance().deleteRobot(robot);
    }

    @Test
    public void laserGivesDoubleDamageTest(){
        Robot robot = new Robot(new Position(8,8),"player");
        int expectedTokens = robot.getDamageTokens();
        expectedTokens += 2;
        Map.getInstance().fireLasers();
        assertEquals("Fail, damage tokens are not equal to expected",expectedTokens,robot.getDamageTokens());
        Map.getInstance().deleteRobot(robot);
    }

    @Test
    public void laserStopOnRobotHit(){
        Robot robot = new Robot(new Position(2,8),"player");
        Robot hidden = new Robot(new Position(1,8),"player");
        int expectedTokens = hidden.getDamageTokens();
        Map.getInstance().fireLasers();

        assertEquals("Fail, damage tokens are not equal to expected",expectedTokens,hidden.getDamageTokens());
        assertNotEquals("Fail, damage tokens are not different", robot.getDamageTokens(),hidden.getDamageTokens());
        Map.getInstance().deleteRobot(robot);
        Map.getInstance().deleteRobot(hidden);
    }

    @Test
    public void laserBlockedByWall(){
        Robot robot = new Robot(new Position(4,4),"player");
        Robot hidden = new Robot(new Position(4,8),"player");
        int expectedTokens = hidden.getDamageTokens();
        Map.getInstance().fireLasers();
        assertEquals("Fail, damage tokens are not equal to expected",expectedTokens,hidden.getDamageTokens());
        Map.getInstance().deleteRobot(robot);
        Map.getInstance().deleteRobot(hidden);
    }

    @Test
    public void robotLaserTest(){
        Robot robot = new Robot(new Position(4,4),"player");
        Robot target = new Robot(new Position(4,5),"player");
        int expectedTokens = target.getDamageTokens();
        expectedTokens += 1;
        Map.getInstance().fireLasers();

        assertEquals("Fail, damage tokens are not equal to expected",expectedTokens,target.getDamageTokens());

        Map.getInstance().deleteRobot(robot);
        Map.getInstance().deleteRobot(target);
    }
}