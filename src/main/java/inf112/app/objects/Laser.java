package inf112.app.objects;

import inf112.app.map.Map;
import inf112.app.map.MapCell;

import java.util.ArrayList;

public class Laser {

    private ILaserInteractor owner;
    private boolean isDouble;

    public Laser (ILaserInteractor owner, boolean isDouble) {
        this.owner = owner;
        this.isDouble = isDouble;
    }

   public ArrayList<MapCell> findLaserPath() {
       Position laserBeam = owner.getPos().copyOf();
       ArrayList<MapCell> path = new ArrayList<>();
       Map map = Map.getInstance();

       //Check that beam doesn´t go out of bounds
       while (laserBeam.getXCoordinate() < map.getMapSizeX() && laserBeam.getYCoordinate() < map.getMapSizeY() &&
               laserBeam.getXCoordinate() >= 0 && laserBeam.getYCoordinate() >= 0) {

           MapCell current = map.getCellList().getCell(laserBeam);
           path.add(current);
           laserBeam.moveInDirection();
           ArrayList<IBoardElement> elements = current.getInventory().getElements();

           if (elements.isEmpty()) {
               continue;
           }
           for (IBoardElement e : elements) {
               if (e instanceof ILaserInteractor) {
                   return path;
               }
           }
       }
       return path;
   }
}


