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
    CellInventory inventory;
    Flag flag;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load("assets/testMap.tmx");
        inventory = new CellInventory();
        flag = new Flag(1);
        inventory.addElement(flag);
    }

    @Test
    public void getElementByElement() {
        assertEquals("Failure - could not get element in array by element"
                , this.inventory.getElement(new Flag(1)), flag);
    }

    @Test
    public void getElementByIndex() {
        assertEquals("Failure - could not get element in array by index"
                , this.inventory.getElement(0), flag);
    }

    @Test
    public void getElements() {
        assertEquals("Failure - could not get elements from array ", this.inventory.getElement(0), flag);
    }

    @Test
    public void setElement() {
        this.inventory.setElement(0, new Flag(2));
        assertEquals("Failure - could not set element",
                this.inventory.getElement(0), new Flag(2));
        exception.expect(IndexOutOfBoundsException.class);
        inventory.setElement(2,new Flag(2));
    }

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

    @Test
    public void addElement() {
        this.inventory.addElement(new Flag(2));
        assertEquals("Failure - returned element is not the same as added element",
                this.inventory.getElement(new Flag(2)), new Flag(2));
    }
}