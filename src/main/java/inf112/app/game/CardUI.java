package inf112.app.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import inf112.app.cards.CardSlot;
import inf112.app.cards.ICard;


public class CardUI {
    private final TiledMap cardUI;
    private final TiledMapTileLayer damageTokens;
    private final TiledMapTileLayer uiButtons;
    private final TiledMapTileLayer laserSprites;
    private final TiledMapTileLayer buttonApplicationLayer;

    private TiledMapTileLayer.Cell[] healthLights;

    private static CardUI instance;

    private CardSlot[] bottomCardSlots;
    private CardSlot[] sideCardSlots;
    //Used as a lookup table for the stage
    private CardSlot[][] lookupSlots;
    private Player user;

    private TiledMapStage stage;

    /**
     * Constructor used only for testing
     */
    private CardUI(){
        TmxMapLoader loader = new TmxMapLoader();
        cardUI = loader.load("assets/CardUI2.tmx");
        buttonApplicationLayer = (TiledMapTileLayer) cardUI.getLayers().get("Buttons");

        TiledMap buttons = loader.load("assets/GameButtons/Buttons.tmx");
        damageTokens = (TiledMapTileLayer) buttons.getLayers().get("Tokens");
        uiButtons = (TiledMapTileLayer) buttons.getLayers().get("Buttons");

        TiledMap lasers = loader.load("assets/Lasers.tmx");
        laserSprites = (TiledMapTileLayer) lasers.getLayers().get("Laser");
    }

    private CardUI(TiledMap cardUI, TiledMap buttons, TiledMap laserSprites, Texture healthSprites){
        this.cardUI = cardUI;
        damageTokens = (TiledMapTileLayer) buttons.getLayers().get("Tokens");
        uiButtons = (TiledMapTileLayer) buttons.getLayers().get("Buttons");
        buttonApplicationLayer = (TiledMapTileLayer) cardUI.getLayers().get("Buttons");
        this.laserSprites = (TiledMapTileLayer) laserSprites.getLayers().get("Laser");
        stage = null;

        healthLights = new TiledMapTileLayer.Cell[4];
        TextureRegion[][] sprites = TextureRegion.split(healthSprites,400,600);

        for(int i = 0; i<healthLights.length; i++){
            healthLights[i] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(sprites[0][3-i]));
        }
    }

    public void setHealthLight(int amount){
        buttonApplicationLayer.setCell(buttonApplicationLayer.getWidth()-3,0,healthLights[amount]);
    }

    public static CardUI setInstance(TiledMap cardUI, TiledMap buttons, TiledMap laserSprites, Texture healthLight){
        instance = new CardUI(cardUI,buttons,laserSprites,healthLight);
        return instance;
    }

    public static CardUI getInstance(){
        if(instance == null){
            instance = new CardUI();
        }
        return instance;
    }

    public TiledMap getCardUITiles() {
        return cardUI;
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
            buttonApplicationLayer.setCell(i, 1, damageTokens.getCell(0,0));
        }
    }

    public void updateDamageTokens(int tokens) {
        initializeDamageTokens();
        int amountDouble = tokens / 2;
        boolean rest = (tokens % 2 == 1);
        for (int i = 0; i < amountDouble; i++) {
            buttonApplicationLayer.setCell(i, 1, damageTokens.getCell(2, 0));
        }
        if (rest) {
            buttonApplicationLayer.setCell(amountDouble, 1, damageTokens.getCell(1, 0));
        }
    }

    public CardSlot getSlotFromCoordinates(int x, int y){
        return lookupSlots[x][y];
    }

    public boolean addCardToSlot(ICard card, String where, int num){
        if("bottom".equals(where)){
            return bottomCardSlots[num].addCard(card,stage);
        } else if("side".equals(where)){
            return sideCardSlots[num].addCard(card,stage);
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

    public TiledMapTileLayer getUiButtons() {
        return uiButtons;
    }

    public TiledMapTileLayer getLaserSprites() {
        return laserSprites;
    }

    public void setTiledStage(TiledMapStage stage){
        this.stage = stage;
    }
}
