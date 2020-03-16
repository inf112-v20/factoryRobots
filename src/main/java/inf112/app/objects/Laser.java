package inf112.app.objects;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.map.Map;
import inf112.app.map.MapCell;

import java.util.ArrayList;

public class Laser {

    private ILaserInteractor owner;
    private boolean isDouble;
    private TiledMapTileLayer.Cell beam;

    public Laser (ILaserInteractor owner, boolean isDouble) {
        this.owner = owner;
        this.isDouble = isDouble;
    }

   private ArrayList<MapCell> findLaserPath() {
       Position laserBeam = owner.getPos().copyOf();
       ArrayList<MapCell> path = new ArrayList<>();
       Map map = Map.getInstance();

       path.add(map.getCellList().getCell(laserBeam));
       laserBeam.moveInDirection();

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
           if(!map.validMove(laserBeam)){
               return path;
           }
          /* for (IBoardElement e : elements) {
               if (e instanceof ILaserInteractor) {
                   if(e instanceof Wall){
                       Wall wall = (Wall) e;
                       if(wall.blocks(false,laserBeam.getDirection())) {
                           path.remove(path.size() - 1);
                       }
                       return path;
                   }
               }
           }*/
       }
       return path;
   }

   public void fire(){
       ArrayList<MapCell> path = findLaserPath();
       Map map = Map.getInstance();
       Direction dir = owner.getPos().getDirection().copyOf();
       boolean horizontal = (dir.getDirEnum() == Direction.DirEnum.EAST || dir.getDirEnum() == Direction.DirEnum.WEST);

       int index = -1;
       if(horizontal){
           index = (!isDouble) ? 0 : 2;
       } else {
           index = (!isDouble) ? 1 : 3;
       }
       TiledMapTileLayer applicationLayer = (TiledMapTileLayer) map.getLaserSprites().getLayers().get(0);
       for(MapCell cell : path){
           Position pos = cell.getPosition();
           TiledMapTileLayer.Cell laser = applicationLayer.getCell(index,0);
            map.getLayer("laser").setCell(pos.getXCoordinate(),pos.getYCoordinate(),laser);
       }
   }
}


