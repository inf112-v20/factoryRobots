package inf112.app.map;

import inf112.app.objects.Position;

public class MapCell {
    private Position position;
    private CellInventory inventory;

    public MapCell(Position position, CellInventory inventory) {
        this.position = position;
        this.inventory = inventory;
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
}
