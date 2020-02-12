package inf112.app.map;

import inf112.app.objects.IBoardElement;

public class CellInventory {
    private IBoardElement[] elements;

    public CellInventory(IBoardElement[] elements) {
        this.elements = elements;
    }

    public IBoardElement getElements(int position) {
        return elements[position];
    }

    public void setElement(int position, IBoardElement element) {
        this.elements[position] = element;
    }

}
