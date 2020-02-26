package inf112.app.objects;

public class Flag implements IBoardElement {
    private int num;

    public Flag(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
