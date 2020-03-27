package inf112.app.cards;

import inf112.app.GdxTestRunner;
import inf112.app.map.Map;
import inf112.app.map.Direction.Rotation;
import inf112.app.game.Player;
import inf112.app.map.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class RotateCardTest {
    private RotateCard leftRotate;
    private RotateCard rightRotate;
    private RotateCard uTurn;
    private Player player;

    @Before
    public void setUp() throws Exception {
        leftRotate = new RotateCard(150, Rotation.LEFT,null);
        rightRotate = new RotateCard(150, Rotation.RIGHT,null);
        uTurn = new RotateCard(150,true,null);
        Map.setInstance("testMap");
        player = new Player(2,2);
    }

    @Test
    public void doActionRotateLeftTest() {
        Position oldPos = player.getCharacter().getPos().copyOf();
        oldPos.getDirection().turn(Rotation.LEFT);
        leftRotate.doAction(player.getCharacter());
        assertEquals("Failure, positions should be the same ",oldPos,player.getCharacter().getPos());
    }


    @Test
    public void doActionRotateRightTest() {
        Position oldPos = player.getCharacter().getPos().copyOf();
        oldPos.getDirection().turn(Rotation.RIGHT);
        rightRotate.doAction(player.getCharacter());
        assertEquals("Failure, positions should be the same ",oldPos,player.getCharacter().getPos());
    }

    @Test
    public void doActionUTurnTest() {
        Position oldPos = player.getCharacter().getPos().copyOf();
        oldPos.getDirection().turn(Rotation.RIGHT);
        oldPos.getDirection().turn(Rotation.RIGHT);
        uTurn.doAction(player.getCharacter());
        assertEquals("Failure, positions should be the same ",oldPos,player.getCharacter().getPos());
    }
}