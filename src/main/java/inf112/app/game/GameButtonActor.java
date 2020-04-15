package inf112.app.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.map.Map;

public class GameButtonActor extends ButtonActor {

    private TiledMapTileLayer.Cell buttonUp;
    private TiledMapTileLayer.Cell buttonDown;
    private TiledMapTileLayer.Cell active;
    private TiledMapTileLayer layer;
    private int x,y;
    private String type;
    private TiledMapStage stage;
    private boolean pushable;


    public GameButtonActor(TiledMapTileLayer.Cell cell, TiledMapTileLayer layer, String type, int x, int y, TiledMapStage stage){
        buttonUp = cell;
        active = cell;
        this.layer = layer;
        this.type = type;
        int index = "powerdown".equals(type) ? 1 : 3;
        TiledMapTileLayer buttons = CardUI.getInstance().getUiButtons();
        buttonDown = buttons.getCell(index,0);
        this.x = x;
        this.y = y;
        this.stage = stage;
        pushable = true;
    }

    @Override
    void setCell(TiledMapTileLayer.Cell cell) {
        this.active = cell;
    }

    @Override
    public TiledMapTileLayer.Cell getCell() {
        return active;
    }

    @Override
    public void clickAction() {
        if(pushable) {
            pushable = false;
            layer.setCell(x, y, buttonDown);
            if ("lockIn".equals(type)) {
                stage.getGame().getPlayer().getCharacter().setDoneProgramming(true);
                Map.getInstance().incrementDoneProgramming();
                stage.setCardLock(false);
                if(stage.getGame().client != null){
                    stage.getGame().client.sendProgramming();
                }
            } else if ("powerdown".equals(type)) {
                stage.getGame().getPlayer().getCharacter().setPowerDownNextRound(true);
            }
        }
    }

    public void releaseButton(){
        pushable = true;
        layer.setCell(x,y,buttonUp);
    }


}
