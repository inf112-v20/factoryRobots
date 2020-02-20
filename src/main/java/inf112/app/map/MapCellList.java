package inf112.app.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.objects.IBoardElement;
import inf112.app.objects.Position;

import java.util.ArrayList;
import java.util.Iterator;

public class MapCellList {
    private ArrayList<MapCell> celleListe;

    MapCellList(int sizeX, int sizeY, MapLayers layers){
        celleListe = new ArrayList<>();

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                CellInventory inventory = new CellInventory();
                inventory.setElements(findObjectsInLayers(x, y, layers));
                celleListe.add(new MapCell(new Position(x, y), new CellInventory()));
            }
        }
    }

    private ArrayList<IBoardElement> findObjectsInLayers(int x, int y, MapLayers layers){
        ArrayList<IBoardElement> elements = new ArrayList<>();
        Iterator<MapLayer> it = layers.iterator();
        if (it.hasNext()){
            TiledMapTileLayer layer = (TiledMapTileLayer) it.next();
            if (layer.getCell(x, y) != null){
                //elements.add(layer.getCell(x, y).getTile().getId()); #TODO Implement factory function
            }
        }
        return elements;
    }
    public MapCell getCell(Position p){
        for (MapCell celle: celleListe){
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
        celleListe.add(new MapCell(p, inventory));
    }
    private void setCell(int x, int y, CellInventory inventory){
        Position p = new Position(x,y);
        setCell(p, inventory);
    }
}
