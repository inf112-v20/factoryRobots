package inf112.app.game;

import inf112.app.cards.CardDeck;
import inf112.app.cards.CardSlot;
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

    //fases

    //fase1
    public void doMoveOne(){
        ArrayList<ICard> slotOne = new ArrayList<>();
        CardSlot[] slots = CardUI.getInstance().getBottomCardSlots();
        for (Robot r : robots){
            slotOne.add(slots[0].getCard().getPoint());
        }
        Collections.sort(slotOne, Collections.reverseOrder());
        for (int i = 0; i < slotOne.size(); i ++) {
            for (Robot r : robots) {
                if (slotOne.get(i) == r.getProgrammedCard(0).getPoint()){
                   ICard card = r.getProgrammedCard(0);
                   card.doAction(r);
                }
            }
        }
        putBackPlayers();
    }
    //fase2
    public void doMoveTwo() {
        ArrayList<ICard> slotOne = new ArrayList<>();
        CardSlot[] slots = CardUI.getInstance().getBottomCardSlots();
        for (Robot r : robots) {
            slotOne.add(slots[1].getCard().getPoint());
        }
        Collections.sort(slotOne, Collections.reverseOrder());
        for (int i = 0; i < slotOne.size(); i++) {
            for (Robot r : robots) {
                if (slotOne.get(i) == r.getProgrammedCard(1).getPoint()) {
                    ICard card = r.getProgrammedCard(1);
                    card.doAction(r);
                }
            }
        }
        putBackPlayers();
    }
    //fase3
    public void doMoveThree() {
        ArrayList<ICard> slotOne = new ArrayList<>();
        CardSlot[] slots = CardUI.getInstance().getBottomCardSlots();
        for (Robot r : robots) {
            slotOne.add(slots[2].getCard().getPoint());
        }
        Collections.sort(slotOne, Collections.reverseOrder());
        for (int i = 0; i < slotOne.size(); i++) {
            for (Robot r : robots) {
                if (slotOne.get(i) == r.getProgrammedCard(2).getPoint()) {
                    ICard card = r.getProgrammedCard(2);
                    card.doAction(r);
                }
            }
        }
        putBackPlayers();
    }
    //fase4
    public void doMoveFour() {
        ArrayList<ICard> slotOne = new ArrayList<>();
        CardSlot[] slots = CardUI.getInstance().getBottomCardSlots();
        for (Robot r : robots) {
            slotOne.add(slots[3].getCard().getPoint());
        }
        Collections.sort(slotOne, Collections.reverseOrder());
        for (int i = 0; i < slotOne.size(); i++) {
            for (Robot r : robots) {
                if (slotOne.get(i) == r.getProgrammedCard(3).getPoint()) {
                    ICard card = r.getProgrammedCard(3);
                    card.doAction(r);
                }
            }
        }
        putBackPlayers();
    }
    //fase5
    public void doMoveFive() {
        ArrayList<ICard> slotOne = new ArrayList<>();
        CardSlot[] slots = CardUI.getInstance().getBottomCardSlots();
        for (Robot r : robots) {
            slotOne.add(slots[4].getCard().getPoint());
        }
        Collections.sort(slotOne, Collections.reverseOrder());
        for (int i = 0; i < slotOne.size(); i++) {
            for (Robot r : robots) {
                if (slotOne.get(i) == r.getProgrammedCard(4).getPoint()) {
                    ICard card = r.getProgrammedCard(4);
                    card.doAction(r);
                }
            }
        }
        putBackPlayers();
    }
}

