package inf112.app.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.objects.Robot;

public interface ICard {

    void doAction(Robot robot);

    ICard copyOf();

    TiledMapTileLayer.Cell getCardTile();
}
