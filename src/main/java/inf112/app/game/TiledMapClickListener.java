package inf112.app.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TiledMapClickListener extends ClickListener {

    private ButtonActor actor;

    public TiledMapClickListener(ButtonActor actor) {
        this.actor = actor;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        System.out.println(actor.getCell() + " has been clicked.");
        System.out.println(x + " " + y);
        if(actor.getCell() != null){
            actor.clickAction();
        }
    }




}
