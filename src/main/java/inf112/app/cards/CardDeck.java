package inf112.app.cards;

import com.badlogic.gdx.graphics.Texture;
import inf112.app.map.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 *
 * This class creates and holds an entire deck of cards.
 */
public class CardDeck {
    private Stack<ICard> deck;
    private Texture[] textures;

    public CardDeck(){
        loadCardTextures();
        reset();
        shuffle();
    }

    /**
     * Constructor used by automated tests
     * should never be used by actual game
     * @param testing extra parameter for distinguishing the constructors
     *                can be true or false
     */
    public CardDeck(boolean testing){
        textures = new Texture[7];
        for(int i = 0; i<7; i++){
            textures[i] = null;
        }
        reset();

    }

    /**
     *
     * Resets the deck and 'unshuffles' them
     */
    public void reset() {
        deck = new Stack<>();

        //MOVE_ONE Cards:
        int point = 490;
        for (int i = 1; i <= 18; i++) {
            deck.add(new MoveCard(point,1, textures[0]));
            point = point + 10;

        }

        //MOVE_TWO Cards:
        point = 670;
        for (int i = 1; i <= 12; i++) {
            deck.add(new MoveCard(point,2, textures[1]));
            point = point + 10;

        }

        //MOVE_THREE Cards
        point = 790;
        for (int i = 1; i <= 6; i++) {
            deck.add(new MoveCard(point, 3, textures[2]));
            point = point + 10;

        }

        //BACKUP Cards
        point = 430;
        for (int i = 1; i <= 6; i++) {
            deck.add(new MoveCard(point, true, textures[6]));
            point = point + 10;

        }

        //ROTATE_LEFT Cards
        point = 70;
        for (int i = 1; i <= 18; i++) {
            deck.add(new RotateCard(point, Direction.Rotation.LEFT, textures[3]));
            point = point + 20;

        }

        //ROTATE_RIGHT Cards
        point = 70;
        for (int i = 1; i <= 18; i++) {
            deck.add(new RotateCard(point, Direction.Rotation.RIGHT, textures[4]));
            point = point + 20;
        }

        //U_TURN Cards
        point = 10;
        for (int i = 1; i <= 6; i++) {
            deck.add(new RotateCard(point, true, textures[5]));
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
        deck.push(card);
    }


    /**
     * Return the desired amount of cards
     * Prevents retrieving doubles
     * @param amount The desired amount of cards
     * @return The amount of cards
     */
    public ArrayList<ICard> getCards(int amount) {
        ArrayList<ICard> playerDeck = new ArrayList<>();
        for (int i = 0; i < amount; i ++) {
            playerDeck.add(deck.pop());
        }
        return playerDeck;
    }

    public ICard getCard(){
        if(deck.isEmpty()){
            System.out.println("Deck is empty");
            return null;
        }
        return deck.pop();
    }

    public ICard getCard(int priority){
        for(ICard card : deck){
            if(card.getPoint() == priority){
                deck.remove(card);
                return card;
            }
        }
        return null;
    }

    public boolean isEmpty(){
        return deck.isEmpty();
    }

    private void loadCardTextures(){
        textures = new Texture[7];
        String[] cardNames = new String[]{"move_1","move_2","move_3","rotate_left","rotate_right","u-turn","back_up"};
        for(int i = 0; i<7; i++){
            String path = "assets/Card/" + cardNames[i] + ".png";
            textures[i] = new Texture(path);
        }
    }
}