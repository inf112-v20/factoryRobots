package inf112.app.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kotcrab.vis.ui.widget.VisLabel;

import java.text.DecimalFormat;

public class Timer {

    private float count;
    private BitmapFont font;
    private DecimalFormat df = new DecimalFormat("#");
    private VisLabel label;
    private boolean warning;
    public boolean done;
    private boolean disabled;
    private String str;

    public Timer(float count, VisLabel label){
        this.count = count;
        this.label = label;
        font = new BitmapFont();
        font.setColor(Color.GREEN);
        font.getData().setScale(3f);
        warning = false;
        disabled = false;
    }

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

    public void start(){
        Gdx.graphics.getDeltaTime();
        count = 30;
        label.setColor(Color.WHITE);
        warning = false;
        done = false;
    }

    public void disable() {
        disabled = true;
    }
}
