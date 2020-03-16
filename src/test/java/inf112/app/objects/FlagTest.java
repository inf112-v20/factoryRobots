package inf112.app.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.GdxTestRunner;
import inf112.app.map.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class FlagTest {
    private Player player;
    private int num;
    private TiledMap map;


    @Before
    public void setUp() throws Exception {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("assets/testMap.tmx");
        Map.setInstance("testMap");
    }

    /**
     * Checking if player can get a new getVisitedFlag higher than 1 from previously visited flag.
     * Also if getVisitedFlag works.
     */
    @Test
    public void doAction() {
        Flag flag1 = new Flag(1);
        Flag flag2 = new Flag(2);
        Flag flag3 = new Flag(3);
        Flag flag4 = new Flag(4);

        player = new Player(2, 2);

        flag2.doAction(player);
        assertEquals("Checking for no flag", player.getCharacter().getVisitedFlag(), null);
        flag1.doAction(player);
        assertEquals("Checking if player registers new visited flag 1", player.getCharacter().getVisitedFlag(), flag1);
        flag2.doAction(player);
        assertEquals("Checking if player registers new visited flag 2", player.getCharacter().getVisitedFlag(), flag2);
        flag3.doAction(player);
        assertEquals("Checking if player registers new visited flag 3", player.getCharacter().getVisitedFlag(), flag3);
        flag4.doAction(player);
        assertEquals("Checking if player registers new visited flag 4", player.getCharacter().getVisitedFlag(), flag4);
    }
}