package inf112.app.objects;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.game.CardUI;
import inf112.app.map.Direction;
import inf112.app.map.Map;
import inf112.app.map.MapCell;
import inf112.app.map.Position;

import java.util.ArrayList;

/**
 * Class for representing the lasers
 */
public class Laser {
    private ILaserInteractor owner;
    private boolean isDouble;

    /**
     * Constructor for creating a laser
     * @param owner The object that holds the laser
     * @param isDouble true if the laser is a double beam
     */
    public Laser (ILaserInteractor owner, boolean isDouble) {
        this.owner = owner;
        this.isDouble = isDouble;
    }

    /**
     * Method for determining which cells the laser will traverse.
     * Goes until it is blocked by the edge of the map, robot or wall.
     * Used by the {@link #fire()} method
     * @return The lasers path
     */
   private ArrayList<MapCell> findLaserPath() {
       Position laserBeam = owner.getPos().copyOf();
       ArrayList<MapCell> path = new ArrayList<>();
       Map map = Map.getInstance();

       //Laser should start on the same cell as the owner
       path.add(map.getCellList().getCell(laserBeam));

       while (map.validMove(laserBeam)) {
           laserBeam.moveInDirection();
           MapCell current = map.getCellList().getCell(laserBeam);
           path.add(current);
           if(map.robotInTile(current.getPosition()) != null){
               break;
           }
       }
       return path;
   }

    /**
     * Triggers the laser to fire. Deals damage if there is a robot at the end of the beam. <br>
     * Applies the laser graphics until deactivated.
     * The {@link Map} class handles the duration and deactivation.
     */
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
       TiledMapTileLayer sourceLayer = CardUI.getInstance().getLaserSprites();
       for(MapCell cell : path){
           Position pos = cell.getPosition();
           TiledMapTileLayer.Cell laser = sourceLayer.getCell(index,0);
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


