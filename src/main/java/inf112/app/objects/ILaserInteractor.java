package inf112.app.objects;

import inf112.app.map.Position;

public interface ILaserInteractor {
    Position getPos();

    void fireLaser();
}
