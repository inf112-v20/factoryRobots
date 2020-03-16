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
    //Used as a lookup table for the stage
    private CardSlot[][] lookupSlots;


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
        lookupSlots = new CardSlot[8][6];

        for(int i = 0; i<5; i++){
            CardSlot newSlot = new CardSlot(i,0,"bottom");
            bottomCardSlots[i] = newSlot;
            lookupSlots[i][0] = newSlot;
        }
        for(int i = 9; i>0; i--){
            int x = 6+(i%2);
            int y = (i/2)+1;
            CardSlot newSlot = new CardSlot(x,y,"side");
            sideCardSlots[i-1] = newSlot;
            lookupSlots[x][y] = newSlot;
        }
    }

    public CardSlot getSlotFromCoordinates(int x, int y){
        return lookupSlots[x][y];
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

    public CardSlot findAvailableSlot(String where) {
        if("bottom".equals(where)){
            for(int i = 0; i<bottomCardSlots.length; i++){
                if(!bottomCardSlots[i].hasCard()){
                    return bottomCardSlots[i];
                }
            }
        } else if("side".equals(where)){
            for(int i = 0; i<sideCardSlots.length; i++){
                if(!sideCardSlots[i].hasCard()){
                    return sideCardSlots[i];
                }
            }
        }
        return null;
    }
}
