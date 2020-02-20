package inf112.app.game;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * This class creates and holds an entire deck of cards.
 */
public class CardDeck {

    /**
     *
     * Resets the deck and 'unshuffles' them
     */

    ArrayList<> deck = new ArrayList<>();
    public void reset() {

        //MOVE_ONE Cards:
        int point = 490;
        for (int i = 1; i <= 18; i++) {
            deck.add(new MoveOneCard(point));
            point = point + 10;

        }

        //MOVE_TWO Cards:
        point = 670;
        for (int i = 1; i <= 12; i++) {
            deck.add(new MoveTwoCard(point));
            point = point + 10;

        }

        //MOVE_THREE Cards
        point = 790;
        for (int i = 1; i <= 6; i++) {
            deck.add(new MoveThreeCard(point));
            point = point + 10;

        }

        //BACKUP Cards
        point = 430;
        for (int i = 1; i <= 6; i++) {
            deck.add(new BackUpCard(point));
            point = point + 10;

        }

        //ROTATE_LEFT Cards
        point = 70;
        for (int i = 1; i <= 18; i++) {
            deck.add(new RotateLeftCard(point));
            point = point + 20;

        }

        //ROTATE_RIGHT Cards
        point = 70;
        for (int i = 1; i <= 18; i++) {
            deck.add(new RotateRightCard(point));
            point = point + 20;
        }

        //U_TURN Cards
        point = 10;
        for (int i = 1; i <= 6; i++) {
            deck.add(new U_TurnCard(point));
            point = point + 10;
        }
    }

    /**
     *
     * Shuffles the deck, use this before getting cards
     */

    public void shuffle() {
        Collections.shuffle(deck);
    }

}