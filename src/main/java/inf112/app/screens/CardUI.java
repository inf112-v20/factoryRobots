package inf112.app.screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.game.CardSlot;
import inf112.app.game.ICard;


public class CardUI {
    private TiledMap cardUI;
    private static CardUI instance;

    private CardSlot[] bottomCardSlots;
    private CardSlot[] sideCardSlots;


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

    public void initializeCardSlots(){
        bottomCardSlots = new CardSlot[5];
        sideCardSlots = new CardSlot[9];
        for(int i = 0; i<5; i++){
            bottomCardSlots[i] = new CardSlot(i,0);
        }
        for(int i = 9; i>0; i--){
            sideCardSlots[i-1] = new CardSlot(6+(i%2),(i/2)+1);
        }
    }

    public boolean addCardToSlot(ICard card, String where, int num){
        if("bottom".equals(where)){
            return bottomCardSlots[num].addCard(card);
        } else if("side".equals(where)){
            return sideCardSlots[num].addCard(card);
        }
        System.out.println("where parameter invalid");
        return false;
    }

    public CardSlot[] getBottomCardSlots() {
        return bottomCardSlots;
    }

    public CardSlot[] getSideCardSlots() {
        return sideCardSlots;
    }
}
