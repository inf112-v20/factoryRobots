package inf112.app.game;

import com.badlogic.gdx.Game;
import inf112.app.objects.Position;
import inf112.app.objects.Robot;

import java.util.ArrayList;

public class Rounds {

    private final int standardCardAmount = 9;
    private final ArrayList<Robot> robots = null;

    public Rounds(){
        this.robots = GameStatus.getRobots();
    }
    public void putBackPlayers(){

    }
}
