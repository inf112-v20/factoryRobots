package inf112.app.game;

import inf112.app.cards.ICard;
import inf112.app.map.Map;
import inf112.app.objects.IBoardElement;
import inf112.app.objects.Laser;
import inf112.app.objects.Robot;

import java.util.ArrayList;
import java.util.Collections;

public class Rounds {
    private final ArrayList<Robot> robots;

    public Rounds(){
        this.robots = Map.getInstance().getRobotList();
    }

    public void startRound(){
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
                r.setLostLife(false);
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


    /**
     * method for doing the actions in rights order for each of the cards
     */
    public void doPhase(int phaseNum){
        ArrayList<Integer> slotOne = new ArrayList<>();
        for (Robot r : robots){
            ICard card = r.getProgrammedCard(phaseNum);
            if(card!=null){
                slotOne.add(card.getPoint());
            }
        }
        Collections.sort(slotOne, Collections.reverseOrder());
        for (int i = 0; i < slotOne.size(); i ++) {
            for (Robot r : robots) {
                ICard card = r.getProgrammedCard(phaseNum);
                if(card == null){
                    continue;
                }
                if (slotOne.get(i) == card.getPoint()){
                   card.doAction(r);
                }
            }

        }
        putBackPlayers();
    }
}

