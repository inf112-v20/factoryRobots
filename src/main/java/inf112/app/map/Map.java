package inf112.app.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.cards.CardDeck;
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
    private TiledMapTileLayer spawnpoints;

    private int mapSizeX;
    private int mapSizeY;
    private MapCellList cellList;
    private ArrayList<Robot> robotList;
    private CardDeck deck;
    private int doneProgrammingCount = 0;

    private TiledMap laserSprites;
    private ArrayList<ILaserInteractor> laserObjects;
    private int laserTimer = 0;
    private boolean lasersActive = false;

    private TiledMap gameButtons;

    /**
     * Create a init the Map object by map title
     * @param mapName Name of the map. Must be without extension and full path
     */
    public Map(String mapName){
        String pathToMap = "assets/" + mapName + ".tmx";

        //Loading map
        TmxMapLoader loader = new TmxMapLoader();
        this.map = loader.load(pathToMap);
        laserSprites = loader.load("assets/Lasers.tmx");
        initializeObjects();

        gameButtons = loader.load("assets/GameButtons/Buttons.tmx");
    }

    /**
     * Create a init the Map object by TiledMap. Much faster if map is already loaded.
     * @param tiledMap The TiledMap object to init objects from
     */
    public Map(TiledMap tiledMap, TiledMap laserSprites, TiledMap gameButtons){
        this.map = tiledMap;
        this.laserSprites = laserSprites;
        this.gameButtons = gameButtons;
        initializeObjects();

    }

    /**
     * Init objects
     */
    private void initializeObjects(){

        //Loading layers
        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");
        holeLayer = (TiledMapTileLayer) map.getLayers().get("Hole");
        flagLayer = (TiledMapTileLayer) map.getLayers().get("Flag");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");
        gearLayer = (TiledMapTileLayer) map.getLayers().get("Gear");
        wallLayer = (TiledMapTileLayer) map.getLayers().get("Wall");
        conveyorLayer = (TiledMapTileLayer) map.getLayers().get("Conveyor");
        laserLayer = (TiledMapTileLayer) map.getLayers().get("Laser");
        spawnpoints = (TiledMapTileLayer) map.getLayers().get(("SpawnPoints"));

        //Extra layer so lasers can cross each other
        laser2Layer = (TiledMapTileLayer) map.getLayers().get("Laser2");
        utilityLayer = (TiledMapTileLayer) map.getLayers().get("Utility");


        MapProperties props = map.getProperties();
        mapSizeX = props.get("width",Integer.class);
        mapSizeY = props.get("height",Integer.class);
        cellList = new MapCellList(mapSizeX, mapSizeY, map.getLayers());

        laserObjects = obtainLaserObjects();
        robotList = new ArrayList<>();
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

    public getSpawnPoints () {
        return spawnpoints;
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
    public static synchronized boolean setInstance(TiledMap tiledMap, TiledMap laserSprites, TiledMap gameButtons){
        if (cellMap == null) {
            cellMap = new Map(tiledMap, laserSprites, gameButtons);
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
        for(ILaserInteractor object : laserObjects){
            object.fireLaser();
        }
        lasersActive = true;
    }

    /**
     * @return The amount of ticks since the lasers were activated
     */
    public int getLaserTimer() {
        return laserTimer;
    }

    /**
     *
     * @return true if lasers are active, otherwise false
     */
    public boolean lasersActive() {
        return lasersActive;
    }

    public void incrementLaserTimer(){
        laserTimer++;
    }

    /**
     * Method for turning off the lasers. <br>
     * Resets the timer and clears all the laser graphics from the map
     */
    public void deactivateLasers(){
        lasersActive = false;
        laserTimer = 0;
        clearLayer(laserLayer);
        clearLayer(laser2Layer);
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
    }

    public boolean checkForTimerActivation(){
        return doneProgrammingCount == robotList.size()-1;
    }

    public boolean checkIfAllRobotsReady(){
        return doneProgrammingCount == robotList.size();
    }

    public TiledMap getGameButtons() {
        return gameButtons;
    }
}
