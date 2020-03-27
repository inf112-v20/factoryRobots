package inf112.app.map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import inf112.app.map.Direction.*;

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
        assertEquals("Failure, directions are not the same",expectedDir,dir);
    }

    @Test
    public void enumNorthTranslatesAsExpected() {
        int angle = Direction.translateEnumToAngle(DirEnum.NORTH);
        int expectedAngle = 0;
        assertEquals("Failure, angles are not the same", expectedAngle,angle);
    }

    @Test
    public void enumEastTranslatesAsExpected() {
        int angle = Direction.translateEnumToAngle(DirEnum.EAST);
        int expectedAngle = 90;
        assertEquals("Failure, angles are not the same",expectedAngle,angle);
    }

    @Test
    public void enumSouthTranslatesAsExpected() {
        int angle = Direction.translateEnumToAngle(DirEnum.SOUTH);
        int expectedAngle = 180;
        assertEquals("Failure, angles are not the same",expectedAngle,angle);
    }

    @Test
    public void enumWestTranslatesAsExpected() {
        int angle = Direction.translateEnumToAngle(DirEnum.WEST);
        int expectedAngle = 270;
        assertEquals("Failure, angles are not the same",expectedAngle,angle);
    }

    @Test
    public void angleTranslatesAsExpected(){
        DirEnum[] list = new DirEnum[]{DirEnum.NORTH,DirEnum.EAST,DirEnum.SOUTH,DirEnum.WEST};
        int[] angles = new int[]{0,90,180,270};
        for (int i = 0; i < 4; i++){
            assertEquals("Failure, enums are not the same",list[i], Direction.translateAngleToEnum(angles[i]));
        }
    }


}
