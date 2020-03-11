package inf112.app.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.objects.Player;

public interface ICard {

    void doAction(Player player);


    ICard copyOf();

    TiledMapTileLayer.Cell getCardTile();
}
