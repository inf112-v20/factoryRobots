package inf112.app.screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.map.Map;

import java.util.NoSuchElementException;

public class CardGUI {
    private static CardGUI instance;
    private TiledMap cardUI;
    private TiledMapTileLayer cardLayer;
    private TiledMapTileLayer backgroundLayer;

    private CardGUI(){
        //Loading map
        TmxMapLoader loader = new TmxMapLoader();
        cardUI = loader.load("assets/CardUI.tmx");

        //Loading layers
        cardLayer = (TiledMapTileLayer) cardUI.getLayers().get("Cards");
        backgroundLayer = (TiledMapTileLayer) cardUI.getLayers().get("Background");

    }



    public static synchronized CardGUI getInstance(){
        if (instance == null){
            instance = new CardGUI();
        }
        return instance;
    }


    public TiledMap getTiles() {
        return cardUI;
    }
}
