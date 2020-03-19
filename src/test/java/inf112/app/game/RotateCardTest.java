package inf112.app.game;

import inf112.app.GdxTestRunner;
import inf112.app.map.Map;
import inf112.app.objects.Direction.Rotation;
import inf112.app.objects.Player;
import inf112.app.objects.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class RotateCardTest {
    RotateCard leftRotate;
    RotateCard rightRotate;
    RotateCard uTurn;
    Player player;

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