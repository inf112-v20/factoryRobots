package inf112.app.game;

import inf112.app.cards.CardDeck;
import inf112.app.objects.Robot;

import java.util.ArrayList;

public class Rounds {
    private final int standardCardAmount = 9;
    private final ArrayList<Robot> robots = null;
    private int chooserIndex = 0;
    private final CardDeck deck;

    public Rounds(CardDeck deck){
        this.deck = deck;

    }

    private void startRound(){
        putBackPlayers();
        dealCards();
        makePlayerPickCards();
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
    private void makePlayerPickCards(){

    }
}
