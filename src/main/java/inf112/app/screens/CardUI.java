package inf112.app.screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;



public class CardUI {
    private TiledMap cardUI;
    private static CardUI instance;


    private CardUI(){
        TmxMapLoader loader = new TmxMapLoader();
        cardUI = loader.load("assets/CardUI2.tmx");
    }


    public TiledMap getTiles() {
        return cardUI;
    }

    public static CardUI getInstance(){
        if(instance == null){
            instance = new CardUI();
        }
        return instance;
    }
}
