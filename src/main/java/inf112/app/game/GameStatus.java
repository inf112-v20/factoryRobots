package inf112.app.game;

import inf112.app.cards.CardDeck;
import inf112.app.map.Map;
import inf112.app.map.Position;
import inf112.app.objects.Robot;

import java.util.ArrayList;

public class GameStatus {
    private final Map board;
    public final ArrayList<Robot> robots;
    private CardDeck deck;
    private int amountOfPlayers;
    private Position startPosition;

    public GameStatus(ArrayList<Robot> robots, Map map){
        this.robots = robots;
        this.board = map;
        resetDeck();
        getRobots(amountOfPlayers);
    }


    private void resetDeck() {
        if (deck == null){
            deck = new CardDeck();
        }else{
            deck.reset();
        }
    }

    public void startPositions(){
        for (int i = 0; i < robots.size(); i++) {
            startPosition = new Position(2*(i+1), 2);
        }
    }

    /**
     * returns all robots that are in the game
     * @return
     */
    public ArrayList<Robot> getRobots(int amountOfPlayers) {
        ArrayList<Robot> robots = new ArrayList<>();
        for (int i = 0; i < amountOfPlayers; i++) {
            robots.add(new Robot(startPosition, "Robotnr" + i));
        }
        return robots;
    }


}
