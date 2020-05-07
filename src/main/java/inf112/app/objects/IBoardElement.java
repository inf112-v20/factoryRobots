package inf112.app.objects;

/**
 * Interface for grouping all the elements of the board together
 */
public interface IBoardElement {
    /**
     * All elements on the board must have a doAction method that can be called
     * when a robot is positioned on the element
     * @param robot The robot that is positioned on the element
     */
    void doAction(Robot robot);
}
