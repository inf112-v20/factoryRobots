package inf112.app.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.cards.CardSlot;
import inf112.app.cards.ICard;
import inf112.app.map.Map;


public class CardUI {
    private TiledMap cardUI;
    private TiledMapTileLayer damageTokens;
    private static CardUI instance;

    private CardSlot[] bottomCardSlots;
    private CardSlot[] sideCardSlots;
    //Used as a lookup table for the stage
    private CardSlot[][] lookupSlots;
    private Player user;
    private TiledMapTileLayer cardUIButtonLayer;


    private CardUI(){
        TmxMapLoader loader = new TmxMapLoader();
        cardUI = loader.load("assets/CardUI2.tmx");
        damageTokens = (TiledMapTileLayer) Map.getInstance().getGameButtons().getLayers().get("Tokens");
        cardUIButtonLayer = (TiledMapTileLayer) cardUI.getLayers().get("Buttons");
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

    public void initializeCardSlots(Player player){
        user = player;
        bottomCardSlots = player.getCharacter().getProgrammedCards();
        sideCardSlots = player.getCharacter().getAvailableCards();
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

    public void initializeDamageTokens(){
        for (int i = 0; i < 5; i++){
            cardUIButtonLayer.setCell(i, 1, damageTokens.getCell(0,0));
        }
    }

    public void updateDamageTokens(int tokens) {
        initializeDamageTokens();
        int amountDouble = tokens / 2;
        boolean rest = (tokens % 2 == 1);
        for (int i = 0; i < amountDouble; i++) {
            cardUIButtonLayer.setCell(i, 1, damageTokens.getCell(2, 0));
        }
        if (rest) {
            cardUIButtonLayer.setCell(amountDouble, 1, damageTokens.getCell(1, 0));
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
