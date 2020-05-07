package inf112.app.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import inf112.app.cards.CardSlot;

/**
 * Class extending the {@link Stage} class, that also maintains
 * the in-game buttons and holds and additional Tiledmap grid for clicklisteners
 * corresponding to the {@link CardUI} grid.
 */
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

    /**
     * Creates clicklisteners for the cards in the {@link CardUI} tiledmap grid
     * @param layer the clicklisteners should be based on
     */
    private void createActor(TiledMapTileLayer layer){
        for(int x = 0; x < layer.getWidth(); x++) {
            for(int y = 0; y < layer.getHeight(); y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                CardSlot slot = cardUI.getSlotFromCoordinates(x,y);
                CardSlotActor actor = new CardSlotActor(cell, slot,this);
                actorGrid[x][y] = actor;
                actor.setBounds(x*(cardWidth), y*(cardHeight), cardWidth, cardHeight);
                addActor(actor);
                EventListener eventListener = new TiledMapClickListener(actor);
                actor.addListener(eventListener);
            }
        }
    }

    /**
     * Creates clicklisteners for the buttons in the {@link CardUI} grid
     * @param layer the clicklisteners should be based on the, i.e. the button layer
     */
    private void instantiateButtons(TiledMapTileLayer layer){
        int x = layer.getWidth() - 1;
        String[] type = {"powerdown", "lockin", "sound"};
        for (int i = 0; i < type.length; i++){
            int y = 0;
            if (i == 2) {
                y = 1;
            }
            TiledMapTileLayer.Cell cell = layer.getCell(x, y);
            GameButtonActor actor = new GameButtonActor(cell, layer, type[i], x, y,this);
            actor.setBounds(x * cardWidth, y, cardWidth, cardHeight);
            addActor(actor);
            EventListener eventListener = new TiledMapClickListener(actor);
            actor.addListener(eventListener);
            actorGrid[x][y] = actor;
            x--;
        }
    }

    /**
     * When the screen is resized, the click-listener tiles need to be resized as well
     * This method adjusts the {@link #cardHeight} and {@link #cardWidth} based on the new scale and set ratios
     * and calls the {@link #refreshBoundaries()} method
     * @param width New width of the window
     * @param height New height of the window
     */
    public void resize(int width, int height){
        cardWidth = width*widthRatio;
        cardHeight = height*heightRatio;
        refreshBoundaries();
    }

    /**
     * Sets the size of the click-listener tiles according to the newly adjusted {@link #cardWidth}
     * and {@link #cardHeight}
     */
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

    public GameButtonActor getLockInButton(){
        return (GameButtonActor) actorGrid[cardLayer.getWidth()-2][0];
    }

    public GameButtonActor getPowerdownButton(){
        return (GameButtonActor) actorGrid[cardLayer.getWidth()-1][0];
    }

    /**
     * Makes all the cards and buttons clickable clickable
     */
    public void releaseButtons(){
         getLockInButton().releaseButton();
         getPowerdownButton().releaseButton();
         setCardPushable(true);
    }

    /**
     * Method for setting if the cards are clickable
     * @param clickable true if cards should be clickable, false if they should be locked
     */
    public void setCardPushable(boolean clickable){
        for(Actor actor : getActors()){
            if(actor instanceof CardSlotActor){
                CardSlotActor cardSlot = (CardSlotActor) actor;
                if(cardSlot.getSlot()!=null){
                    if(!cardSlot.getSlot().isLocked()){
                        cardSlot.setPushable(clickable);
                    }
                }


            }
        }
    }
}
