package inf112.app.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import inf112.app.game.CardSlot;
import inf112.app.game.ICard;
import inf112.app.map.Map;
import inf112.app.objects.Direction.Rotation;
import inf112.app.screens.CardUI;

import java.util.ArrayList;

/**
 * This class is a representation of the robots
 * on the board
 */
public class Robot implements ILaserInteractor, IBoardElement {
    private Map map;
    private Position pos;
    private Vector2 vectorPos;
    private Flag lastVisited;
    private Laser laser;
    private int damageTokens;
    private int lives;
    private boolean powerDown;

    //Player sprites
    private TiledMapTileLayer.Cell normalPlayer;
    private TiledMapTileLayer.Cell winningPlayer;
    private TiledMapTileLayer.Cell loosingPlayer;

    public Robot(Position pos, String charName){
        this.pos = pos;
        this.map = Map.getInstance();
        vectorPos = new Vector2(pos.getXCoordinate(),pos.getYCoordinate());
        loadPlayerSprites(charName);
        map.getCellList().getCell(pos).getInventory().addElement(this);
        lastVisited = null;
        damageTokens = 0;
        lives = 3;
        powerDown = false;
        laser = new Laser(this,false);
    }

    /**
     * Method to move the robot forward in the direction it is facing
     * @param steps Number of steps the robot should take
     */
    public void move(int steps){
        while(steps!=0){
            steps -= 1;
            moveAndPush(this,getPos().getDirection());
        }
        vectorPos.set(pos.getXCoordinate(), pos.getYCoordinate());
    }

    /**
     * Move the robot in a certain direction without turning
     * Only moves one step
     * @param dir Direction to move in
     */
    public void move(Direction dir){
        Direction saved = pos.getDirection().copyOf();
        pos.setDirection(dir);
        Position copy = pos.copyOf();
        copy.moveInDirection();
        if(map.validMove(pos) && !map.robotInTile(copy)) {
            pos.moveInDirection();
        }
        pos.setDirection(saved);
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

    /**
     *
     * @param r by recursion, the position is updated after a move
     * @param dir maintain the orientation of the original robot
     * @return true if Robot can move, false if not
     */
     public boolean moveAndPush(Robot r, Direction dir) {
         Position newPos = r.getPos().copyOf();
         newPos.setDirection(dir);
         if(map.validMove(newPos)) {
             newPos.moveInDirection();
             IBoardElement nextCell = checkContentOfCell(newPos);
             if (nextCell == null || nextCell instanceof Wall) {
                 updatePosition(r,dir);
                 return true;
             } else if (nextCell instanceof Robot) {
                 Robot next = (Robot) nextCell;
                 boolean canMove = moveAndPush(next,dir);
                 if (canMove) {
                     updatePosition(r,dir);
                 }
                 return canMove;
             }
             return false;
         }
         return false;
     }

    /**
     *
     * @param r
     * @param dir position of
     */
     private void updatePosition(Robot r, Direction dir){

         Position oldPos = r.getPos().copyOf();
         Direction old = oldPos.getDirection().copyOf();
         r.getPos().setDirection(dir);
         r.getPos().moveInDirection();
         r.getPos().setDirection(old);
         int index = map.getCellList().getCell(oldPos).getInventory().getElements().indexOf(r);
         map.getCellList().getCell(oldPos).getInventory().getElements().remove(index);
         map.getCellList().getCell(r.getPos()).getInventory().addElement(r);
     }

    /**
     *
     * @param position what position the robot is in
     * @return if Robot can go to the next cell
     */
    public IBoardElement checkContentOfCell(Position position) {
        ArrayList<IBoardElement> newCell = map.getCellList().getCell(position).getInventory().getElements();
        IBoardElement elem = null;
        for (IBoardElement e : newCell) {
            if(e instanceof Robot){
                elem = e;
            } else if(e instanceof Wall){
                if(elem == null){
                    elem = e;
                }

            }
        }
        return elem;
    }

    @Override
    public void doAction(Robot robot) {
        fireLaser();
    }

    /**
     * returns a list used to determine if robots have visited flags
     * @return
     */
    public Flag getVisitedFlag() {
        return lastVisited;
    }

    public void setVisitedFlag(Flag flag) {
        this.lastVisited = flag;
    }

    /**
     * returns an int value determining the robots damage tokens
     * @return
     */
    public int getDamageTokens() {return damageTokens; }

    public void addDamageTokens(int dealDamage) {
        this.damageTokens += dealDamage;
        if (damageTokens >= 10) {
            lives--;
            damageTokens = 0;
        }
    }

    /**
     * Used by repairStation to remove damageTokens
     * @param amount
     */
    public void removeDamageTokens(int amount) {
        damageTokens -= amount;
        if (damageTokens < 0) damageTokens = 0;
    }

    /**
     * returns amount of lives the robot have
     * @return
     */
    public int getLives() { return lives; }

    /**
     * Used to set the powerStatus of the robot(powerDown)
     * @param power
     */
    public void setPowerDown(boolean power) {
        powerDown = power;
        if (power) damageTokens = 0;
    }

    public boolean getPowerDown() {return powerDown; }

    public void initiateRobotProgramme() {
        CardSlot[] slots = CardUI.getInstance().getBottomCardSlots();
        for(CardSlot slot : slots){
            ICard card  = slot.removeCard();
            if(card != null){
                card.doAction(this);
            }
        }
    }

    @Override
    public void fireLaser() {
        laser.fire();
    }
}
