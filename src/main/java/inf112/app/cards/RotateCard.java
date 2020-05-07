package inf112.app.cards;

import com.badlogic.gdx.graphics.Texture;
import inf112.app.map.Direction.Rotation;
import inf112.app.objects.Robot;

/**
 * Class for representing the rotation action cards
 */
public class RotateCard extends CardStatus {
    private Rotation rotation;
    private boolean uTurn;

    /**
     * Constructor for rotateLeft, rotateRight cards
     * @param points Priority number of the card
     * @param r Rotation direction of the card
     * @param texture The cards blank graphical texture
     */
    public RotateCard(int points, Rotation r, Texture texture){
        super(points, texture);
        this.rotation = r;
        this.uTurn = false;
    }

    /**
     * Constructor for U Turn card
     * @param points Priority number of the card
     * @param uTurn Boolean designating if this is a uTurn card, true for u-turn
     *              false for normal rotate card
     */
    public RotateCard(int points, boolean uTurn, Texture texture){
        super(points, texture);
        this.uTurn = uTurn;
        rotation = Rotation.RIGHT;
    }


    /**
     * Method which rotates or turns the robot around
     * Checks if the card is a u-turn card,
     * and moves the robot in the appropriate direction
     * @param robot The robot which the action should be applied to
     */
    public void doAction(Robot robot){
        robot.turn(rotation);
        if (uTurn) {
            robot.turn(rotation);
        }
    }

    /**
     * @return Rotation direction of the card
     */
    public Rotation getRotation() {
        return rotation;
    }

    /**
     * @return True if the card is a u-turn card, false if not
     */
    public boolean isUTurn() {
        return uTurn;
    }

    @Override
    public String toString() {
        return "RotateCard " + rotation +
                "uTurn: " + uTurn;
    }
}
