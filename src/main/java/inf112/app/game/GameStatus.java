package inf112.app.game;

import inf112.app.map.Map;
import inf112.app.objects.Robot;

import java.util.ArrayList;

public class GameStatus {
    private final Map board;
    private final ArrayList<Robot> robots;
    private CardDeck deck;

    public GameStatus(ArrayList<Robot> robots, Map map){
        this.robots = robots;
        this.board = map;
        resetDeck();
        setStartPosition();
    }

    private void setStartPosition() {
    }

    private void resetDeck() {
        if (deck == null){
            deck = new CardDeck();
        }else{
            deck.reset();
        }
    }


}
