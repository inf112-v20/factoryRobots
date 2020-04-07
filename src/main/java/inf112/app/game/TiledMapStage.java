package inf112.app.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import inf112.app.cards.CardSlot;

public class TiledMapStage extends Stage {
    private final RoboRally game;
    private TiledMap tiledMap;
    private TiledMapTileLayer cardLayer;
    private CardUI cardUI;
    private TiledMapTileLayer buttonLayer;
    private float cardHeight = 1.5f;
    private float cardWidth = 1f;

    private float heightRatio;
    private float widthRatio;

    private ButtonActor[][] actorGrid;

    public TiledMapStage(RoboRally game){
        cardUI = CardUI.getInstance();
        tiledMap = cardUI.getCardUITiles();

        cardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Cards");
        buttonLayer = (TiledMapTileLayer) cardUI.getCardUITiles().getLayers().get("Buttons");

        actorGrid = new ButtonActor[cardLayer.getWidth()][cardLayer.getHeight()];

        createActor(cardLayer);
        instantiateButtons(buttonLayer);

        heightRatio = 1.5f/Gdx.graphics.getHeight();
        widthRatio = 1f/Gdx.graphics.getWidth();

        this.game = game;
    }

    private void createActor(TiledMapTileLayer layer){
        for(int x = 0; x < layer.getWidth(); x++) {
            for(int y = 0; y < layer.getHeight(); y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                CardSlot slot = cardUI.getSlotFromCoordinates(x,y);
                CardSlotActor actor = new CardSlotActor(cell, slot,this);
                actorGrid[x][y] = actor;
                actor.setBounds(x*(cardWidth), y*(cardHeight), cardWidth, cardHeight);  //height 1.5 since that is the cards ratio (400x600)
                addActor(actor);                                            //*1.5f to compensate the stretch downward
                EventListener eventListener = new TiledMapClickListener(actor);
                actor.addListener(eventListener);
            }
        }
    }

    private void instantiateButtons(TiledMapTileLayer layer){
        boolean done = false;
        int x = layer.getWidth() - 1;
        String type = "powerdown";
        while(!done){
            done = x == layer.getWidth() - 2;
            TiledMapTileLayer.Cell cell = layer.getCell(x, 0);
            GameButtonActor actor = new GameButtonActor(cell,layer,type,x,0,this);
            actor.setBounds(x*cardWidth,0,cardWidth,cardHeight);
            addActor(actor);
            EventListener eventListener = new TiledMapClickListener(actor);
            actor.addListener(eventListener);
            actorGrid[x][0] = actor;
            x--;
            type = "lockIn";
        }

    }

    public void resize(int width, int height){
        cardWidth = width*widthRatio;
        cardHeight = height*heightRatio;
        refreshBoundaries();
    }

    private void refreshBoundaries(){
        for(int x = 0; x < actorGrid.length; x++) {
            for (int y = 0; y < actorGrid[x].length; y++) {
                actorGrid[x][y].setBounds(x*cardWidth,y*cardHeight,cardWidth,cardHeight);
            }
        }
    }

    public RoboRally getGame() {
        return game;
    }

    public ButtonActor getActorFromGrid(int x, int y){
        return actorGrid[x][y];
    }
}
