package inf112.app.map;

import inf112.app.objects.IBoardElement;
import inf112.app.objects.IDTranslator.ElemEnum;

import java.util.ArrayList;

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



}
