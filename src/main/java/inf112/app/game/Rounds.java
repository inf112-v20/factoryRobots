package inf112.app.game;

import inf112.app.cards.CardDeck;
import inf112.app.cards.CardSlot;
import inf112.app.objects.Robot;

import java.util.ArrayList;

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
        pickCards();
    }

    public void putBackPlayers(){
        for (Robot r : robots){
            if (!r.isDead() && r.hasLostLife()){
                r.backToCheckPoint();
            }
        }
    }

    private void dealCards(){
        for (Robot r : robots){
            r.dealNewCards();
        }

    }
    private void pickCards(){
        CardSlot[] slots = CardUI.getInstance().getBottomCardSlots();
        for (Robot r : robots){
            for (CardSlot slot : slots){
                if (slot.hasCard()){
                    r.doneProgramming();
                }
            }
        }
    }

    /**
     * method for finding out when to start the timer
     * @return boolean
     */
    private boolean startTimer(){
        ArrayList<Robot> count = new ArrayList<>();
        for (Robot r : robots){
            if (r.doneProgramming()){
                count.add(r);
            }
        }
        if (count.size() == robots.size() - 1){
            return true;
        }else{
            return false;
        }

    }

    //fase

}

