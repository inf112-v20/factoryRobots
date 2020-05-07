package inf112.app.cards;

import com.badlogic.gdx.graphics.Texture;
import inf112.app.map.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 *
 * Class for holding a deck of cards as well as the used a stack for the cards that have been used.
 */
public class CardDeck {
    private Stack<ICard> deck;
    private ArrayList<ICard> usedStack;
    private Texture[] textures;

    /**
     * Normal constructor, loads the card textures, assigns appropriate priority points and shuffles the deck.
     */
    public CardDeck(){
        loadCardTextures();
        initialize();
        shuffle();
    }

    /**
     * Constructor used by automated tests
     * should never be used by actual game as it doesn't load the textures
     * @param testing extra parameter for distinguishing the constructors
     *                can be true or false
     */
    public CardDeck(boolean testing){
        textures = new Texture[7];
        for(int i = 0; i<7; i++){
            textures[i] = null;
        }
        initialize();

    }

    /**
     * Loads the card textures, assigns priority points
     * and adds the cards to the deck
     */
    public void initialize() {
        deck = new Stack<>();
        usedStack = new ArrayList<>();

        //MOVE_ONE Cards:
        int point = 520;
        for (int i = 1; i <= 18; i++) {
            deck.add(new MoveCard(point,1, textures[0]));
            point = point + 10;

        }

        //MOVE_TWO Cards:
        point = 710;
        for (int i = 1; i <= 12; i++) {
            deck.add(new MoveCard(point,2, textures[1]));
            point = point + 10;

        }

        //MOVE_THREE Cards
        point = 840;
        for (int i = 1; i <= 6; i++) {
            deck.add(new MoveCard(point, 3, textures[2]));
            point = point + 10;

        }

        //BACKUP Cards
        point = 450;
        for (int i = 1; i <= 6; i++) {
            deck.add(new MoveCard(point, true, textures[6]));
            point = point + 10;

        }

        //ROTATE_LEFT Cards
        point = 80;
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
     * Readds all the used cards to the deck and shuffles
     */
    public void reset(){
        for(ICard card : usedStack){
            deck.push(card);
        }
        shuffle();
    }

    /**
     * Shuffles the card deck
     */

    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * Return the desired amount of cards
     * @param amount The desired amount of cards
     * @return A list containing the correct amount of cards
     */
    public ArrayList<ICard> getCards(int amount) {
        ArrayList<ICard> playerDeck = new ArrayList<>();
        for (int i = 0; i < amount; i ++) {
            ICard card = deck.pop();
            playerDeck.add(card);
            usedStack.add(card);
        }
        return playerDeck;
    }

    /**
     * Method for drawing the top card from the deck
     * @return The top card of the deck
     */
    public ICard getCard(){
        if(deck.isEmpty()){
            reset();
        }
        ICard card = deck.pop();
        usedStack.add(card);
        return card;
    }

    /**
     * Method for drawing a specific card from the deck,
     * using the priority number of the card as a key
     * @param priority Priority number of the card to be extracted from the deck
     * @return The card with the correct priority number or null if the card doesn't exist
     */
    public ICard getCard(int priority){
        ICard c = null;
        for(ICard card : deck){
            if(card.getPoint() == priority){
                deck.remove(card);
                usedStack.add(card);
                return card;
            }
        }
        if(c == null){
            reset();
            c = getCard(priority);
        }
        return c;
    }

    /**
     * @return The amount of cards in the deck, not including the used cards
     */
    public int getSize(){
        return deck.size();
    }

    /**
     * @return true if the deck is out of cards, false if not
     */
    public boolean isEmpty(){
        return deck.isEmpty();
    }

    /**
     * Used to load the raw card textures from filesystem
     */
    private void loadCardTextures(){
        textures = new Texture[7];
        String[] cardNames = new String[]{"move_1","move_2","move_3","rotate_left","rotate_right","u-turn","back_up"};
        for(int i = 0; i<7; i++){
            String path = "assets/Card/" + cardNames[i] + ".png";
            textures[i] = new Texture(path);
        }
    }
}