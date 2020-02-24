package inf112.app.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.objects.Direction;
import inf112.app.objects.IBoardElement;
import inf112.app.objects.Position;
import inf112.app.objects.Wall;

import java.util.ArrayList;

public class Map {

    //Map and layers
    private TiledMap map;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer holeLayer;
    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer gearLayer;
    private TiledMapTileLayer wallLayer;
    private TiledMapTileLayer conveyorLayer;
    private TiledMapTileLayer laserLayer;
    private TiledMapTileLayer utilityLayer;

    private final int mapSizeX;
    private final int mapSizeY;
    private MapCellList cellList;

    public Map(String mapName){
        String pathToMap = "assets/" + mapName + ".tmx";

        //Loading map
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load(pathToMap);

        //Loading layers
        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");
        holeLayer = (TiledMapTileLayer) map.getLayers().get("Hole");
        flagLayer = (TiledMapTileLayer) map.getLayers().get("Flag");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");
        gearLayer = (TiledMapTileLayer) map.getLayers().get("Gear");
        wallLayer = (TiledMapTileLayer) map.getLayers().get("Wall");
        conveyorLayer = (TiledMapTileLayer) map.getLayers().get("Conveyor");
        laserLayer = (TiledMapTileLayer) map.getLayers().get("Laser");
        utilityLayer = (TiledMapTileLayer) map.getLayers().get("Utility");

        MapProperties props = map.getProperties();
        mapSizeX = props.get("width",Integer.class);
        mapSizeY = props.get("height",Integer.class);
        cellList = new MapCellList(mapSizeX, mapSizeY, map.getLayers());
        /* Add new object to celleListe
        MapCell celle = celleListe.getCell(5,5);
        celle.appendToInventory(new Wall());
         */
    }

    public int getMapSizeX(){
        return mapSizeX;
    }

    public int getMapSizeY(){
        return mapSizeY;
    }

    public TiledMap getMap() {
        return map;
    }

    public TiledMapTileLayer getLayer(String layerName){
        switch(layerName) {
            case "player":
                return playerLayer;
            case "hole":
                return holeLayer;
            case "board":
                return boardLayer;
            case "flag":
                return flagLayer;
            case "gear":
                return gearLayer;
            case "wall":
                return wallLayer;
            case "conveyor":
                return conveyorLayer;
            case "laser":
                return laserLayer;
            case "utility":
                return utilityLayer;
            default:
                throw new IllegalArgumentException("Layer name is invalid");
        }
    }

    /**
     * Checks if there is a wall blocking the move or if it is out of bounds
     * @param currentPos Position the objects is moving from
     * @return true if the move can be made
     */
    public boolean validMove(Position currentPos){
        Position newPos = currentPos.copyOf();
        newPos.moveInDirection();
        //Move is out of bounds
        if(newPos.getXCoordinate() >= mapSizeX || newPos.getYCoordinate() >= mapSizeY ||
                newPos.getXCoordinate() < 0 || newPos.getYCoordinate() < 0){
            return false;
        }

        MapCell current = cellList.getCell(currentPos);
        MapCell next = cellList.getCell(newPos);
        boolean valid = true;
        //Check if there are walls that are blocking
        if(containsWall(current)){
            Wall wall = (Wall) findWall(current.getInventory().getElements());
            valid = !wall.blocks(true, currentPos.getDirection().copyOf());
        }
        if(containsWall(next)){
            Wall wall = (Wall) findWall(next.getInventory().getElements());
            return valid && !wall.blocks(false, newPos.getDirection().copyOf());
        }
        return valid;
    }


    private boolean containsWall(MapCell cell){
        ArrayList<IBoardElement> inventory = cell.getInventory().getElements();
        return findWall(inventory) != null;
    }

    /**
     * Find and returns a wall in the cell if there are any
     * @param inventory The elements in the inventory of a cell
     * @return The wall if the inventory contains any, null if not
     */
    private IBoardElement findWall(ArrayList<IBoardElement> inventory){
        for(IBoardElement elem : inventory) {
            if (elem instanceof Wall) {
                return elem;
            }
        }
        return null;
    }
}
