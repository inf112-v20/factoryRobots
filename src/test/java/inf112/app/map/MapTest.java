package inf112.app.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.app.GdxTestRunner;
import inf112.app.objects.Direction;
import inf112.app.objects.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class MapTest {
    Map objectMap;
    TiledMap map;

    /**
     *
     */
    @Before
    public void setUp() throws Exception {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("assets/testMap.tmx");
        objectMap = new Map("testMap");
    }

    /**
     *
     */
    @Test
    public void getMapSizeX() {
        assertEquals(this.objectMap.getMapSizeX(),15);
    }

    /**
     *
     */
    @Test
    public void getMapSizeY() {
        assertEquals(this.objectMap.getMapSizeY(),15);
    }

    /**
     *
     */
    @Test
    public void getMap() {
        assertEquals(this.objectMap.getMap(), this.map);
    }

    /**
     *
     */
    @Test
    public void getLayer() {
        TiledMapTileLayer boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");
        TiledMapTileLayer holeLayer = (TiledMapTileLayer) map.getLayers().get("Hole");
        TiledMapTileLayer flagLayer = (TiledMapTileLayer) map.getLayers().get("Flag");
        TiledMapTileLayer playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");
        TiledMapTileLayer gearLayer = (TiledMapTileLayer) map.getLayers().get("Gear");
        TiledMapTileLayer wallLayer = (TiledMapTileLayer) map.getLayers().get("Wall");
        TiledMapTileLayer conveyorLayer = (TiledMapTileLayer) map.getLayers().get("Conveyor");
        TiledMapTileLayer laserLayer = (TiledMapTileLayer) map.getLayers().get("Laser");
        TiledMapTileLayer utilityLayer = (TiledMapTileLayer) map.getLayers().get("Utility");

        assertEquals("Failure - hole layer is not the same",
                this.objectMap.getLayer("hole").getName(), holeLayer.getName());
        assertEquals("Failure - flag layer is not the same",
                this.objectMap.getLayer("flag").getName(), flagLayer.getName());
        assertEquals("Failure - player layer is not the same",
                this.objectMap.getLayer("player").getName(), playerLayer.getName());
        assertEquals("Failure - gear layer is not the same",
                this.objectMap.getLayer("gear").getName(), gearLayer.getName());
        assertEquals("Failure - wall layer is not the same",
                this.objectMap.getLayer("wall").getName(), wallLayer.getName());
        assertEquals("Failure - conveyor layer is not the same",
                this.objectMap.getLayer("conveyor").getName(), conveyorLayer.getName());
        assertEquals("Failure - laser layer is not the same",
                this.objectMap.getLayer("laser").getName(), laserLayer.getName());
        assertEquals("Failure - utility layer is not the same",
                this.objectMap.getLayer("utility").getName(), utilityLayer.getName());
    }

    /**
     *
     */
    @Test
    public void validMove() {
        assertTrue("Failure - X=1, Y=1 should be a valid move",
                this.objectMap.validMove(new Position(1,1,
                new Direction(Direction.DirEnum.NORTH))));

        assertFalse("Failure - X=16, Y=16 should be a invalid move",
                this.objectMap.validMove(new Position(16,16,
                new Direction(Direction.DirEnum.NORTH))));
    }
}