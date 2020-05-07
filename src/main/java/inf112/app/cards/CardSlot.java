package inf112.app.cards;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.game.CardUI;
import inf112.app.game.TiledMapStage;

/**
 * Class for representing a slot that may or may not hold a card,
 * when cards are extracted from the carddeck they must be held in a slot
 */
public class CardSlot {
    private ICard card;
    private final String position;
    private int xCoord;
    private int yCoord;
    private TiledMapTileLayer cardLayer;
    private boolean isLocked;
    private final boolean noUI;

    /**
     * Constructor used when the slot will be represented graphically, i.e. the slot belongs to the user
     * @param x x-coordinate of the slot in the UI grid
     * @param y y-coordinate of the slot in the UI grid
     * @param position String denoting if the slot belongs among the bottom slots, i.e. the slots
     *                 holding the cards will be programmed into the robot, or the slots on the side
     */
    public CardSlot(int x, int y, String position){
        card = null;
        xCoord = x;
        yCoord = y;
        CardUI cardUI = CardUI.getInstance();
        cardLayer = (TiledMapTileLayer) cardUI.getCardUITiles().getLayers().get("Cards");
        this.position = position;
        this.isLocked = false;
        noUI = false;
    }

    /**
     * Constructor used to create slots that won't be represented graphically,
     * i.e. it the slot doesn't belong to a user
     * @param position String denoting if the slot belongs among the bottom slots, i.e. the slots
     *                 holding the cards will be programmed into the robot, or the slots on the side
     */
    public CardSlot(String position){
        card = null;
        this.position = position;
        this.isLocked = false;
        noUI = true;
    }

    /**
     * Adds a card to the slot if there isn't another card occupying it
     * @param newCard The card to be added
     * @param stage The stage which holds the {@link inf112.app.game.CardSlotActor}'s
     *              in case the slot belongs to the user. If certain it doesn't then null may be passed,
     *              but avoid if possible
     * @return True if the card could be added false if not
     */
    public boolean addCard(ICard newCard, TiledMapStage stage){
        if(card == null){
            this.card = newCard;
            if(!noUI) {
                cardLayer.setCell(xCoord, yCoord, card.getCardTile());
                stage.getActorFromGrid(xCoord, yCoord).setCell(cardLayer.getCell(xCoord, yCoord));
            }
            return true;
        }
        return false;
    }

    /**
     * Removes the card from the slot if there is one
     * @return The card that has been removed
     */
    public ICard removeCard(){
        if(card == null){
            return null;
        } else {
            ICard value = card;
            card = null;
            if(!noUI){
                cardLayer.setCell(xCoord,yCoord,null);
            }
            return value;
        }
    }

    /**
     * Locks the slot, makes it unclickable for the user if it belongs to the user
     */
    public void lockSlot(){
        this.isLocked = true;
        if(!noUI){
            CardUI.getInstance().lockProgramSlot(xCoord);
        }
    }

    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Unlocks the slot, makes it clickable for the user if it belongs to the user
     */
    public void unlockSlot(){
        this.isLocked = false;
        if(!noUI){
            CardUI.getInstance().unlockProgramSlot(xCoord);
        }
    }

    public String getPosition() {
        return position;
    }

    public boolean hasCard(){
        return card != null;
    }

    public ICard getCard() {
        return card;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }
}
