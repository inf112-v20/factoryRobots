package inf112.app.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kotcrab.vis.ui.widget.VisTable;

public class TableBuilder {
    private static int padding = 3;
    private static int width = 600;
    private static int height = 60;

    /**
     * Build a button table with one column and actors.length rows
     * @param table The table to append actors to
     * @param actors The actors to append to the table
     */
    public static void column(VisTable table, Actor... actors){
        for (Actor actor : actors) {
            table.add(actor).pad(padding).height(height).width(width);
            table.row();
        }
    }

    /**
     * Build a button table with one row and actors.length columns
     * @param table The table to append actors to
     * @param actors The actors to append to the table
     */
    public static void row(VisTable table, Actor... actors){
        int length = width / actors.length;
        for (Actor actor : actors) {
            table.add(actor).pad(padding).height(height).width(length);
        }
        table.row();
    }
}
