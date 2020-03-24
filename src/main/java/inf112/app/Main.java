package inf112.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowListener;
import inf112.app.game.RoboRally;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        RoboRally game = new RoboRally();
        config.setTitle("RoboRaly");
        config.setWindowedMode(1000,1000);
        config.setWindowListener(new Lwjgl3WindowListener() {
            @Override
            public void created(Lwjgl3Window lwjgl3Window) {

            }

            /**
             * @param icon True if window is iconified, false if it leaves the iconified state
             */
            @Override
            public void iconified(boolean icon) {

            }

            @Override
            public void maximized(boolean b) {

            }

            @Override
            public void focusLost() {
                // the window lost focus, pause the game
                System.out.println("Lost Focus -> Pause game");
                // Cancel pause debugger is attached
                if (!(java.lang.management.ManagementFactory.getRuntimeMXBean()
                        .getInputArguments().toString().contains("jdwp"))){
                    game.pause();
                }
            }

            @Override
            public void focusGained() {
                // the window received input focus, unpause the game
                System.out.println("Gained Focus -> Start Game");
                // Cancel resume debugger is attached
                if (!(java.lang.management.ManagementFactory.getRuntimeMXBean()
                        .getInputArguments().toString().contains("jdwp"))){
                    game.resume();
                }
            }

            /**
             * Could potentially show a dialog box here
             * @return
             */
            @Override
            public boolean closeRequested() {
                return true;
            }

            @Override
            public void filesDropped(String[] strings) {

            }

            @Override
            public void refreshRequested() {

            }
        });

        new Lwjgl3Application(game, config);
    }
}