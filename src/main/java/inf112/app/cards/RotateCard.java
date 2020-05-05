package inf112.app.cards;

import com.badlogic.gdx.graphics.Texture;
import inf112.app.map.Direction.Rotation;
import inf112.app.objects.Robot;

/**
 * this class represents the gameaction to rotate the robot
 */
public class RotateCard extends CardStatus {
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
     * @param robot
     */
    public void doAction(Robot robot){
        if (uTurn){
            robot.turn(rotation);
            robot.turn(rotation);
        } else {
            robot.turn(rotation);
        }
    }

    public Rotation getRotation() {
        return rotation;
    }

    public boolean isUTurn() {
        return uTurn;
    }

    @Override
    public String toString() {
        return "RotateCard " + rotation +
                "uTurn: " + uTurn;
    }
}
