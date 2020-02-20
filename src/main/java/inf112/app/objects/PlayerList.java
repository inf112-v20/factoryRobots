package inf112.app.objects;

import java.util.ArrayList;

public class PlayerList {
    private ArrayList<Player> playerList;
    public PlayerList() {
        playerList = new ArrayList<>();
    }
    public void appendPlayer(Player player){
        this.playerList.add(player);
    }
    public void removePlayer(Player player){
        this.playerList.remove(player);
    }
}
