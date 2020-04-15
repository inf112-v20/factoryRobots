package inf112.app.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.cards.CardSlot;
import inf112.app.cards.ICard;

public class CardSlotActor extends ButtonActor {

    private TiledMapTileLayer.Cell cell;

    private final CardSlot slot;

    private final TiledMapStage stage;

    private boolean pushable;

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
        stage.getActorFromGrid(newSlot.getxCoord(), newSlot.getyCoord()).setCell(this.cell);
        this.cell = null;
        ICard card = slot.removeCard();
        newSlot.addCard(card);
    }

    public void setPushable(boolean pushable){
        this.pushable = pushable;
    }
}
