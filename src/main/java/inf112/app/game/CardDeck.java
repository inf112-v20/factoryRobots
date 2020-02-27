package inf112.app.game;

import inf112.app.objects.Direction;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * This class creates and holds an entire deck of cards.
 */
public class CardDeck {
    private ArrayList<ICard> deck;

    public CardDeck(){
        reset();
        shuffle();
    }

    /**
     *
     * Resets the deck and 'unshuffles' them
     */


    public void reset() {
        deck = new ArrayList<>();

        //MOVE_ONE Cards:
        int point = 490;
        for (int i = 1; i <= 18; i++) {
            deck.add(new MoveCard(point,1));
            point = point + 10;

        }

        //MOVE_TWO Cards:
        point = 670;
        for (int i = 1; i <= 12; i++) {
            deck.add(new MoveCard(point,2));
            point = point + 10;

        }

        //MOVE_THREE Cards
        point = 790;
        for (int i = 1; i <= 6; i++) {
            deck.add(new MoveCard(point, 3));
            point = point + 10;

        }

        //BACKUP Cards
        point = 430;
        for (int i = 1; i <= 6; i++) {
            deck.add(new MoveCard(point, true));
            point = point + 10;

        }

        //ROTATE_LEFT Cards
        point = 70;
        for (int i = 1; i <= 18; i++) {
            deck.add(new RotateCard(point, Direction.Rotation.LEFT));
            point = point + 20;

        }

        //ROTATE_RIGHT Cards
        point = 70;
        for (int i = 1; i <= 18; i++) {
            deck.add(new RotateCard(point, Direction.Rotation.RIGHT));
            point = point + 20;
        }

        //U_TURN Cards
        point = 10;
        for (int i = 1; i <= 6; i++) {
            deck.add(new RotateCard(point, true));
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

    /**
     * Adds card back to the deck
     * @param card A single card that is to be added back to the deck
     */
    public void addCard(ICard card){
        deck.add(card);
    }

    /**
     * Get cards
     */

    /**
     * Return the desired amount of cards
     * Prevents retrieving doubles
     * @param amount The desired amount of cards
     * @return The amount of cards
     */
    public ArrayList<ICard> getCards(int amount) {
        ArrayList<ICard> playerDeck = new ArrayList<>();
        for (int i = 0; i < amount; i ++) {
            playerDeck.add(deck.get(i));
            deck.remove(i);
        }
        return playerDeck;
    }

    public ICard getCard(){
        if(deck.isEmpty()){
            System.out.println("Deck is empty");
            return null;
        }
        ICard card = deck.get(0);
        deck.remove(0);
        return card;
    }

    public boolean isEmpty(){
        return deck.isEmpty();
    }
}