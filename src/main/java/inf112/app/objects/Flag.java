package inf112.app.objects;

import java.util.Objects;

public class Flag implements IBoardElement {
    private int num;

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

    }
}
