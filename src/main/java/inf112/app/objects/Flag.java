package inf112.app.objects;

import org.lwjgl.Sys;

import java.util.Objects;

public class Flag implements IBoardElement {
    private int num;
    private Robot robot;

    public Flag(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flag)) return false;
        Flag flag = (Flag) o;
        return getNum() == flag.getNum();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNum());
    }

    @Override
    public void doAction(Player player) {
        Flag lastVisitedFlag = player.getCharacter().getVisitedFlag();
        if(lastVisitedFlag == null){
            if(num == 1){
                player.getCharacter().setVisitedFlag(this);
            }
        } else {
            int lastVisitedFlagNum = player.getCharacter().getVisitedFlag().getNum();
            if(lastVisitedFlagNum == num-1){
                player.getCharacter().setVisitedFlag(this);
            }
        }
    }
}
