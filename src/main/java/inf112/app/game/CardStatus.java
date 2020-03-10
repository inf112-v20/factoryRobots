package inf112.app.game;

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
public abstract class CardStatus implements Comparable {

    private final int point;
    private boolean isHidden;
    private boolean isLocked;

    protected Texture texture;
    protected TiledMapTileLayer.Cell cardTile;

    /**
     * abstract constructor used by sub-classes
     * @param point The amount of priority points the card should have
     */
    public CardStatus(int point, Texture texture) {
        this.point = point;
        this.isHidden = false;
        this.isLocked = false;
        this.texture = texture;

        makeCardTile(texture);
    }

    private void makeCardTile(Texture texture){
        SpriteBatch spriteBatch=new SpriteBatch();
        BitmapFont font=new BitmapFont();

        int width = texture.getWidth();
        int height = texture.getHeight();

        //FrameBuffer frameBuffer=new FrameBuffer(Pixmap.Format.RGBA8888,Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),false) ;

        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,width, height,false);
        frameBuffer.begin();

        Gdx.gl.glClearColor(0f,0f,0f,0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(texture,0,0);
        font.setColor(Color.GREEN);
        font.draw(spriteBatch, String.valueOf(getPoint()),100,100);
        spriteBatch.end();

        TextureRegion textureRegion = new TextureRegion(frameBuffer.getColorBufferTexture(),0,0,frameBuffer.getWidth(),frameBuffer.getHeight());
        textureRegion.flip(false,true);

        cardTile = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(textureRegion));

        frameBuffer.end();
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

    /**
     * functions to set card information
     */

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
    public int compareTo(Object o) {
        return this.point - ((CardStatus) o).point;
    }

    public TiledMapTileLayer.Cell getCardTile() {
        return cardTile;
    }
}
