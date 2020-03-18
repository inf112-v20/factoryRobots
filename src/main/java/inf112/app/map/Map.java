package inf112.app.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.objects.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Class that keeps track of the map and it's layers
 * as well as all the objects on it
 */
public class Map {

    //Map and layers
    private static Map cellMap;
    private TiledMap map;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer holeLayer;
    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer gearLayer;
    private TiledMapTileLayer wallLayer;
    private TiledMapTileLayer conveyorLayer;
    private TiledMapTileLayer laserLayer;
    private TiledMapTileLayer laser2Layer;
    private TiledMapTileLayer utilityLayer;

    private final int mapSizeX;
    private final int mapSizeY;
    private MapCellList cellList;

    private TiledMap laserSprites;
    private ArrayList<ILaserInteractor> laserObjects;
    private int laserTimer = 0;
    private boolean lasersActive = false;

    private Map(String mapName){
        String pathToMap = "assets/" + mapName + ".tmx";

        //Loading map
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load(pathToMap);
        laserSprites = loader.load("assets/Lasers.tmx");

        //Loading layers
        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");
        holeLayer = (TiledMapTileLayer) map.getLayers().get("Hole");
        flagLayer = (TiledMapTileLayer) map.getLayers().get("Flag");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");
        gearLayer = (TiledMapTileLayer) map.getLayers().get("Gear");
        wallLayer = (TiledMapTileLayer) map.getLayers().get("Wall");
        conveyorLayer = (TiledMapTileLayer) map.getLayers().get("Conveyor");
        laserLayer = (TiledMapTileLayer) map.getLayers().get("Laser");
        //Extra layer so lasers can cross each other
        laser2Layer = (TiledMapTileLayer) map.getLayers().get("Laser2");
        utilityLayer = (TiledMapTileLayer) map.getLayers().get("Utility");

        MapProperties props = map.getProperties();
        mapSizeX = props.get("width",Integer.class);
        mapSizeY = props.get("height",Integer.class);
        cellList = new MapCellList(mapSizeX, mapSizeY, map.getLayers());

        laserObjects = obtainLaserObjects();
    }

    private ArrayList<ILaserInteractor> obtainLaserObjects() {
        ArrayList<ILaserInteractor> objects = new ArrayList<>();
        for(int x = 0; x < mapSizeX; x++){
            for(int y = 0; y < mapSizeY; y++){
                MapCell cell = cellList.getCell(x,y);
                ArrayList<IBoardElement> inventory = cell.getInventory().getElements();
                for(IBoardElement elem : inventory){
                    if(elem instanceof ILaserInteractor){
                        objects.add((ILaserInteractor) elem);
                    }
                }
            }
        }
        return objects;
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

    public MapCellList getCellList() {
        return cellList;
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
            case "laser2":
                return laser2Layer;
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

    /**
     * Checks if a {@link MapCell} contains a wall
     * @param cell to be checked
     * @return true if there is a Wall object in the cell, else false
     */
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

    /**
     *
     * @param mapName name of the map to parse
     * @return true if cellMap was created and false if not.
     */
    public static synchronized boolean setInstance(String mapName){
        if (cellMap == null) {
            cellMap = new Map(mapName);
            return true;
        }
        return false;
    }

    /**
     *
     * @return returns the static created cellMap. Throws exception if it doesn't exist
     */
    public static synchronized Map getInstance(){
        if (cellMap == null)
            throw new NoSuchElementException("Could not find the cellMap");
        return cellMap;
    }

    public TiledMap getLaserSprites() {
        return laserSprites;
    }

    public void clearLayer(TiledMapTileLayer layer){
        for(int x = 0; x < layer.getWidth(); x++){
            for(int y = 0; y < layer.getHeight(); y++){
                layer.setCell(x,y,null);
            }
        }
    }

    public void fireLasers(){
        for(ILaserInteractor object : laserObjects){
            object.fireLaser();
        }
        lasersActive = true;
    }

    public int getLaserTimer() {
        return laserTimer;
    }

    public boolean lasersActive() {
        return lasersActive;
    }

    public void incrementLaserTimer(){
        laserTimer++;
    }

    public void deactivateLasers(){
        lasersActive = false;
        laserTimer = 0;
        clearLayer(laserLayer);
        clearLayer(laser2Layer);
    }

    public void registerRobot(Robot robot){
        cellList.getCell(robot.getPos()).appendToInventory(robot);
        laserObjects.add(robot);
    }

    public boolean robotInTile(Position pos){
        ArrayList<IBoardElement> elems = cellList.getCell(pos).getInventory().getElements();
        for(IBoardElement e : elems){
            if(e instanceof Robot){
                return true;
            }
        }
        return false;
    }

    public IBoardElement findTypeInTile(IBoardElement type, Position pos){
        ArrayList<IBoardElement> elems = cellList.getCell(pos).getInventory().getElements();
        for(IBoardElement e : elems){
            if(e.getClass().equals(type.getClass())){
                return e;
            }
        }
        return null;
    }
}
