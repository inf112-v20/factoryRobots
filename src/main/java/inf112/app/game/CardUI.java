package inf112.app.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import inf112.app.cards.CardSlot;
import inf112.app.cards.ICard;
import inf112.app.map.Position;
import inf112.app.objects.Robot;


public class CardUI {
    private final TiledMap cardUI;
    private final TiledMapTileLayer damageTokens;
    private final TiledMapTileLayer uiButtons;
    private final TiledMapTileLayer laserSprites;
    private final TiledMapTileLayer buttonApplicationLayer;

    private TiledMapTileLayer.Cell[] healthLights;
    private TiledMapTileLayer.Cell robotThumbnail;
    private TiledMapTileLayer.Cell cardLock;

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
        user = new Player();
        user.assignRobot(new Robot(new Position(0,0),"player"));
    }

    /**
     * Standard constructor for the cardUI, takes TiledMaps and Textures loaded by the assetmanager as arguments,
     * initializes all the elements onto the UI.
     * @param cardUI Tiledmap holding the grid holding the card slots, where buttons damagetokens and other
     *               graphical elements of the UI will be applied in the constructor
     * @param buttons The tiledmap holding the graphics of the different buttons and button states which
     *                will be dynamically applied to the cardUI as the user interacts with the buttons
     * @param laserSprites The tiledmap holding all the different laser-beam graphics, which will be applied to the
     *                     laser paths in the tiledmap game-map when lasers are fired
     * @param healthSprites Sprite sheet texture holding all the different sprites for the lights displaying how many
     *                      lives the users robot has remaining
     * @param lock The texture of the lock that are displayed above the locked card-slots
     */
    private CardUI(TiledMap cardUI, TiledMap buttons, TiledMap laserSprites,
                   Texture healthSprites, Texture lock){
        this.cardUI = cardUI;
        damageTokens = (TiledMapTileLayer) buttons.getLayers().get("Tokens");
        uiButtons = (TiledMapTileLayer) buttons.getLayers().get("Buttons");
        buttonApplicationLayer = (TiledMapTileLayer) cardUI.getLayers().get("Buttons");
        this.laserSprites = (TiledMapTileLayer) laserSprites.getLayers().get("Laser");
        stage = null;

        //Get the health lights
        healthLights = new TiledMapTileLayer.Cell[4];
        TextureRegion[][] sprites = TextureRegion.split(healthSprites,400,600);

        for(int i = 0; i<healthLights.length; i++){
            healthLights[i] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(sprites[0][3-i]));
        }
        //Get the cardlocks
        TextureRegion[][] temp = TextureRegion.split(lock,400,600);
        cardLock = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(temp[0][0]));
    }

    /**
     * @param amount Sets how many of the health lights that should be green
     */
    public void setHealthLight(int amount){
        buttonApplicationLayer.setCell(buttonApplicationLayer.getWidth()-3,0,healthLights[amount]);
    }

    /**
     * Since {@link CardUI} is a singleton, we store it as a static instance so that all classes can get it.
     * Accepts the arguments and passes it to the constructor
     * @param cardUI The loaded cardUI
     * @param buttons The loaded button textures
     * @param laserSprites The loaded laser sprites texture
     * @param healthLight The loaded health lights texture
     * @param lock The loaded lock texture
     * @return Returns the created instance
     */
    public static CardUI setInstance(TiledMap cardUI, TiledMap buttons, TiledMap laserSprites, Texture healthLight,
                                     Texture lock){
        instance = new CardUI(cardUI,buttons,laserSprites,healthLight, lock);
        return instance;
    }

    /**
     * Since {@link CardUI} is a singleton, we store it as a static instance so that all classes can get it
     * @return The instance of the cardUI
     */
    synchronized public static CardUI getInstance(){
        if(instance == null){
            instance = new CardUI();
        }
        return instance;
    }

    /**
     * Deletes cardUI instance so that it can be reset and reloaded
     */
    synchronized public static void clearInstance(){
        instance = null;
    }

    /**
     * Sets which thumbnail that is displayed in the UI, showing the user which robot he is controlling
     * @param robot The thumbnail of the robot
     */
    public void setPlayerRobotGraphic(Texture robot){
        TextureRegion[][] temp = TextureRegion.split(robot,400,600);
        robotThumbnail = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(temp[0][0]));
        buttonApplicationLayer.setCell(buttonApplicationLayer.getWidth()-2,1,robotThumbnail);
    }

    /**
     * @return The actual tiledmap grid of the cardUI holding all the cards,
     * buttons and other UI elements
     */
    public TiledMap getCardUITiles() {
        return cardUI;
    }

    /**
     * Instantiates the users {@link CardSlot}'s, places them in the
     * UI and assigns them coordinates
     * @param player The user
     */
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

    /**
     * Assigns the damage token graphics to the appropriate tiles in the UI
     */
    public void initializeDamageTokens(){
        for (int i = 0; i < 4; i++){
            buttonApplicationLayer.setCell(i, 1, damageTokens.getCell(0,0));
        }
    }

    /**
     * Changes how many of the damage tokens that are lit up
     * @param tokens The amount of tokens that should be lit up
     */
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

    /**
     * @param x x-coordinate of the slot
     * @param y y-coordinate of the slot
     * @return The card slot contained in the cell at the given coordinates, if there is one
     */
    public CardSlot getSlotFromCoordinates(int x, int y){
        return lookupSlots[x][y];
    }

    /**
     * Looks up a slot with the help of the parameters and adds a card to that slot
     * @param card The card to be added
     * @param where A string denoting if the slot can be found on bottom line (programmed cards slots)
     *              or on the side (available cards slots)
     * @param num The index of the slot in the set of slots denoted by
     * @return true if the card could be added, false if not
     */
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

    /**
     * Finds the first free slot (i.e. that doesn't contain a card) in the set of slots denoted by where
     * @param where A string denoting if the method should look for a slot among the programmed cards slots
     *              or on the side among the available cards slots
     * @return The first available slot, or null if no one is available
     */
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

    public Player getUser() {
        return user;
    }

    /**
     * Makes the user unable to interact with the programmed cards {@link CardSlot}
     * @param xCoord index of the slot to be locked among the programmed cards slots
     */
    public void lockProgramSlot(int xCoord) {
        buttonApplicationLayer.setCell(xCoord,0,cardLock);
        CardSlot slot = bottomCardSlots[xCoord];
        ((CardSlotActor)stage.getActorFromGrid(slot.getxCoord(),slot.getyCoord())).setPushable(false);
    }

    /**
     * Makes the user able to interact with the programmed cards {@link CardSlot}
     * @param xCoord index of the slot to be unlocked among the programmed cards slots
     */
    public void unlockProgramSlot(int xCoord){
        buttonApplicationLayer.setCell(xCoord,0,null);
        CardSlot slot = bottomCardSlots[xCoord];
        ((CardSlotActor)stage.getActorFromGrid(slot.getxCoord(),slot.getyCoord())).setPushable(true);
    }
}
