package inf112.app.objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectionTest {
    private Direction dir;

    @Before
    public void setUp() throws Exception {
        dir = new Direction(DirEnum.NORTH);
    }

    @Test
    public void turnRotatesAsExpected() {
        Direction expectedDir = new Direction(DirEnum.EAST);
        dir.turn(Rotation.RIGHT);
        assertEquals(expectedDir,dir);
    }
}
