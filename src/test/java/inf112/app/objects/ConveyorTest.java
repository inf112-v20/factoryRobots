package inf112.app.objects;

import inf112.app.GdxTestRunner;
import inf112.app.game.Player;
import inf112.app.map.Direction;
import inf112.app.map.Map;

import java.util.ArrayList;

import inf112.app.map.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class ConveyorTest {
    Player player;
    Robot robot;

    @Before
    public void setUp() throws Exception {
        Map.setInstance("testMap");

    }

    /**
     * testing for double speed conveyor
     */
    @Test
    public void doActionDoubleSpeed() {
        player = new Player(8, 4);
        robot = player.getCharacter();
        Conveyor conveyor = null;
        ArrayList<IBoardElement> elements = Map.getInstance().getCellList().getCell(player.getCharacter().getPos()).getInventory().getElements();
        for(IBoardElement e : elements){
            if(e instanceof Conveyor){
                conveyor = (Conveyor) e;
            }
        }
        Position oldPos = player.getCharacter().getPos().copyOf();
        oldPos.setDirection(conveyor.getExit());
        oldPos.moveInDirection();
        oldPos.moveInDirection();
        oldPos.getDirection().turn(Direction.Rotation.LEFT);
        conveyor.doAction(robot);
        assertEquals("Positions should be the same ",oldPos,player.getCharacter().getPos());

    }

    /**
     * testing for single speed conveyor
     */
    @Test
    public void doActionSingleSpeed() {
        player = new Player(11, 4);
        robot = player.getCharacter();
        Conveyor conveyor = null;
        ArrayList<IBoardElement> elements = Map.getInstance().getCellList().getCell(player.getCharacter().getPos()).getInventory().getElements();
        for(IBoardElement e : elements){
            if(e instanceof Conveyor){
                conveyor = (Conveyor) e;
            }
        }

        Position oldPos = player.getCharacter().getPos().copyOf();
        oldPos.setDirection(conveyor.getExit());
        oldPos.moveInDirection();
        conveyor.doAction(robot);
        assertEquals("Positions should be the same ",oldPos,player.getCharacter().getPos());

    }

}