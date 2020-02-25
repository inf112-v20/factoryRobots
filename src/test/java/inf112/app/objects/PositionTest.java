package inf112.app.objects;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import inf112.app.objects.Direction.*;

public class PositionTest {
    private Position pos;

    @Before
    public void setUp() throws Exception {
        pos = new Position(3,3);
    }

    @Test
    public void moveInDirectionNorthTest() {
        Position target = new Position(3,4,new Direction(DirEnum.NORTH));
        pos.moveInDirection();
        assertEquals(target,pos);
    }

    @Test
    public void moveInDirectionEastTest() {
        pos.getDirection().turn(Rotation.RIGHT);
        Position target = new Position(4,3,new Direction(DirEnum.EAST));
        pos.moveInDirection();
        assertEquals(target,pos);
    }

    @Test
    public void moveInDirectionSouthTest() {
        pos.getDirection().turn(Rotation.RIGHT);
        pos.getDirection().turn(Rotation.RIGHT);
        Position target = new Position(3,2,new Direction(DirEnum.SOUTH));
        pos.moveInDirection();
        assertEquals(target,pos);
    }

    @Test
    public void moveInDirectionWestTest() {
        pos.getDirection().turn(Rotation.LEFT);
        Position target = new Position(2,3,new Direction(DirEnum.WEST));
        pos.moveInDirection();
        assertEquals(target,pos);
    }
}
