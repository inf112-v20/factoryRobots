package inf112.app.objects;
import inf112.app.objects.Direction.Rotation;

public class Cog implements IBoardElement {
    private Rotation rot;

    public Cog(Rotation rot){
        this.rot = rot;
    }

    public Rotation getRot() {
        return rot;
    }
}
