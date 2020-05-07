package inf112.app.cards;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.objects.Robot;

/**
 * Interface that holds all the requirements the
 * different types of cards in the game need to satisfy
 */
public interface ICard {
    /**
     * Method that triggers the cards action on a certain robot
     * @param robot The robot the action should be applied to
     */
    void doAction(Robot robot);

    /**
     * @return The cards texture as a Tile
     */
    TiledMapTileLayer.Cell getCardTile();

    /**
     * @return The priority number of the cards
     */
    int getPoint();

}
