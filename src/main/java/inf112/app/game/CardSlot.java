package inf112.app.game;

import java.util.NoSuchElementException;

public class CardSlot {
    private ICard card;

    public CardSlot(){
        card = null;
    }

    public boolean addCard(ICard newCard){
        if(card == null){
            this.card = newCard;
            return true;
        }
        return false;
    }

    public ICard removeCard(){
        if(card == null){
            throw new NoSuchElementException("");
        } else {
            ICard value = card.copyOf();
            card = null;
            return value;
        }
    }

}
