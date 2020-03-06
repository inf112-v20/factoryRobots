package inf112.app.screens;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TiledMapStage extends Stage {
    private TiledMap tiledMap;


    public TiledMapStage(){
        CardUI cardUI = CardUI.getInstance();
        tiledMap = cardUI.getTiles();

        for(MapLayer layer : tiledMap.getLayers()) {
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;
            createActor(tileLayer);
        }

    }

    private void createActor(TiledMapTileLayer layer){
        for(int x = 0; x < layer.getWidth(); x++) {
            for(int y = 0; y < layer.getHeight(); y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                TiledMapActor actor = new TiledMapActor(tiledMap, layer, cell);
                actor.setBounds(x, y, 1, 1.5f);  //1.5 since that is the cards ratio (400x600)
                addActor(actor);
                EventListener eventListener = new TiledMapClickListener(actor);
                actor.addListener(eventListener);
            }
        }
    }
}
