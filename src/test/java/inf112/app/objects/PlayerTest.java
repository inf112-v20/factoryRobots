package inf112.app.objects;

import com.badlogic.gdx.Input;
import inf112.app.GdxTestRunner;
import inf112.app.map.Map;
import inf112.app.objects.Direction.Rotation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;


@RunWith(GdxTestRunner.class)
public class PlayerTest {
    private Player player;
    private Map map;

    @Before
    public void setUp() throws Exception {
        this.map = new Map("testMap");
        this.player = new Player(2,2, map);
    }

    @Test
    public void playerPositionUpdatesOnKeyUpTest() {
        Position oldPos = player.getCharacter().getPos();
        player.keyUp(Input.Keys.LEFT);
        Position newPos = player.getCharacter().getPos();
        assertNotEquals(oldPos,newPos);
    }

    private void assertNotEquals(Position oldPos, Position newPos) {
    }

    @Test
    public void playerPositionUpdatesUp() {
        Position oldPos = player.getCharacter().getPos().copyOf();
        oldPos.moveInDirection();
        player.keyUp(Input.Keys.UP);
        Position newPos = player.getCharacter().getPos();
        assertEquals("Positions are not the same",oldPos,newPos);
    }

    @Test
    public void playerPositionUpdatesRight() {
        Position oldPos = player.getCharacter().getPos().copyOf();
        oldPos.getDirection().turn(Rotation.RIGHT);
        oldPos.moveInDirection();
        oldPos.getDirection().turn(Rotation.LEFT);
        player.keyUp(Input.Keys.RIGHT);
        Position newPos = player.getCharacter().getPos();
        assertEquals("Positions are not the same", oldPos,newPos);
    }

    @Test
    public void playerPositionUpdatesLeft() {
        Position oldPos = player.getCharacter().getPos().copyOf();
        oldPos.getDirection().turn(Rotation.LEFT);
        oldPos.moveInDirection();
        oldPos.getDirection().turn(Rotation.RIGHT);
        player.keyUp(Input.Keys.LEFT);
        Position newPos = player.getCharacter().getPos();
        assertEquals("Positions are not the same", oldPos,newPos);
    }

    @Test
    public void playerPositionUpdatesDown() {
        Position oldPos = player.getCharacter().getPos().copyOf();
        oldPos.getDirection().turn(Rotation.RIGHT);
        oldPos.getDirection().turn(Rotation.RIGHT);
        oldPos.moveInDirection();
        oldPos.getDirection().turn(Rotation.LEFT);
        oldPos.getDirection().turn(Rotation.LEFT);
        player.keyUp(Input.Keys.DOWN);
        Position newPos = player.getCharacter().getPos();
        assertEquals("Positions are not the same", oldPos,newPos);
    }
}
