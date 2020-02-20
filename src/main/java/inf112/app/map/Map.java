package inf112.app.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.objects.Wall;

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
    private MapCellList celleListe;

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
        celleListe = new MapCellList(mapSizeX, mapSizeY, map.getLayers());
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
}
