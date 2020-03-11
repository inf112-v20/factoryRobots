package inf112.app.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.screens.CardUI;

import java.util.NoSuchElementException;

public class CardSlot {
    private ICard card;
    private int xCoord;
    private int yCoord;
    private TiledMapTileLayer cardLayer;

    public CardSlot(int x, int y){
        card = null;
        xCoord = x;
        yCoord = y;
        CardUI cardUI = CardUI.getInstance();
        cardLayer = (TiledMapTileLayer) cardUI.getTiles().getLayers().get("Cards");
    }

    public boolean addCard(ICard newCard){
        if(card == null){
            this.card = newCard;
            cardLayer.setCell(xCoord,yCoord,card.getCardTile());
            return true;
        }
        return false;
    }

    public ICard removeCard(){
        if(card == null){
            throw new NoSuchElementException("No card to remove in the slot");
        } else {
            ICard value = card.copyOf();
            card = null;
            cardLayer.setCell(xCoord,yCoord,null);
            return value;
        }
    }

}
