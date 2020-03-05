package inf112.app.objects;
import inf112.app.map.Map;
import inf112.app.objects.Direction.Rotation;
import inf112.app.objects.Player;

import java.util.NoSuchElementException;


/**
 * this class makes the object cog turn robots direction on it
 */
public class Cog implements IBoardElement {
    private Rotation rotation;

    /**
     * constructor to rotate direction of robot
     * @param r
     */
    public Cog(Rotation r){
        this.rotation = r;
    }

    @Override
    public void doAction(Player player) {
        player.getCharacter().turn(rotation);
    }
}