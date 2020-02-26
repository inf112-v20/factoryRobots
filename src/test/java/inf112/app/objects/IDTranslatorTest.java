package inf112.app.objects;

import org.junit.Before;
import org.junit.Test;
import inf112.app.objects.IDTranslator.ElemEnum;

import static org.junit.Assert.*;

public class IDTranslatorTest {
    IDTranslator translator;

    @Before
    public void setUp() throws Exception {
        translator = new IDTranslator();
    }

    @Test
    public void findWallFromWallIdTest() {
        assertEquals(ElemEnum.WALL,translator.findNameFromId(29));
    }

    @Test
    public void findConveyorFromConveyorIdTest() {
        assertEquals(ElemEnum.BELTX2,translator.findNameFromId(18));
    }

    @Test
    public void findFlagFromFlagIdTest() {
        assertEquals(ElemEnum.FLAG,translator.findNameFromId(55));
    }

    @Test
    public void findHoleFromHoleIdTest() {
        assertEquals(ElemEnum.HOLE,translator.findNameFromId(6));
    }

    @Test
    public void findCogFromCogIdTest() {
        assertEquals(ElemEnum.COG,translator.findNameFromId(53));
    }

    @Test
    public void findLaserFromLaserIdTest() {
        assertEquals(ElemEnum.LASER,translator.findNameFromId(39));
    }

    @Test
    public void findBoardFromBoardIdTest() {
        assertEquals(ElemEnum.BOARD,translator.findNameFromId(5));
    }

    @Test
    public void findRepairStationFromRepairStationIDTest() {
        assertEquals(ElemEnum.REPAIRSTATION,translator.findNameFromId(7));
    }

}