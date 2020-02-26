package inf112.app.objects;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectFactoryTest {
    TiledMapTile tile;
    @Before
    public void setUp() throws Exception {
        TmxMapLoader loader = new TmxMapLoader();
        ObjectFactory factory = new ObjectFactory();
    }

    @Test
    public void generateObject() {
    }
}