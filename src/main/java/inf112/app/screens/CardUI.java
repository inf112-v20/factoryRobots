package inf112.app.screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;


public class CardUI {
    private TiledMap cardUI;
  /*  private TiledMapTileLayer cardLayer;
    private TiledMapTileLayer backgroundLayer;*/

    public CardUI(String spec){
        TmxMapLoader loader = new TmxMapLoader();
        if(spec == "bottom"){
            cardUI = loader.load("assets/CardUI.tmx");
        } else if (spec == "side"){
            cardUI = loader.load("assets/SideBar1.tmx");
        } else {
            throw new IllegalArgumentException("CardUI spec is invalid");
        }
        //Loading layers
       /* cardLayer = (TiledMapTileLayer) cardUI.getLayers().get("Cards");
        backgroundLayer = (TiledMapTileLayer) cardUI.getLayers().get("Background");*/

    }


    public TiledMap getTiles() {
        return cardUI;
    }
}
