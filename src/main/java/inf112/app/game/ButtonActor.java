package inf112.app.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class ButtonActor extends Actor {
    abstract TiledMapTileLayer.Cell getCell();

    abstract void clickAction();

    abstract void setCell(TiledMapTileLayer.Cell cell);

}
