package inf112.app.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Class extending {@link ClickListener}, holding a {@link ButtonActor}
 * which it triggers the {@link ButtonActor#clickAction()} method for,
 * but only if there is an actual element in the cell that is clicked
 */
public class TiledMapClickListener extends ClickListener {

    private ButtonActor actor;

    /**
     * Standard constructor takes an actor which it listens for clicks for
     * @param actor Actor which the click-listener should manage
     */
    public TiledMapClickListener(ButtonActor actor) {
        this.actor = actor;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if(actor.getCell() != null){
            actor.clickAction();
        }
    }




}
