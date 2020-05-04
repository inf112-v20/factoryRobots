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

            /**
             * Not used
             * @param lwjgl3Window The window instance
             */
            @Override
            public void created(Lwjgl3Window lwjgl3Window) {

            }

            /**
             * @param icon True if window is iconified, false if it leaves the iconified state
             */
            @Override
            public void iconified(boolean icon) {

            }

            /**
             * Not used
             * @param b True if maximized, false if not
             */
            @Override
            public void maximized(boolean b) {

            }

            /**
             * The window lost focus, pause the game
             */
            @Override
            public void focusLost() {

            /*    System.out.println("Lost Focus -> Pause game");
                // Cancel pause debugger is attached
                if (!(java.lang.management.ManagementFactory.getRuntimeMXBean()
                        .getInputArguments().toString().contains("jdwp"))){
                    game.pause();
                } */
            }
            /**
             * The window gained focus, resume the game
             */
            @Override
            public void focusGained() {
               /* System.out.println("Gained Focus -> Start Game");
                // Cancel resume debugger is attached
                if (!(java.lang.management.ManagementFactory.getRuntimeMXBean()
                        .getInputArguments().toString().contains("jdwp"))){
                    game.resume();
                } */
            }

            /**
             * Could potentially show a dialog box here
             * @return true
             */
            @Override
            public boolean closeRequested() {
                if(game.isHost){
                    game.shutdownServer();
                }
                return true;
            }

            /**
             * Not used
             * @param strings Files dropped into the window
             */
            @Override
            public void filesDropped(String[] strings) {

            }

            /**
             * Not used
             */
            @Override
            public void refreshRequested() {

            }
        });

        new Lwjgl3Application(game, config);
    }
}