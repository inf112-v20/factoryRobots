package inf112.app.map;

import inf112.app.objects.IBoardElement;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class which cells use to keep track of it's contents
 */
public class CellInventory {
    private ArrayList<IBoardElement> elements;
    public CellInventory() {
        this.elements = new ArrayList<>();
    }


    public IBoardElement getElement(int index) {
        return this.elements.get(index);
    }

    public IBoardElement getElement(IBoardElement element) {
        return this.elements.get(this.elements.indexOf(element));
    }
    public ArrayList<IBoardElement> getElements() {
        return elements;
    }

    public void setElement(int index, IBoardElement element) {
        this.elements.set(index, element);
    }
    public void setElements(ArrayList<IBoardElement> elements){
        this.elements = elements;
    }
    public void addElement(IBoardElement element) {
        this.elements.add(element);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CellInventory)) return false;
        CellInventory inventory = (CellInventory) o;
        return Objects.equals(getElements(), inventory.getElements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getElements());
    }
}
