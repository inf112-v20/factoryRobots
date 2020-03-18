package inf112.app.objects;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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

   private ArrayList<MapCell> findLaserPath() {
       Position laserBeam = owner.getPos().copyOf();
       ArrayList<MapCell> path = new ArrayList<>();
       Map map = Map.getInstance();

       path.add(map.getCellList().getCell(laserBeam));
       laserBeam.moveInDirection();

       //Check that beam doesn´t go out of bounds
       while (map.validMove(laserBeam)) {
           MapCell current = map.getCellList().getCell(laserBeam);
           path.add(current);
           if(map.robotInTile(current.getPosition()) != null){
               break;
           }
           laserBeam.moveInDirection();
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
           if(map.getLayer("laser").getCell(pos.getXCoordinate(),pos.getYCoordinate()) == null){
               map.getLayer("laser").setCell(pos.getXCoordinate(),pos.getYCoordinate(),laser);
           } else {
               map.getLayer("laser2").setCell(pos.getXCoordinate(),pos.getYCoordinate(),laser);
           }

       }
       Robot potential = map.robotInTile(path.get(path.size()-1).getPosition());
       if(potential != null){
           potential.addDamageTokens(isDouble ? 2 : 1);
       }
   }
}


