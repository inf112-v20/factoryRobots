package inf112.app.game;

import java.util.Comparator;

/**
 *Comparator for all card classes
 * class to compare the priority points of cards
 */

public class CardCompare implements Comparator<GetSetCard> {

    @Override
    public int compare(GetSetCard card1, GetSetCard card2) {
        return card2.getPoint() - card1.getPoint();
    }
}
