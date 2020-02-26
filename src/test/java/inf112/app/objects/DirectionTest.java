package inf112.app.objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import inf112.app.objects.Direction.*;

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

    @Test
    public void enumNorthTranslatesAsExpected() {
        int angle = Direction.translateEnumToAngle(DirEnum.NORTH);
        int expectedAngle = 0;
        assertEquals(expectedAngle,angle);
    }

    @Test
    public void enumEastTranslatesAsExpected() {
        int angle = Direction.translateEnumToAngle(DirEnum.EAST);
        int expectedAngle = 90;
        assertEquals(expectedAngle,angle);
    }

    @Test
    public void enumSouthTranslatesAsExpected() {
        int angle = Direction.translateEnumToAngle(DirEnum.SOUTH);
        int expectedAngle = 180;
        assertEquals(expectedAngle,angle);
    }

    @Test
    public void enumWestTranslatesAsExpected() {
        int angle = Direction.translateEnumToAngle(DirEnum.WEST);
        int expectedAngle = 270;
        assertEquals(expectedAngle,angle);
    }

    @Test
    public void angleTranslatesAsExpected(){
        DirEnum[] list = new DirEnum[]{DirEnum.NORTH,DirEnum.EAST,DirEnum.SOUTH,DirEnum.WEST};
        int[] angles = new int[]{0,90,180,270};
        for (int i = 0; i < 4; i++){
            assertEquals(list[i], Direction.translateAngleToEnum(angles[i]));
        }
    }


}
