package inf112.app.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import inf112.app.objects.Direction.Rotation;
import inf112.app.objects.Player;

/**
 * this class represents the gameaction to rotate the robot
 */
public class RotateCard extends CardStatus implements ICard {
    private Rotation rotation;
    private boolean uTurn;

    /**
     * constructor for rotateLeft, rotateRight cards
     * @param points
     * @param r
     */
    public RotateCard(int points, Rotation r, Texture texture){
        super(points, texture);
        this.rotation = r;
        this.uTurn = false;
    }

    /**
     * constructor for U Turn card
     * @param points
     * @param uTurn
     */
    public RotateCard(int points, boolean uTurn, Texture texture){
        super(points, texture);
        this.uTurn = uTurn;
        rotation = Rotation.RIGHT;
    }


    /**
     * method to rotate or turn the robot around
     * checks if the card is uturn, and checks the direction of the rotation
     * @param player
     */
    public void doAction(Player player){
        if (uTurn){
            player.getCharacter().turn(rotation);
            player.getCharacter().turn(rotation);
        } else {
            player.getCharacter().turn(rotation);
        }
    }

    @Override
    public ICard copyOf() {
        if(!uTurn){
            return new RotateCard(this.getPoint(), this.rotation, this.getTexture());
        } else {
            return new RotateCard(this.getPoint(), this.uTurn, this.getTexture());
        }
    }

    public Rotation getRotation() {
        return rotation;
    }

    public boolean isUTurn() {
        return uTurn;
    }
}
