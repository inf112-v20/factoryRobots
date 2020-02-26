package inf112.app.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.GdxTestRunner;
import inf112.app.objects.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class MapCellListTest {
    MapCellList cellList;
    @Before
    public void setUp() throws Exception {
        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load("assets/testMap.tmx");
        cellList = new MapCellList(50,50,map.getLayers());
    }

    /**
     *
     */
    @Test
    public void getCellFromPosition() {
        Position pos = new Position(1,1);
        CellInventory inventory = new CellInventory();
        assertEquals(cellList.getCell(pos), new MapCell(pos, inventory));
    }

    /**
     *
     */
    @Test
    public void GetCellFromXY() {
    }
}