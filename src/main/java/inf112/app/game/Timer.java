package inf112.app.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kotcrab.vis.ui.widget.VisLabel;

import java.text.DecimalFormat;

/**
 * Class for a timer that counts down from a set time based on the {@link Gdx#graphics} delta time,
 * i.e. the time since the last render call in the current instance of {@link com.badlogic.gdx.Screen}
 */
public class Timer {

    private float count;
    private BitmapFont font;
    private DecimalFormat df = new DecimalFormat("#");
    private VisLabel label;
    private boolean warning;
    public boolean done;
    private boolean disabled;
    private String str;

    /**
     * Basic constructor, initialises the necessary field
     * @param count The initial time it should count down from
     * @param label The label which the current time should be printed to
     */
    public Timer(float count, VisLabel label){
        this.count = count;
        this.label = label;
        font = new BitmapFont();
        font.setColor(Color.GREEN);
        font.getData().setScale(3f);
        warning = false;
        disabled = false;
    }

    /**
     * Method which subracts the time since the last call and unless the timer
     * is disabled (dictated by {@link #disabled} boolean) or the timer is {@link #done} the method will draw
     * the updated time to the label
     */
    public void drawTime() {
        count -= Gdx.graphics.getDeltaTime();
        if(disabled){return;}
        str = "Programming locks in: " + df.format(count) + "\t sec";
        if(count<0f){
            done = true;
            str = "";
        }
        label.setText(str);
        if(count<10f && !warning){
            label.setColor(Color.RED);
            warning = true;
        }

    }

    /**
     * Initiates the timer to count down from 30 seconds, resets all the necessary fields
     */
    public void start(){
        Gdx.graphics.getDeltaTime();
        count = 30;
        label.setColor(Color.WHITE);
        warning = false;
        done = false;
    }

    /**
     * Sets the {@link #disabled} boolean to true
     */
    public void disable() {
        disabled = true;
    }
}
