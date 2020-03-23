package inf112.app.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import inf112.app.game.CardDeck;
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
    private boolean isDead;
    private Position checkPoint;


    //Player sprites
    private TiledMapTileLayer.Cell normalPlayer;
    private TiledMapTileLayer.Cell winningPlayer;
    private TiledMapTileLayer.Cell loosingPlayer;

    public Robot(Position pos, String charName){
        this.map = Map.getInstance();

        this.pos = pos;
        vectorPos = new Vector2(pos.getXCoordinate(),pos.getYCoordinate());

        loadPlayerSprites(charName);

        map.registerRobot(this);

        lastVisited = null;
        damageTokens = 0;
        lives = 3;
        powerDown = false;
        laser = new Laser(this,false);
        isDead = false;
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
    }

    /**
     * Move the robot in a certain direction without turning
     * Only moves one step
     * @param dir Direction to move in
     */
    public void move(Direction dir){
        Position copy = pos.copyOf();
        copy.setDirection(dir);
        boolean valid = map.validMove(copy);
        copy.moveInDirection();
        if(valid && map.robotInTile(copy) == null) {
            updatePosition(this,dir);
        }
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

    /**
     * Rotates the visual representation of the robot
     * @param rot The direction the robot should rotate
     */
    private void rotateSprites(Rotation rot) {
        int orientation = normalPlayer.getRotation();
        switch(rot){
            case LEFT:
                orientation = (orientation + 1) % 4;
                break;
            case RIGHT:
                orientation -= 1;
                orientation = (orientation < 0) ? 3 : orientation;
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
     * @param r Needed since the method is recursive
     * @param dir Used to maintain the orientation of the original robot
     * @return true if Robot has moved, false if not
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
     * The method that all other move methods should call to change the robots position. <br>
     * Does not check if the move is valid, but makes sure that map inventory and vector position
     * is updated in addition to the players position.
     * @param dir The direction the robot should move
     */
     private void updatePosition(Robot robot, Direction dir){
         Position oldPos = robot.getPos().copyOf();
         Direction old = oldPos.getDirection().copyOf();
         robot.getPos().setDirection(dir);
         robot.getPos().moveInDirection();
         robot.getPos().setDirection(old);
         map.getCellList().getCell(oldPos).getInventory().getElements().remove(robot);
         map.getCellList().getCell(robot.getPos()).getInventory().addElement(robot);
         vectorPos.set(robot.getPos().getXCoordinate(), robot.getPos().getYCoordinate());
     }

    /**
     * Method used to check if there is either a wall or a Robot in a cell
     * If there is both, then we prefer the robot
     * @param position The position of the cell we want to check
     * @return The element found in the cell, either a robot or a wall
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

    public Flag getVisitedFlag() {
        return lastVisited;
    }

    public void setVisitedFlag(Flag flag) {
        this.lastVisited = flag;
    }

    public int getDamageTokens() {return damageTokens; }

    public void addDamageTokens(int dealDamage) {
        damageTokens += dealDamage;
        if (damageTokens >= 10) {
            lives--;
            damageTokens = 0;
        }
        System.out.println("Damage tokens:" + damageTokens);
    }

    /**
     * method for deaing the right amount of cards compared to damageTokens
     */
    public void dealNewCards() {
        int amount = 9;
        for (damageTokens = 0; damageTokens < 10; damageTokens++){
            ArrayList<ICard> playerDeck = new CardDeck().getCards(amount);
            amount--;
        }

    }


    /**
     * Method used by repairStation to remove damageTokens from robot.
     * @param amount How many damageTokens should be removed
     */
    public void removeDamageTokens(int amount) {
        damageTokens -= amount;
        if (damageTokens < 0) damageTokens = 0;
    }

    public int getLives() { return lives; }

    /**
     *method for when a robot looses a life
     */
    public void loseLife(){
        this.lives = lives - 1;
        if (this.lives <= 0){
            isDead = true;
        }
    }

    /**
     * method used to find out if robot is dead or alive
     * @return boolean true if dead, false if alive
     */
    public boolean isDead(){
        if (lives == 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Sets a new checkpoint for the robot
     * @param p
     */
    public void setCheckPoint(Position p){
        this.checkPoint = p;
    }


    /**
     * Used to set the powerStatus of the robot(powerDown)
     * @param powerDown
     */
    public void setPowerDown(boolean powerDown) {
        this.powerDown = powerDown;
        damageTokens = 0;
    }

    public boolean getPowerDown() { return powerDown; }

    /**
     * Method for initialising the moves depicted on the cards
     * in the programming slots
     */
    public void initiateRobotProgramme() {
        CardSlot[] slots = CardUI.getInstance().getBottomCardSlots();
        for(CardSlot slot : slots) {
            if (slot.isLocked()){
                ICard card = slot.removeCard();
                if (card != null) {
                    card.doAction(this);
                }
            }
        }
    }

    @Override
    public void fireLaser() {
        laser.fire();
    }
}
