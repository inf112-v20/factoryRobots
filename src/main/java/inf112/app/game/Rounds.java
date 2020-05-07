package inf112.app.game;

import inf112.app.cards.ICard;
import inf112.app.map.Map;
import inf112.app.objects.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for handling the ordering of the rounds and phases in a game-session
 */
public class Rounds {
    private final ArrayList<Robot> robots;
    private Map map;

    /**
     * Basic constructor, creates a Rounds instance and assigns the robotList and map to private fields
     */
    public Rounds() {
        this.robots = Map.getInstance().getRobotList();
        this.map = Map.getInstance();
    }


    /**
     * Deals cards to all the robots in the robotList
     */
    public void dealCards(TiledMapStage stage){
        for (Robot r : robots){
            r.dealNewCards(stage);
        }
    }

    /**
     * Method for doing the actions in rights order for each of the cards
     * and triggering all the board elements
     */
    public void doPhase(int phaseNum) {
        ArrayList<Integer> cardsFromSlot = new ArrayList<>();
        for (Robot r : robots) {
            ICard card = r.getProgrammedCard(phaseNum - 1);
            if (card != null) {
                cardsFromSlot.add(card.getPoint());
            }
        }
        cardsFromSlot.sort(Collections.reverseOrder());
        for (int i = 0; i < cardsFromSlot.size(); i++) {
            for (Robot r : robots) {
                ICard card = r.getProgrammedCard(phaseNum - 1);
                if (card == null) {
                    continue;
                }
                if (cardsFromSlot.get(i) == card.getPoint()) {
                    card.doAction(r);
                }
            }
        }
        for (Robot r : robots) {
            Conveyor conveyor = Conveyor.extractConveyorFromCell(r.getPos());
            if (conveyor != null) {
                conveyor.doAction(r);
            }
        }
        for (Robot r : robots) {
            ArrayList<IBoardElement> contents = map.getCellList().getCell(r.getPos()).getInventory().getElements();
            for (IBoardElement elem : contents) {
                if (elem instanceof Conveyor) {
                    continue;
                } else if (elem instanceof Laser) {
                    continue;
                } else if (elem != null) {
                    elem.doAction(r);
                }
            }
        }
    }
}

