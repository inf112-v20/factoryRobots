package inf112.app.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.GdxTestRunner;
import inf112.app.objects.Flag;
import inf112.app.objects.IBoardElement;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CellInventoryTest {
    private CellInventory inventory;
    private Flag flag;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        inventory = new CellInventory();
        flag = new Flag(1);
        inventory.addElement(flag);
    }

    /**
     * Tests if function inventory returns the correct element in the array (By element)
     */
    @Test
    public void getElementByElement() {
        assertEquals("Failure - could not get element in array by element"
                , this.inventory.getElement(new Flag(1)), flag);
    }

    /**
     * Tests if function inventory returns the correct element in the array (By index)
     */
    @Test
    public void getElementByIndex() {
        assertEquals("Failure - could not get element in array by index"
                , this.inventory.getElement(0), flag);
    }

    /**
     * Tests if function inventory can correctly set element. Also test index out of bounds
     */
    @Test
    public void setElement() {
        this.inventory.setElement(0, new Flag(2));
        assertEquals("Failure - could not set element",
                this.inventory.getElement(0), new Flag(2));
        exception.expect(IndexOutOfBoundsException.class);
        inventory.setElement(2,new Flag(2));
    }

    /**
     * Tests if one can set multiple elements in the inventory
     */
    @Test
    public void setElements() {
        ArrayList<IBoardElement> list = new ArrayList<>();
        list.add(new Flag(2));
        list.add(new Flag(3));
        list.add(new Flag(4));
        this.inventory.setElements(list);
        ArrayList<IBoardElement> listExpect = this.inventory.getElements();
        assertEquals("Failure - returned elements inserted into the array is not the same when returned"
                , list, listExpect);
    }

    /**
     * Test if on can add an element to the inventory
     */
    @Test
    public void addElement() {
        this.inventory.addElement(new Flag(2));
        assertEquals("Failure - returned element is not the same as added element",
                this.inventory.getElement(new Flag(2)), new Flag(2));
    }
}