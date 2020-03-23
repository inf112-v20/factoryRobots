package inf112.app.objects;

import inf112.app.GdxTestRunner;
import inf112.app.map.Direction;
import inf112.app.map.Map;
import inf112.app.map.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class RepairStationTest {
    private Robot doubleDamageBot;
    private Robot singleDamageBot;

    @Before
    public void setUp() throws Exception {
        Map.setInstance("testMap");
        Map.getInstance().clearBots();
        doubleDamageBot = new Robot(new Position(7,8),"player");
        singleDamageBot = new Robot(new Position(2,8),"player");

    }

    @Test
    public void repairStationHealsCorrectAmountOfDamageTest() {
        Map.getInstance().fireLasers();
        assertEquals("Fail, lasers didn't give damage to bot",1,singleDamageBot.getDamageTokens());
        assertEquals("Fail, lasers didn't give damage to bot",2,doubleDamageBot.getDamageTokens());

        singleDamageBot.move(2);
        doubleDamageBot.move(2);
        singleDamageBot.turn(Direction.Rotation.RIGHT);
        doubleDamageBot.turn(Direction.Rotation.LEFT);
        singleDamageBot.move(2);
        doubleDamageBot.move(2);

        Map.getInstance().getCellList().getCell(singleDamageBot.getPos()).doAction(singleDamageBot);
        Map.getInstance().getCellList().getCell(doubleDamageBot.getPos()).doAction(doubleDamageBot);

        assertEquals("Fail, repair station didn't remove damage from bot",0,singleDamageBot.getDamageTokens());
        assertEquals("Fail, repair station didn't remove damage from bot",0,doubleDamageBot.getDamageTokens());

        Map.getInstance().deleteRobot(singleDamageBot);
        Map.getInstance().deleteRobot(doubleDamageBot);
    }

    @Test
    public void repairStationDoesntGoPastZeroTest(){
        Robot robot = new Robot(new Position(4,10),"player");
        Map.getInstance().getCellList().getCell(robot.getPos()).doAction(robot);
        assertEquals("Fail, robot should have 0 damage tokens",0,robot.getDamageTokens());

        Map.getInstance().deleteRobot(robot);
    }
}