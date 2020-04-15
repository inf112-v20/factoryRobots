package inf112.app.cards;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.game.CardUI;

public class CardSlot {
    private ICard card;
    private final String position;
    private int xCoord;
    private int yCoord;
    private TiledMapTileLayer cardLayer;
    private boolean isLocked;
    private final boolean noUI;

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

    public CardSlot(String position){
        card = null;
        this.position = position;
        this.isLocked = false;
        noUI = true;
    }

    public boolean addCard(ICard newCard){
        if(card == null){
            this.card = newCard;
            if(!noUI) {
                cardLayer.setCell(xCoord, yCoord, card.getCardTile());
            }
            return true;
        }
        return false;
    }

    public ICard removeCard(){
        if(card == null){
            System.out.println("No card to remove in the slot");
            return null;
        } else {
            ICard value = card.copyOf();
            card = null;
            cardLayer.setCell(xCoord,yCoord,null);
            return value;
        }
    }

    public void lockSlot(){
        this.isLocked = true;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void unlockSlot(){
        this.isLocked = false;
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
