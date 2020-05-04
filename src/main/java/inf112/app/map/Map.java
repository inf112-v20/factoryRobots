package inf112.app.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.cards.CardDeck;
import inf112.app.game.RoboRally;
import inf112.app.objects.*;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that keeps track of the map and it's layers
 * as well as all the objects on it
 */
public class Map {

    //Map and layers
    private static Map cellMap;
    private TiledMap map;

    private int mapSizeX;
    private int mapSizeY;
    private MapCellList cellList;
    private ArrayList<Robot> robotList;
    private CardDeck deck;
    private int doneProgrammingCount = 0;

    private ArrayList<ILaserInteractor> laserObjects;

    private Position[] spawnPoints = new Position[RoboRally.MAX_PLAYER_AMOUNT];
    private int finalFlagNum;

    /**
     * Create a init the Map object by map title
     * @param mapName Name of the map. Must be without extension and full path
     */
    public Map(String mapName){
        String pathToMap = "assets/" + mapName + ".tmx";

        //Loading map
        TmxMapLoader loader = new TmxMapLoader();
        this.map = loader.load(pathToMap);
        initializeObjects();

    }

    /**
     * Create a init the Map object by TiledMap. Much faster if map is already loaded.
     * @param tiledMap The TiledMap object to init objects from
     */
    public Map(TiledMap tiledMap){
        this.map = tiledMap;
        initializeObjects();
    }

    /**
     * Init objects
     */
    private void initializeObjects(){
        MapProperties props = map.getProperties();
        mapSizeX = props.get("width",Integer.class);
        mapSizeY = props.get("height",Integer.class);
        cellList = new MapCellList(mapSizeX, mapSizeY, map.getLayers());

        laserObjects = obtainLaserObjects();
        robotList = new ArrayList<>();
        spawnPoints = getSpawnPoints(getLayer("StartPosition"));
        createAllFlags();
    }

    /**
     * Method used by the constructor to find all the {@link ILaserInteractor}'s on the map
     * @return All objects on the map that are able to shoot and interact with lasers
     */
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

    public Position[] getSpawnPoints (TiledMapTileLayer spawnLayer) {
        Position[] positions = new Position[8];
        for(int x = 0; x < mapSizeX; x++){
            for(int y = 0; y < mapSizeY; y++){
                if (spawnLayer.getCell(x, y) != null) {
                    TiledMapTile tile = spawnLayer.getCell(x, y).getTile();
                    int num = (int) tile.getProperties().get("num");
                    positions[num - 1] = new Position(x, y);
                }
            }
        }
        return positions;
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
        String key = layerName.substring(0,1).toUpperCase() + layerName.substring(1);
        return (TiledMapTileLayer) map.getLayers().get(key);
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
            assert wall != null;
            valid = !wall.blocks(true, currentPos.getDirection().copyOf());
        }
        if(containsWall(next)){
            Wall wall = (Wall) findWall(next.getInventory().getElements());
            assert wall != null;
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
     * @param tiledMap TiledMap to create a map instance from
     * @return true if cellMap was created and false if not.
     */
    public static synchronized boolean setInstance(TiledMap tiledMap){
        if (cellMap == null) {
            cellMap = new Map(tiledMap);
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

    /**
     * Method for removing all the graphics from a {@link TiledMapTileLayer}
     * @param layer which the elements should be removed from
     */
    public void clearLayer(TiledMapTileLayer layer){
        for(int x = 0; x < layer.getWidth(); x++){
            for(int y = 0; y < layer.getHeight(); y++){
                layer.setCell(x,y,null);
            }
        }
    }

    /**
     * Method for triggering all the lasers on the map to fire
     */
    public void fireLasers(){
        for(int i = 0; i<laserObjects.size(); i++){
            laserObjects.get(i).fireLaser();
        }
    }


    /**
     * Method for turning off the lasers. <br>
     * Resets the timer and clears all the laser graphics from the map
     */
    public void deactivateLasers(){
        clearLayer(getLayer("laser"));
        clearLayer(getLayer("laser2"));
        for(Robot r : robotList){
            r.isHit = false;
        }
    }

    /**
     * Method used by the {@link Robot} constructor to make sure that
     * the object is registered in all required lists .
     * @param robot to be registered
     */
    public void registerRobot(Robot robot){
        cellList.getCell(robot.getPos()).appendToInventory(robot);
        laserObjects.add(robot);
        robotList.add(robot);
    }

    /**
     * Method for checking if there is a robot in a given cell on the map
     * @param pos Position of the cell to check
     * @return the robot if there is one, otherwise null
     */
    public Robot robotInTile(Position pos){
        ArrayList<IBoardElement> elems = cellList.getCell(pos).getInventory().getElements();
        for(IBoardElement e : elems){
            if(e instanceof Robot){
                return (Robot) e;
            }
        }
        return null;
    }

    public ArrayList<Robot> getRobotList() {
        return robotList;
    }

    /**
     * Method for deleting specific robots from the registries. <br>
     * Mostly used for testing
     * @param robot to be deleted
     */
    public void deleteRobot(Robot robot){
        cellList.getCell(robot.getPos()).getInventory().getElements().remove(robot);
        laserObjects.remove(robot);
        robotList.remove(robot);
    }

    /**
     * Method for deleting all the robots on the map.
     * Mostly used for testing purposes as the instance
     * can get cluttered after a lot of tests
     */
    public void clearBots(){
        for(Robot robot : robotList){
            cellList.getCell(robot.getPos()).getInventory().getElements().remove(robot);
            laserObjects.remove(robot);
        }
        robotList.clear();
    }

    public void setDeck(CardDeck deck){
        this.deck = deck;
    }

    public CardDeck getDeck(){
        return this.deck;
    }

    public void incrementDoneProgramming(){
        doneProgrammingCount++;
    }

    public void resetDoneProgramming(){
        doneProgrammingCount = 0;
        for(Robot r : robotList)
            r.setDoneProgramming(false);
    }

    public boolean checkForTimerActivation(){
        return doneProgrammingCount == robotList.size()-1;
    }

    public boolean checkIfAllRobotsReady(){
        return doneProgrammingCount == robotList.size();
    }

    public void setRobotList(ArrayList<Robot> robotList){
        clearBots();
        for(Robot r : robotList){
            registerRobot(r);
        }
    }

    public Position getSpawnpoint(int index){
        return spawnPoints[index];
    }

    public void createAllFlags(){
        finalFlagNum = -1;
        for(int x = 0; x < mapSizeX; x++){
            for(int y = 0; y < mapSizeY; y++){
                MapCell cell = cellList.getCell(x,y);
                ArrayList<IBoardElement> inventory = cell.getInventory().getElements();
                for(IBoardElement elem : inventory){
                    if(elem instanceof Flag){
                        if(((Flag) elem).getNum() > finalFlagNum){
                            finalFlagNum = ((Flag) elem).getNum();
                        }
                    }
                }
            }
        }
    }

    public int getFinalFlagNum(){
        return finalFlagNum;
    }

}
