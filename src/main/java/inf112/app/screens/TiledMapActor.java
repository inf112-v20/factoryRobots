package inf112.app.screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import inf112.app.game.CardSlot;
import inf112.app.game.ICard;

public class TiledMapActor extends Actor {

    private TiledMap tiledMap;

    private TiledMapTileLayer tiledLayer;

    private TiledMapTileLayer.Cell cell;

    private CardSlot slot;

    public TiledMapActor(TiledMap tiledMap, TiledMapTileLayer tiledLayer, TiledMapTileLayer.Cell cell,
                         CardSlot slot) {
        this.tiledMap = tiledMap;
        this.tiledLayer = tiledLayer;
        this.cell = cell;
        this.slot = slot;
    }

    public TiledMapTileLayer.Cell getCell() {
        return cell;
    }

    public void clickAction() {
        if(slot == null){
            System.out.println("Slot is not initialized, something is very wrong");
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
        ICard card = slot.removeCard();
        newSlot.addCard(card);
    }
}
