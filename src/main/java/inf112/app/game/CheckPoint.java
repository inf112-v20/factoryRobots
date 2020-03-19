package inf112.app.game;

import inf112.app.map.Map;
import inf112.app.objects.*;

import java.util.ArrayList;

public class CheckPoint extends Robot {

    public CheckPoint(Position pos, String charName) {
        super(pos, charName);
    }

    private RepairStation extractRepairStationFromCell(Position pos){
        Map map = Map.getInstance();
        ArrayList<IBoardElement> objectList = map.getCellList().getCell(pos).getInventory().getElements();
        RepairStation position = null;
        for (IBoardElement e : objectList) {
            if (e instanceof RepairStation) {
                position = (RepairStation) e;
            }
        }
        return position;
    }

    public void updateCheckpoint(){
        if
    }
}
