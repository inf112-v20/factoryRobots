package inf112.app.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public interface IActor {
    TiledMapTileLayer.Cell getCell();

    void clickAction();
}
