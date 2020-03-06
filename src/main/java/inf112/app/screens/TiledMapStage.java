package inf112.app.screens;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TiledMapStage extends Stage {
    private CardUI bottomUI;
    private TiledMap bottomBar;

    private CardUI sideUI;
    private TiledMap sideBar;

    public TiledMapStage(){
        bottomUI = new CardUI("bottom");
        sideUI = new CardUI("side");
        bottomBar = bottomUI.getTiles();
        sideBar = sideUI.getTiles();

        for(MapLayer layer : bottomBar.getLayers()) {
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;
            createActor(tileLayer);
        }

        for(MapLayer layer : sideBar.getLayers()) {
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;
            createActor(tileLayer);
        }
    }

    private void createActor(TiledMapTileLayer layer){
        for(int x = 0; x < layer.getWidth(); x++){
            TiledMapTileLayer.Cell cell = layer.getCell(x,0);
            TiledMapActor actor = new TiledMapActor(bottomBar, layer, cell);
            actor.setBounds(x, 0, 1, 1.5f);  //1.5 since that is the ratio (400x600)
            addActor(actor);
            EventListener eventListener = new TiledMapClickListener(actor);
            actor.addListener(eventListener);
        }
    }

    public CardUI getBottomUI() {
        return bottomUI;
    }

    public CardUI getSideUI() {
        return sideUI;
    }
}
