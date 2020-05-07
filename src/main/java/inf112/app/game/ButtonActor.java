package inf112.app.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Abstract class for managing the click listeners of the buttons in the games UI,
 */
public abstract class ButtonActor extends Actor {
    /**
     * @return The cell in the tiledmap grid holding the button
     */
    abstract TiledMapTileLayer.Cell getCell();

    /**
     * Method for dictating what should take place when a user clicks on the cell
     */
    abstract void clickAction();

    /**
     * Method for setting the graphical content of the cell in the
     * tiledmap grid holding the button
     * @param cell The graphical texture to be applied
     */
    public abstract void setCell(TiledMapTileLayer.Cell cell);

}
