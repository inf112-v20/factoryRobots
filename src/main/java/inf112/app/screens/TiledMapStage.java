package inf112.app.screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TiledMapStage extends Stage {
    private TiledMap tiledMap;
    private TiledMapTileLayer cardLayer;


    public TiledMapStage(){
        CardUI cardUI = CardUI.getInstance();
        tiledMap = cardUI.getTiles();

        cardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Cards");
        createActor(cardLayer);
    }

    private void createActor(TiledMapTileLayer layer){
        for(int x = 0; x < layer.getWidth(); x++) {
            for(int y = 0; y < layer.getHeight(); y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                TiledMapActor actor = new TiledMapActor(tiledMap, layer, cell);
                actor.setBounds(x, y*(1.5f), 1, 1.5f);  //height 1.5 since that is the cards ratio (400x600)
                addActor(actor);                                        //*1.5f to compensate the stretch downward
                EventListener eventListener = new TiledMapClickListener(actor);
                actor.addListener(eventListener);
            }
        }
    }

    @Override
    public void act() {
        //Reset list of actors
        getActors().clear();
        //Refreshed list based on tiledmap
        createActor(cardLayer);
        super.act();
    }
}
