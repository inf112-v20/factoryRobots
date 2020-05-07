package inf112.app.cards;

import com.badlogic.gdx.graphics.Texture;
import inf112.app.map.Direction;
import inf112.app.objects.Robot;

/**
 * Class for representing the move- and back-up action cards
 */
public class MoveCard extends CardStatus  {
    private int steps;
    private boolean backUp;

    /**
     * Constructor for ordinary move cards
     * @param points Priority number of the card
     * @param steps How many movement steps the card holds
     * @param texture The cards blank graphical texture
     */
    public MoveCard(int points, int steps, Texture texture){
        super(points,texture);
        this.steps = steps;
        this.backUp = false;

    }

    /**
     * Constructor for the backup card
     * @param points Priority number of the card
     * @param backUp Boolean specifying if the card is a backup card
     * @param texture The cards blank graphical texture
     */
    public MoveCard(int points, boolean backUp, Texture texture) {
        super(points, texture);
        this.backUp = backUp;
        this.steps = 1;
    }

    /**
     * Method to change a players position on the board
     * based on the cards different properties
     * @param robot The robot which the cards action should be applied to
     */
    public void doAction(Robot robot){
        if (backUp){
            robot.turn(Direction.Rotation.LEFT);
            robot.turn(Direction.Rotation.LEFT);
            robot.move(steps);
            robot.turn(Direction.Rotation.LEFT);
            robot.turn(Direction.Rotation.LEFT);
        } else {
            robot.move(steps);
        }
    }

    /**
     * @return How many steps the card will take a robot
     */
    public int getSteps() {
        return steps;
    }

    /**
     * @return True if the card is a backup card, false if normal move card
     */
    public boolean isBackUp() {
        return backUp;
    }

    @Override
    public String toString() {
        return "MoveCard " + steps +
                " backup: " + backUp;
    }
}
