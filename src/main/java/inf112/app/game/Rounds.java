package inf112.app.game;

import inf112.app.cards.CardDeck;
import inf112.app.cards.CardSlot;
import inf112.app.cards.CardStatus;
import inf112.app.cards.ICard;
import inf112.app.objects.Robot;

import java.util.ArrayList;
import java.util.Collections;

public class Rounds {
    private final int standardCardAmount = 9;
    private final ArrayList<Robot> robots;
    private int chooserIndex = 0;
    private final CardDeck deck;
    private boolean startTimer;

    public Rounds(CardDeck deck, ArrayList<Robot> robots){
        this.deck = deck;
        this.robots = robots;
    }

    private void startRound(){
        putBackPlayers();
        dealCards();

    }

    /**
     * method for putting the players who have lost a life
     * back to their checkpoint
     */
    public void putBackPlayers(){
        for (Robot r : robots){
            if (!r.isDead() && r.hasLostLife()){
                r.backToCheckPoint();
            }
        }
    }

    /**
     * dealing cards
     */
    private void dealCards(){
        for (Robot r : robots){
            r.dealNewCards();
        }

    }

    /**
     * method for finding out when to start the timer
     * @return boolean
     */
    private boolean startTimer(){
        int count = 0;
        for (Robot r : robots){
            if (r.doneProgramming()){
                count ++;
            }
        }
        return count == robots.size() -1;

    }

    //fases

    /**
     * methods for doing the actions in rights order for each of the cards
     */
    //f
    public void doFase(int fasenr){
        ArrayList<Integer> slotOne = new ArrayList<>();
        CardSlot[] slots = CardUI.getInstance().getBottomCardSlots();
        for (Robot r : robots){
            slotOne.add(slots[fasenr].getCard().getPoint());
        }
        Collections.sort(slotOne, Collections.reverseOrder());
        for (int i = 0; i < slotOne.size(); i ++) {
            for (Robot r : robots) {
                if (slotOne.get(i) == r.getProgrammedCard(fasenr).getPoint()){
                   ICard card = r.getProgrammedCard(fasenr);
                   card.doAction(r);
                }
            }
        }
        putBackPlayers();
    }
}

