package inf112.app.util;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.GdxTestRunner;
import inf112.app.map.Map;
import inf112.app.objects.*;
import inf112.app.util.ObjectFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ObjectFactoryTest {
    private TiledMapTile tile;
    private TmxMapLoader loader;
    private Map map;
    private TiledMapTileLayer wallLayer;
    private ObjectFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new ObjectFactory();
        Map.setInstance("testMap");
        map = Map.getInstance();
    }

    @Test
    public void generateWallTest() {
        TiledMapTile tile = map.getLayer("wall").getCell(3,7).getTile();
        IBoardElement actual = factory.generateObject(tile,3,7);
        assertTrue("Failure, object should be instance of Wall", actual instanceof Wall);
    }

    @Test
    public void generateFlagTest() {
        TiledMapTile tile = map.getLayer("flag").getCell(11,11).getTile();
        IBoardElement actual = factory.generateObject(tile,11,11);
        assertTrue("Failure, object should be instance of Flag",actual instanceof Flag);
    }

    @Test
    public void generateConveyorTest() {
        TiledMapTile tile = map.getLayer("conveyor").getCell(7,4).getTile();
        IBoardElement actual = factory.generateObject(tile,7,4);
        assertTrue("Failure, object should be instance of Conveyor",actual instanceof Conveyor);
    }

    @Test
    public void generateRepairStationTest() {
        TiledMapTile tile = map.getLayer("utility").getCell(4,10).getTile();
        IBoardElement actual = factory.generateObject(tile,4,10);
        assertTrue("Failure, object should be instance of RepairStation",actual instanceof RepairStation);
    }


}