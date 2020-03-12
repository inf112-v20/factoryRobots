package inf112.app.objects;

import inf112.app.GdxTestRunner;
import inf112.app.map.Map;

import java.lang.reflect.Array;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class ConveyorTest {
    ArrayList<Conveyor> doubleSpeed;
    ArrayList<Conveyor> singleSpeed;
    Direction exit;
    Player player;

    @Before
    public void setUp() throws Exception {
        Map.setInstance("testMap");

    }

    @Test
    public void doActionDoubleSpeed() {
        player = new Player(8, 4);
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
        conveyor.doAction(player);
        assertEquals("Positions should be the same ",oldPos,player.getCharacter().getPos());

    }

    @Test
    public void doActionSingleSpeed() {
        Position oldPos = player.getCharacter().getPos().copyOf();
        oldPos.setDirection(exit);
        oldPos.moveInDirection();
        oldPos.getDirection().turn(Direction.Rotation.LEFT);
        assertEquals("Positions should be the same ",oldPos,player.getCharacter().getPos());

    }

}