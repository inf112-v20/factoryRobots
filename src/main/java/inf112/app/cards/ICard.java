package inf112.app.cards;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.objects.Robot;

public interface ICard {

    void doAction(Robot robot);

    TiledMapTileLayer.Cell getCardTile();

    int getPoint();

}
