package inf112.app.objects;

import inf112.app.map.Position;

/**
 * An interface for grouping board-elements that possess and interact with lasers
 */
public interface ILaserInteractor {
    /**
     * @return The position of the element
     */
    Position getPos();

    /**
     * Fires the laser which the element possesses
     */
    void fireLaser();
}
