package inf112.app.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TiledMapStage extends Stage {
    private TiledMap tiledMap;
    private AssetManager manager;


    public TiledMapStage(){
        CardUI cardUI = CardUI.getInstance();
        tiledMap = cardUI.getTiles();
        manager = new AssetManager();

        for(MapLayer layer : tiledMap.getLayers()) {
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;
            createActor(tileLayer);
        }

    }

    private void createActor(TiledMapTileLayer layer){
        for(int x = 0; x < layer.getWidth(); x++) {
            for(int y = 0; y < layer.getHeight(); y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                TiledMapActor actor = new TiledMapActor(tiledMap, layer, cell, manager);
                actor.setBounds(x, y*(1.5f), 1, 1.5f);  //height 1.5 since that is the cards ratio (400x600)
                addActor(actor);                                        //*1.5f to compensate the stretch downwards
                EventListener eventListener = new TiledMapClickListener(actor);
                actor.addListener(eventListener);
            }
        }
    }


}
