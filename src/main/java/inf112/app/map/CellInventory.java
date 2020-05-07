package inf112.app.map;

import inf112.app.objects.IBoardElement;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class which cells use to keep track of it's contents
 */
public class CellInventory {
    private ArrayList<IBoardElement> elements;

    /**
     * Class constructor that initialises and empty arraylist
     */
    public CellInventory() {
        this.elements = new ArrayList<>();
    }

    /**
     * Retrieve and element from the inventory by index
     * @param index The index to find the element from
     * @return The corresponding element
     */
    public IBoardElement getElement(int index) {
        return this.elements.get(index);
    }

    /**
     * Retrieve and element from the inventory by an IBoardElement
     * @param element The IBoardElement to find in the inventory
     * @return The corresponding element
     */
    public IBoardElement getElement(IBoardElement element) {
        return this.elements.get(this.elements.indexOf(element));
    }

    /**
     * Retrieve all elements from the inventory
     * @return A list of all elements
     */
    public ArrayList<IBoardElement> getElements() {
        return elements;
    }

    /**
     * Sets an element in the inventory by index
     * @param index Index to set to element
     * @param element Element to set in the inventory
     */
    public void setElement(int index, IBoardElement element) {
        this.elements.set(index, element);
    }

    /**
     * Sets the elements to the provided inventory
     * @param elements An arraylist with IBoardElements
     */
    public void setElements(ArrayList<IBoardElement> elements){
        this.elements = elements;
    }

    /**
     * Append an element to the inventory
     * @param element The IBoardElement to append
     */
    public void addElement(IBoardElement element) {
        this.elements.add(element);
    }

    /**
     * Equals function to check if the elements in the inventory are the same
     * @param o Object to compare
     * @return True if equals, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CellInventory)) return false;
        CellInventory inventory = (CellInventory) o;
        return Objects.equals(getElements(), inventory.getElements());
    }

    /**
     * Retrieve the hash code of the object
     * @return Hash code of the whole inventory
     */
    @Override
    public int hashCode() {
        return Objects.hash(getElements());
    }
}
