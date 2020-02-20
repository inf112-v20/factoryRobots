package inf112.app.objects;

import java.util.ArrayList;

public class Laser{

    private Position position;

    Laser (int x, int y, Direction) {

    }

    // All players to fire laser in their direction
   private void activateLasers() {

       for (Player p : players) {

           Fire(p, p.Position.getYCoordinate, );
       }
   }
   // Which direction the robot should shoot
   private void Fire (Player player, int playerDirection, int iterationStart) {

        switch (player.getDireciton()) {

                for (int y = playerDirection; y >= iterationStart; y--) {
                    Position firePosition = new Position(player.getPosition().getXCoordinate, y);
                    for (Player nearestRobot : players) {
                        if (player.equals(nearestRobot) && nearestRobot.getPosition().equals(firePosition)) {
                           //hvordan vise at nearestRobot skal ta skade fra laser
                        }
                    }
                }

       }
   }
}
