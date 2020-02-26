package inf112.app.map;

import inf112.app.objects.Direction;
import inf112.app.objects.Flag;
import inf112.app.objects.Position;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapCellTest {
    MapCell cell;
    Direction dir;
    Position pos;
    CellInventory inventory;

    @Before
    public void setUp() throws Exception {
        dir = new Direction(Direction.DirEnum.NORTH);
        pos = new Position(1, 1, dir);
        inventory = new CellInventory();
        cell = new MapCell(this.pos, this.inventory);
    }

    /**
     * Tests if function return the created position object in the constructor
     */
    @Test
    public void getPosition() {
        assertEquals("Failure - Position is not the same",this.cell.getPosition(), pos);
    }

    /**
     * Tests if function sets new position for cell
     */
    @Test
    public void setPosition() {
        Direction dirNew = new Direction(Direction.DirEnum.SOUTH);
        Position posNew = new Position(1,2, dirNew);
        this.cell.setPosition(posNew);
        assertNotEquals("Failure - Position is not the same",this.pos, posNew);
    }

    /**
     * Tests if function returns the created inventory object in the constructor
     */
    @Test
    public void getInventory() {
        assertEquals("Failure - Inventory is not the same",this.cell.getInventory(), this.inventory);
    }

    /**
     * Tests if function sets new inventory for cell
     */
    @Test
    public void setInventory() {
        CellInventory inventoryNew = new CellInventory();
        inventoryNew.addElement(new Flag(1));
        this.cell.setInventory(inventoryNew);
        assertNotEquals("Failure - Inventory is not the same",this.inventory, inventoryNew);
    }

    /**
     * Tests if function appends new element to inventory
     */
    @Test
    public void appendToInventory() {
        Flag flag = new Flag(1);
        this.cell.appendToInventory(flag);
        assertEquals("Failure - Inventory is not the same", this.cell.getInventory().getElement(flag), flag);
    }
}