package inf112.app.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.objects.IBoardElement;
import inf112.app.objects.ObjectFactory;
import inf112.app.objects.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MapCellList {
    private ArrayList<MapCell> cellList;
    private ArrayList<String> exclusionList;
    private ObjectFactory factory;

    MapCellList(int sizeX, int sizeY, MapLayers layers){
        cellList = new ArrayList<>();
        exclusionList = new ArrayList<>(Arrays.asList("Board","Hole"));
        factory = new ObjectFactory();

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                CellInventory inventory = new CellInventory();
                inventory.setElements(findObjectsInLayers(x, y, layers));
                cellList.add(new MapCell(new Position(x, y), inventory));
            }
        }
    }

    private ArrayList<IBoardElement> findObjectsInLayers(int x, int y, MapLayers layers){
        ArrayList<IBoardElement> elements = new ArrayList<>();
        Iterator<MapLayer> it = layers.iterator();
        while(it.hasNext()){
            TiledMapTileLayer layer = (TiledMapTileLayer) it.next();
            if (layer.getCell(x, y) != null && !exclusionList.contains(layer.getName())){
                TiledMapTile element = layer.getCell(x, y).getTile();
                elements.add(factory.generateObject(element));

            }
        }
        return elements;
    }

    public MapCell getCell(Position p){
        for (MapCell celle: cellList){
            if (celle.getPosition().equals(p)){
                return celle;
            }
        }
        return null;
    }
    public MapCell getCell(int x, int y){
        Position p = new Position(x,y);
        return getCell(p);
    }

    private void setCell(Position p, CellInventory inventory){
        cellList.add(new MapCell(p, inventory));
    }
    private void setCell(int x, int y, CellInventory inventory){
        Position p = new Position(x,y);
        setCell(p, inventory);
    }
}
