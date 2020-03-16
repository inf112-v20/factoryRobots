package inf112.app.game;

import inf112.app.GdxTestRunner;
import inf112.app.map.Map;
import inf112.app.objects.Direction.Rotation;
import inf112.app.objects.Player;
import inf112.app.objects.Position;
import inf112.app.objects.Robot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class MoveCardTest {
    MoveCard moveOne;
    MoveCard moveTwo;
    MoveCard moveThree;
    MoveCard backUp;
    Map map;
    Robot robot;
    @Before
    public void setUp() throws Exception {
        moveOne = new MoveCard(150,1,null);
        moveTwo = new MoveCard(150, 2,null);
        moveThree = new MoveCard(150,3,null);
        backUp = new MoveCard(150,true,null);
        Map.setInstance("testMap");
        map = Map.getInstance();
        Player player = new Player(2,2);
        robot = player.getCharacter();
    }

    @Test
    public void doActionMoveOneTest() {
        Position oldPos = robot.getPos().copyOf();
        oldPos.moveInDirection();
        moveOne.doAction(robot);
        assertEquals("Failure, positions should be the same",oldPos,robot.getPos());
    }

    @Test
    public void doActionMoveTwoTest() {
        Position oldPos = robot.getPos().copyOf();
        oldPos.moveInDirection();
        oldPos.moveInDirection();
        moveTwo.doAction(robot);
        assertEquals("Failure, positions should be the same",oldPos,robot.getPos());
    }

    @Test
    public void doActionMoveThreeTest() {
        Position oldPos = robot.getPos().copyOf();
        for(int i = 0; i<3; i++){
            oldPos.moveInDirection();
        }
        moveThree.doAction(robot);
        assertEquals("Failure, positions should be the same",oldPos,robot.getPos());
    }
    @Test
    public void doActionBackUpTest() {
        Position oldPos = robot.getPos().copyOf();
        oldPos.getDirection().turn(Rotation.LEFT);
        oldPos.getDirection().turn(Rotation.LEFT);
        oldPos.moveInDirection();
        oldPos.getDirection().turn(Rotation.LEFT);
        oldPos.getDirection().turn(Rotation.LEFT);
        backUp.doAction(robot);
        assertEquals("Failure, positions should be the same", oldPos, robot.getPos());
    }

}