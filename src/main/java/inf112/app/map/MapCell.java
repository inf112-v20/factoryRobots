package inf112.app.map;

import inf112.app.objects.IBoardElement;
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

    /**
     * Constructor for a MapCell object
     * @param position Position of the cell
     * @param inventory Inventory of the cell
     */
    public MapCell(Position position, CellInventory inventory) {
        this.position = position;
        this.inventory = inventory;
    }

    /**
     * Method for triggering all the objects in the cell's
     * {@link IBoardElement#doAction(Robot)} method,
     * with the exception of {@link Robot}s
     * @param robot The robot the action should be performed on
     */
    public void doAction(Robot robot){
        ArrayList<IBoardElement> contents = inventory.getElements();
        for(IBoardElement elem : contents){
            if(elem instanceof Robot){
                continue;
            } else if(elem != null){
                elem.doAction(robot);
            }

        }
    }

    /**
     * Get the position of the cell
     * @return Position of the cell
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Set the position of a cell
     * @param position Position to cet the cell to
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Get the inventory of the cell
     * @return Inventory of the cell
     */
    public CellInventory getInventory() {
        return inventory;
    }

    /**
     * Set the inventory of the cell
     * @param inventory Of the cell
     */
    public void setInventory(CellInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Append an IBoardElement to the inventory
     * @param element Element to append to the inventory
     */
    public void appendToInventory(IBoardElement element){
        this.inventory.addElement(element);
    }

    /**
     * Check whether one cell is the same as another.
     * Test for both position and inventory
     * @param o The object to compare
     * @return True if the same, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapCell)) return false;
        MapCell mapCell = (MapCell) o;
        return getPosition().equals(mapCell.getPosition()) &&
                getInventory().equals(mapCell.getInventory());
    }

    /**
     * Find the hashcode of the cell
     * @return Hashcode of the cell
     */
    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getInventory());
    }
}
