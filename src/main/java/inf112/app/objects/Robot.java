package inf112.app.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import inf112.app.map.Map;
import inf112.app.map.MapCellList;
import inf112.app.objects.Direction.Rotation;

/**
 * This class is a representation of the robots
 * on the board
 */
public class Robot implements ILaserInteractor {
    private Map map;
    private Position pos;
    private Vector2 vectorPos;

    //Player sprites
    private TiledMapTileLayer.Cell normalPlayer;
    private TiledMapTileLayer.Cell winningPlayer;
    private TiledMapTileLayer.Cell loosingPlayer;

    public Robot(Position pos, String charName){
        this.pos = pos;
        this.map = Map.getInstance();
        vectorPos = new Vector2(pos.getXCoordinate(),pos.getYCoordinate());
        loadPlayerSprites(charName);
    }

    /**
     * Method to move the robot forward in the direction it is facing
     * @param steps Number of steps the robot should take
     */
    public void move(int steps){
        while(steps!=0){
            steps -= 1;
            if(map.validMove(pos)){
                pos.moveInDirection();
            }
        }
        vectorPos.set(pos.getXCoordinate(), pos.getYCoordinate());
    }

    /**
     * Method to change the direction of the robot
     * @param r Enum for which direction the robot should turn, either LEFT or RIGHT
     */
    public void turn(Rotation r){
        pos.getDirection().turn(r);
        rotateSprites(r);
    }

    public Position getPos() {
        return pos;
    }

    private void rotateSprites(Rotation rot) {
        int orientation = normalPlayer.getRotation();
        switch(rot){
            case LEFT:
                orientation = (orientation + 1) % 4;
                break;
            case RIGHT:
                orientation -= 1;
                if(orientation<0){
                    orientation = 3;
                }
            break;
            default: throw new IllegalArgumentException("Invalid rotation enum in rotateSprites");
        }
        normalPlayer.setRotation(orientation);
        loosingPlayer.setRotation(orientation);
        winningPlayer.setRotation(orientation);
    }

    public TiledMapTileLayer.Cell getNormal() {
        return normalPlayer;
    }

    public TiledMapTileLayer.Cell getWinner() {
        return winningPlayer;
    }

    public TiledMapTileLayer.Cell getLooser() {
        return loosingPlayer;
    }


    /**
     * Loads the different character sprites so that LibGDX can use them
     * @param charName Name of the character, will be used in the filepath to the spritesheet
     */
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

    public void pushRobot () {
        //hvis robot har en robot foran seg i retningen den skal flytte, skal denne dyttes i samme retning slik at

        map.getCellList();
        if (map.getCellList().getCell())

        if(map.validMove(pos)) {
            pos.copyOf().getDirection();
            if(map.validMove(pos) normalPlayer){

            }
            pos.copyOf().moveInDirection();
            pos.moveInDirection() == map.
        }
        map.validMove(pos);
        move(1);

        pos.copyOf().moveInDirection();

        pos.getDirection().getDirEnum()
        return;
    }
}
