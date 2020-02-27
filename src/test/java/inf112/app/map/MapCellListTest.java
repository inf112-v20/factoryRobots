package inf112.app.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.GdxTestRunner;
import inf112.app.objects.Flag;
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
        cellList = new MapCellList(15,15, map.getLayers());
    }

    /**
     * Tests if getCell from position return the correct item
     */
    @Test
    public void getCellFromPosition() {
        Position pos = new Position(11,11); // Should include flag
        CellInventory inventory = new CellInventory();
        inventory.addElement(new Flag(1));
        CellInventory inventoryCell = cellList.getCell(pos).getInventory();
        Boolean test = inventory.equals(inventoryCell);
        assertEquals("Failure - Inventories are not equal", inventory, inventoryCell);
        assertEquals("Failure - Cell is not equal", cellList.getCell(pos), new MapCell(pos, inventory));
    }

    /**
     * Tests if getCell from x,y coordinate return the correct item
     */
    @Test
    public void GetCellFromXY() {
        CellInventory inventory = new CellInventory();
        inventory.addElement(new Flag(1));
        CellInventory inventoryCell = cellList.getCell(11,11).getInventory();
        Boolean test = inventory.equals(inventoryCell);
        assertEquals("Failure - Inventories are not equal", inventory, inventoryCell);
        assertEquals("Failure - Cell is not equal", cellList.getCell(11,11),
                new MapCell(new Position(11,11), inventory));
    }
}