package inf112.app.map;

import inf112.app.objects.IBoardElement;
import inf112.app.objects.Position;
import inf112.app.objects.Robot;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that represents a cell on the map grid
 * holding relevant information such as coordinates and contents
 */
public class MapCell {
    private Position position;
    private CellInventory inventory;

    public MapCell(Position position, CellInventory inventory) {
        this.position = position;
        this.inventory = inventory;
    }

    public void doAction(Robot robot){
        ArrayList<IBoardElement> contents = inventory.getElements();
        for(IBoardElement elem : contents){
            if(elem instanceof Robot){
                continue;
            }
            elem.doAction(robot);
        }
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public CellInventory getInventory() {
        return inventory;
    }

    public void setInventory(CellInventory inventory) {
        this.inventory = inventory;
    }

    public void appendToInventory(IBoardElement element){
        this.inventory.addElement(element);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapCell)) return false;
        MapCell mapCell = (MapCell) o;
        return getPosition().equals(mapCell.getPosition()) &&
                getInventory().equals(mapCell.getInventory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getInventory());
    }
}
