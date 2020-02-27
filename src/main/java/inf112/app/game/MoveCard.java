package inf112.app.game;

import inf112.app.objects.Direction;
import inf112.app.objects.Player;

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
    public MoveCard(int points, int steps){
        super(points);
        this.steps = steps;
        this.backUp = false;
    }

    /**
     * constructor for backup card
     * @param points
     * @param backUp
     */
    public MoveCard(int points, boolean backUp) {
        super(points);
        this.backUp = backUp;
        this.steps = 1;
    }

    /**
     * Method to change a players position on the board
     *checks if the card is backup, and takes in steps for how many moves
     * @param player
     */
    public void doAction(Player player){
        if (backUp){
            player.turn(Direction.Rotation.LEFT);
            player.turn(Direction.Rotation.LEFT);
            player.move(steps);
            player.turn(Direction.Rotation.LEFT);
            player.turn(Direction.Rotation.LEFT);
        } else {
            player.move(steps);
        }
    }

    public int getSteps() {
        return steps;
    }

    public boolean isBackUp() {
        return backUp;
    }
}
