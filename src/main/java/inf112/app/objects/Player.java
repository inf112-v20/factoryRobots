package inf112.app.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import inf112.app.map.Map;
import inf112.app.objects.Direction.Rotation;

public class Player extends InputAdapter {
    private Robot character;
    private Vector2 vectorPos;
    private Map map;

    //Player sprites
    private TiledMapTileLayer.Cell normalPlayer;
    private TiledMapTileLayer.Cell winningPlayer;
    private TiledMapTileLayer.Cell loosingPlayer;

    public Player(int x, int y, Map map){
        loadPlayerSprites("player");
        //Initializing input processor
        Gdx.input.setInputProcessor(this);

        vectorPos = new Vector2(x,y);
        this.map = map;
        character = new Robot(new Position(x,y), this.map);
    }

    public void loadPlayerSprites(String charName){
        String path = "assets/" + charName + ".png";
        //Loading and splitting player sprites
        Texture spriteMap = new Texture(path);
        TextureRegion[][] sprites = TextureRegion.split(spriteMap,300,300);

        //Assigning individual sprites
        normalPlayer = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(sprites[0][0]));
        loosingPlayer = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(sprites[0][1]));
        winningPlayer = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(sprites[0][2]));
    }

    public TiledMapTileLayer.Cell getNormal() {
        return normalPlayer;
    }

    public TiledMapTileLayer.Cell getWinning() {
        return winningPlayer;
    }

    public TiledMapTileLayer.Cell getLoosing() {
        return loosingPlayer;
    }

    public Robot getCharacter(){
        return character;
    }

    public Vector2 getVectorPos() {
        return vectorPos;
    }

    /**
     * Changes the players coordinates based on keypress
     * @param keycode Code for key that is being released
     * @return true if key is being released, false if not
     */
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                character.turn(Rotation.LEFT);
                character.move(1);
                character.turn(Rotation.RIGHT);
                break;
            case Input.Keys.RIGHT:
                character.turn(Rotation.RIGHT);
                character.move(1);
                character.turn(Rotation.LEFT);
                break;
            case Input.Keys.UP:
                character.move(1);
                break;
            case Input.Keys.DOWN: //temporary
                character.turn(Rotation.LEFT);
                character.turn(Rotation.LEFT);
                character.move(1);
                character.turn(Rotation.RIGHT);
                character.turn(Rotation.RIGHT);
                break;
            default:
                System.out.println("Unassigned input");
                break;
        }
        vectorPos.set(character.getPos().getXCoordinate(), character.getPos().getYCoordinate());
        return false;
    }

    public void move(int steps){
        character.move(steps);
        vectorPos.set(character.getPos().getXCoordinate(), character.getPos().getYCoordinate());
    }

    public void turn(Rotation rot){
        character.turn(rot);
        vectorPos.set(character.getPos().getXCoordinate(), character.getPos().getYCoordinate());
    }
}
