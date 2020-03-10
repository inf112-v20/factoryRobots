package inf112.app.screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.game.CardSlot;


public class CardUI {
    private TiledMap cardUI;
    private static CardUI instance;

    private CardSlot[] bottomCardSlots;
    private CardSlot[] sideCardSlots;


    private CardUI(){
        TmxMapLoader loader = new TmxMapLoader();
        cardUI = loader.load("assets/CardUI2.tmx");

        initializeCardSlots();
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

    private void initializeCardSlots(){
        bottomCardSlots = new CardSlot[5];
        sideCardSlots = new CardSlot[9];
        for(int i = 0; i<5; i++){
            bottomCardSlots[i] = new CardSlot();
        }
        for(int i = 0; i<9; i++){
            sideCardSlots[i] = new CardSlot();
        }
    }
}
