package inf112.app.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

/**
 *
 * Class for all the cards in the game
 */
public abstract class CardStatus implements Comparable<ICard>, ICard {

    private final int point;
    private boolean isHidden;
    private boolean isLocked;

    private final Texture texture;
    private TiledMapTileLayer.Cell cardTile;

    /**
     * abstract constructor used by sub-classes
     * @param point The amount of priority points the card should have
     */
    public CardStatus(int point, Texture texture) {
        this.point = point;
        this.isHidden = false;
        this.isLocked = false;
        this.texture = texture;

        //Automated tests will pass texture as null
        if(texture != null){
            makeCardTile(texture);
        }

    }


    /**
     * Method for CardStatus constructor to generate the final card texture
     * with the correct amount of priority points printed on the cards
     * @param texture card to be drawn on
     */
    private void makeCardTile(Texture texture){
        SpriteBatch spriteBatch = new SpriteBatch();
        BitmapFont font = new BitmapFont();

        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),false) ;

        frameBuffer.begin();

        Gdx.gl.glClearColor(0f,0f,0f,0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        //draw card texture
        spriteBatch.draw(texture,0,0);
        font.setColor(Color.GREEN);
        //change font size
        font.getData().setScale(3.3f);
        //draw priority points on texture
        font.draw(spriteBatch, String.valueOf(getPoint()),252.5f,545);
        spriteBatch.end();

        TextureRegion textureRegion = new TextureRegion(frameBuffer.getColorBufferTexture(),0,0,frameBuffer.getWidth(),frameBuffer.getHeight());
        textureRegion.flip(false,true);

        cardTile = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(textureRegion));

        frameBuffer.end();

        font.dispose();
        spriteBatch.dispose();
    }


    /**
     *
     * @return priority point
     */
    public int getPoint() { return point; }


    /**
     * returns true if card is in locked register
     * @return True if card is in a locked register, false if not
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * returns true if card is hidden
     * cards are hidden and are one by one shown
     * @return True if card is hidden, false if not
     */
    public boolean isHidden() {
        return isHidden;
    }

    public Texture getTexture() {
        return texture;
    }


    /**
     * sets card in locked state
     * @param b True to set the card as locked, false to unlock it
     */
    public void setLocked(boolean b) {
        isLocked = b;
    }

    /**
     * sets card in hidden state
     * @param b True to set card as hidden, false to show it
     */
    public void setHidden(boolean b) {
        isHidden = b;
    }

    @Override
    public int compareTo(ICard o) {
        return this.point - ((CardStatus) o).point;
    }

    public TiledMapTileLayer.Cell getCardTile() {
        return cardTile;
    }
}
