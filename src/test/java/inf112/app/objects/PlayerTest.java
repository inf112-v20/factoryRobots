package inf112.app.objects;

import inf112.app.map.Map;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    Player player;
    Map map;

    @Before
    public void setUp() throws Exception {
        map = new Map("testMap");
        player = new Player(2, 2, map);
    }

    @Test
    public void playerMovesTest() {

    }


}