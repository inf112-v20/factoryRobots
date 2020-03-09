package inf112.app.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import inf112.app.game.CardSlot;
import inf112.app.map.Map;
import inf112.app.objects.Direction.Rotation;

import javax.smartcardio.Card;

/**
 * Class for the player which the clients user controls,
 * not to be confused by the player character, which is the {@link Robot} class
 */
public class Player extends InputAdapter {
    private Robot character;
    private Map map;
    private CardSlot[] cardSlots;


    /**
     * The single constructor for Player
     * @param x X-coordinate of the player character
     * @param y Y-coordinate of the player character
     */
    public Player(int x, int y){
        //Initializing input processor
        Gdx.input.setInputProcessor(this);

        this.map = Map.getInstance();
        character = new Robot(new Position(x,y),"player");

        initializeCardSlots();
    }


    public Robot getCharacter(){
        return character;
    }

    /**
     * Changes the players coordinates based on keypress
     * @param keycode Code for key that is being released
     * @return true if key is being released, false if not
     */
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                character.turn(Rotation.LEFT);
                break;
            case Input.Keys.RIGHT:
                character.turn(Rotation.RIGHT);
                break;
            case Input.Keys.UP:
                character.move(1);
                break;
            /*case Input.Keys.DOWN: //temporary
                character.turn(Rotation.LEFT);
                character.turn(Rotation.LEFT);
                character.move(1);
                character.turn(Rotation.RIGHT);
                character.turn(Rotation.RIGHT);
                break; */
            default:
                System.out.println("Unassigned input");
                break;
        }
        return false;
    }

    private void initializeCardSlots(){
        cardSlots = new CardSlot[5];
        for(int i = 0; i<5; i++){
            cardSlots[i] = new CardSlot();
        }
    }
}
