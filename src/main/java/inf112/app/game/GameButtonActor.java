package inf112.app.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.map.Map;

public class GameButtonActor extends ButtonActor {

    private final TiledMapTileLayer.Cell buttonUp;
    private final TiledMapTileLayer.Cell buttonDown;
    private TiledMapTileLayer.Cell active;
    private final TiledMapTileLayer layer;
    private final int x,y;
    private final String type;
    private final TiledMapStage stage;
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
    public void setCell(TiledMapTileLayer.Cell cell) {
        this.active = cell;
    }

    @Override
    public TiledMapTileLayer.Cell getCell() {
        return active;
    }

    @Override
    public void clickAction() {
        if(pushable) {
            stage.getGame().sounds.buttonSound();
            pushable = false;
            layer.setCell(x, y, buttonDown);
            if ("lockIn".equals(type)) {
                if(stage.getGame().getPlayer().getCharacter().getProgrammedCard(4) != null) {
                    stage.getGame().getPlayer().getCharacter().setDoneProgramming(true);
                    Map.getInstance().incrementDoneProgramming();
                    stage.setCardPushable(false);
                    if (stage.getGame().client != null) {
                        stage.getGame().client.sendProgramming();
                    }
                } else {
                    pushable = true;
                    layer.setCell(x, y, buttonUp);
                }
            } else if ("powerdown".equals(type)) {
                stage.getGame().getPlayer().getCharacter().setPowerDownNextRound(true);
            } else if ("sound".equals(type)){
                RoboRally game = stage.getGame();
                if (game.backgroundMusic.isPlaying()) {
                    game.backgroundMusic.pause();
                }
                else {
                    game.backgroundMusic.play();
                }
            }
        }
    }

    public void releaseButton(){
        pushable = true;
        layer.setCell(x,y,buttonUp);
    }


}
