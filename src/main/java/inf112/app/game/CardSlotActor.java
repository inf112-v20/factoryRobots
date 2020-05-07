package inf112.app.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.cards.CardSlot;
import inf112.app.cards.ICard;

/**
 * Actor handling the click actions and listeners for the {@link CardSlot}s in the {@link CardUI}
 */
public class CardSlotActor extends ButtonActor {

    private TiledMapTileLayer.Cell cell;

    private final CardSlot slot;

    private final TiledMapStage stage;

    private boolean pushable;

    /**
     * @param cell The cell in the tiledmap grid holding the card slot
     * @param slot The slot contained within the cell of the specified cell in the tiledmap grid
     * @param stage The stage holding the tiledmap grid and actors
     */
    public CardSlotActor(TiledMapTileLayer.Cell cell, CardSlot slot, TiledMapStage stage) {
        this.cell = cell;
        this.slot = slot;
        this.stage = stage;
        pushable = true;
    }

    public TiledMapTileLayer.Cell getCell() {
        return cell;
    }

    public void setCell(TiledMapTileLayer.Cell cell) {
        this.cell = cell;
    }

    public void clickAction() {
        if(slot == null){
            System.out.println("Slot is not initialized, something is very wrong");
        }
        if(!pushable){
            return;
        }
        String cardPos = slot.getPosition();
        CardSlot newSlot = null;
        if("bottom".equals(cardPos)){
            newSlot = CardUI.getInstance().findAvailableSlot("side");
        } else if("side".equals(cardPos)){
            newSlot = CardUI.getInstance().findAvailableSlot("bottom");
        }
        if(newSlot==null){
            System.out.println("All card slots are occupied");
            return;
        }
        this.cell = null;
        ICard card = slot.removeCard();
        newSlot.addCard(card, stage);
    }

    public void setPushable(boolean pushable){
        this.pushable = pushable;
    }

    public CardSlot getSlot() {
        return slot;
    }
}
