package inf112.app.game;

import com.badlogic.gdx.graphics.Texture;
import inf112.app.objects.Direction;
import inf112.app.objects.Robot;

/**
 * this class represents the gameaction to change the players position
 * on the board.
 */
public class MoveCard extends CardStatus implements ICard  {
    private int steps;
    private boolean backUp;

    /**
     * constructor for move cards
     * @param points
     * @param steps
     */
    public MoveCard(int points, int steps, Texture texture){
        super(points,texture);
        this.steps = steps;
        this.backUp = false;

    }

    /**
     * constructor for backup card
     * @param points
     * @param backUp
     */
    public MoveCard(int points, boolean backUp, Texture texture) {
        super(points, texture);
        this.backUp = backUp;
        this.steps = 1;
    }

    /**
     * Method to change a players position on the board
     *checks if the card is backup, and takes in steps for how many moves
     * @param robot
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

    @Override
    public ICard copyOf() {
        if(!this.backUp) {
            return new MoveCard(this.getPoint(), this.steps, this.getTexture());
        } else {
            return new MoveCard(this.getPoint(), this.backUp, this.getTexture());
        }
    }

    public int getSteps() {
        return steps;
    }

    public boolean isBackUp() {
        return backUp;
    }
}
