package inf112.app.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import inf112.app.cards.CardSlot;
import inf112.app.cards.ICard;
import inf112.app.game.CardUI;
import inf112.app.game.GameSounds;
import inf112.app.game.TiledMapStage;
import inf112.app.map.Direction;
import inf112.app.map.Map;
import inf112.app.map.Position;
import inf112.app.map.Direction.Rotation;

import java.util.ArrayList;

/**
 * This class is a representation of a robot
 * on the board. Since the robot possesses a laser it also
 * implements the {@link ILaserInteractor} interface
 */
public class Robot implements ILaserInteractor, IBoardElement {
    private int id;
    private Position pos;
    private Vector2 vectorPos;

    private Flag lastVisited;
    private ArrayList<Flag> allFlags;

    private Laser laser;

    private int damageTokens;
    private int lives;
    private boolean isWinner;
    private boolean isDead;
    public boolean isHit = false;
    public boolean fellIntoHole = false;

    private boolean powerDown;

    private Position checkPoint;

    private CardSlot[] availableCards;
    private CardSlot[] programmedCards;

    private boolean doneProgramming;

    private boolean powerDownNextRound;

    //Player sprites
    private TiledMapTileLayer.Cell normalPlayer;
    private TiledMapTileLayer.Cell winningPlayer;
    private TiledMapTileLayer.Cell loosingPlayer;

    private GameSounds sound;

    /**
     * Creates a {@link Robot} instance. Loads the player sprites
     * registers the robot with the {@link Map} instance and initializes fields to default values
     * @param pos Initial position of the robot
     * @param charName Character name of the robot, used to locate the appropriate sprites in the file-system
     */
    public Robot(Position pos, String charName){
        this.pos = pos;
        vectorPos = new Vector2(pos.getXCoordinate(),pos.getYCoordinate());

        loadPlayerSprites(charName);

        Map.getInstance().registerRobot(this);

        lastVisited = null;
        damageTokens = 0;
        lives = 3;
        powerDown = false;
        laser = new Laser(this,false);
        isDead = false;
        isWinner = false;
        doneProgramming = false;
        this.checkPoint = pos.copyOf();

        initializeCardsSlots();
        id = -1;
        allFlags = new ArrayList<>();
    }

    /**
     * Creates instances for the indexes in the {@link CardSlot} arrays.
     * If the robot belongs to the user these will be overwritten by
     * graphical slots at a later point
     */
    private void initializeCardsSlots(){
        programmedCards = new CardSlot[5];
        availableCards = new CardSlot[9];
        for(int i = 0; i<9; i++){
            if(i < 5){
                programmedCards[i] = new CardSlot("bottom");
            }
            availableCards[i] = new CardSlot("side");
        }
    }

    /**
     * Method used for passing the sound-effects from the {@link inf112.app.screens.GameScreen}
     * to this class
     * @param sounds
     */
    public void assignGameSounds(GameSounds sounds){
        this.sound = sounds;
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
        boolean valid = Map.getInstance().validMove(copy);
        copy.moveInDirection();
        if(valid && Map.getInstance().robotInTile(copy) == null) {
            updatePosition(this,dir);
        } else if(!valid){
            if(Map.getInstance().isOutSideMap(copy)){
                fellIntoHole = true;
            }
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
        String path = "assets/Robots/" + charName + ".png";
        //Loading and splitting player sprites
        Texture spriteMap = new Texture(path);
        TextureRegion[][] sprites = TextureRegion.split(spriteMap,300,300);
        //Assigning individual sprites
        normalPlayer = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(sprites[0][0]));
        winningPlayer = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(sprites[0][1]));
        loosingPlayer = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(sprites[0][2]));
    }

    /**
     * @param r Needed since the method is recursive
     * @param dir Used to maintain the orientation of the original robot
     * @return true if Robot has moved, false if not
     */
     public boolean moveAndPush(Robot r, Direction dir) {
         Position newPos = r.getPos().copyOf();
         newPos.setDirection(dir.copyOf());
         if(Map.getInstance().validMove(newPos)) {
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
         } else {
             newPos.moveInDirection();
             if(Map.getInstance().isOutSideMap(newPos)){
                 r.fellIntoHole = true;
             }
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
         Map.getInstance().getCellList().getCell(oldPos).getInventory().getElements().remove(robot);
         Map.getInstance().getCellList().getCell(robot.getPos()).getInventory().addElement(robot);
         vectorPos.set(robot.getPos().getXCoordinate(), robot.getPos().getYCoordinate());
         if(Map.getInstance().getLayer("Hole").getCell(robot.getPos().getXCoordinate(),robot.getPos().getYCoordinate()) != null){
             robot.fellIntoHole = true;
         }
     }

    /**
     * Method used to check if there is either a wall or a Robot in a cell
     * If there is both, then we prefer the robot
     * @param position The position of the cell we want to check
     * @return The element found in the cell, either a robot or a wall
     */
    public IBoardElement checkContentOfCell(Position position) {
        ArrayList<IBoardElement> newCell = Map.getInstance().getCellList().getCell(position).getInventory().getElements();
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
        //do nothing
    }

    /**
     * @return The last flag the robot visited that was in the valid order
     */
    public Flag getVisitedFlag() {
        return lastVisited;
    }

    /**
     * Sets the field for which flag robot previously visited that was in valid order
     * @param flag The flag to be assigned
     */
    public void setVisitedFlag(Flag flag) {
        this.lastVisited = flag;
        allFlags.add(flag);
        if(flag.getNum() == Map.getInstance().getFinalFlagNum()){
            isWinner = true;
        }
    }

    public int getDamageTokens() {return damageTokens; }

    /**
     * Adds damagetokens to the damagetoken field, check if the robot has sustained
     * enough damage to die, if so kills the robot and returns it to its last checkpoint,
     * as well as triggering the sound-effect for taking damage
     * @param dealDamage The amount of damage to be dealt
     */
    public void addDamageTokens(int dealDamage) {
        damageTokens += dealDamage;
        checkSlotsToLock();
        if (damageTokens >= 9) {
            takeLife();
            backToCheckPoint();
        } else {
            try {
                sound.takeDamage();
            } catch (NullPointerException ignored){ // Catch exception for test classes

            }
            if(this.equals(CardUI.getInstance().getUser().getCharacter())){
                CardUI.getInstance().updateDamageTokens(damageTokens);
            }
        }

    }

    /**
     * Method for checking which programming card slots that should be locked
     * based on the amount of damage the robot has received. Method both check and locks
     * the appropriate slots
     */
    private void checkSlotsToLock(){
        int i = 5;
        if(damageTokens>=8){
            i = 1;
        }else if(damageTokens>=7){
            i = 2;
        }else if(damageTokens>=6){
            i = 3;
        }else if(damageTokens>=5){
            i = 4;
        }
        int j = 0;
        while(j<i){
            programmedCards[j].unlockSlot();
            j++;
        }
        while(i<5){
            programmedCards[i].lockSlot();
            i++;
        }
    }

    /**
     * Method for dealing the right amount of cards compared based on {@link #damageTokens}
     * and {@link #powerDown} status
     */
    public void dealNewCards(TiledMapStage stage) {
        wipeSlots(availableCards);
        wipeSlots(programmedCards);
        if (powerDown){
            return;
        }

        for (int i = 0; i<9-damageTokens; i++) {
            availableCards[i].addCard(Map.getInstance().getDeck().getCard(),stage);
        }
        //Assign cards to slots that have been locked during powerdown
        for(CardSlot slot : programmedCards){
            if(slot.isLocked() && !slot.hasCard()){
                slot.addCard(Map.getInstance().getDeck().getCard(),stage);
            }
        }
    }

    /**
     * Method for removing all the cards from an array of {@link CardSlot}s
     * @param slotList the array of slots that should be cleared of cards
     */
    public void wipeSlots(CardSlot[] slotList){
        for(CardSlot slot : slotList){
            if(!slot.isLocked()){
                slot.removeCard();
            }
        }
    }


    /**
     * Method used by repairStation to remove damageTokens from robot.
     * @param amount How many damageTokens should be removed
     */
    public void removeDamageTokens(int amount) {
        damageTokens -= amount;
        if (damageTokens < 0) damageTokens = 0;
        if(CardUI.getInstance().getUser().getCharacter().equals(this)){
            CardUI.getInstance().updateDamageTokens(damageTokens);
        }
        checkSlotsToLock();

    }


    /**
     * method used to find out if robot is dead or alive
     * @return boolean true if dead, false if alive
     */
    public boolean isDead(){
        return isDead;
    }

    public boolean isWinner(){
        return isWinner;
    }

    /**
     * Sets a new checkpoint for the robot
     * @param p
     */
    public void setCheckPoint(Position p){
        this.checkPoint = p;
        try {
            sound.checkpoint();
        } catch (NullPointerException ignored){ // Preventing error in test classes

        }
    }

    /**
     * Updates the robots position to its last checkpoint. Checks if the position is occupied,
     * and if so, spawns the robot at a neighboring position instead
     */
    public void backToCheckPoint(){
        Position oldPos = this.pos.copyOf();
        Direction old = oldPos.getDirection().copyOf();
        this.pos = checkPoint.copyOf();
        this.pos.setDirection(old);
        if(Map.getInstance().robotInTile(checkPoint) != null && !Map.getInstance().robotInTile(checkPoint).equals(this)){
            while(!Map.getInstance().validMove(pos)){
                pos.getDirection().turn(Rotation.LEFT);
                rotateSprites(Rotation.LEFT);
            }
            pos.moveInDirection();
        }
        Map.getInstance().getCellList().getCell(oldPos).getInventory().getElements().remove(this);
        Map.getInstance().getCellList().getCell(this.pos).getInventory().addElement(this);
        vectorPos.set(this.pos.getXCoordinate(), this.pos.getYCoordinate());
    }


    /**
     * Used to set the powerStatus of the robot(powerDown)
     * @param powerDown
     */
    public void setPowerDown(boolean powerDown) {
        this.powerDown = powerDown;
        if(powerDown == true){
            damageTokens = 0;
            checkSlotsToLock();
            if(CardUI.getInstance().getUser().getCharacter().equals(this)){
                CardUI.getInstance().updateDamageTokens(0);
            }
        }

    }

    public void setPowerDownNextRound(boolean powerDownNextRound){
        this.powerDownNextRound = powerDownNextRound;
    }

    public boolean getPowerDownNextRound(){
        return powerDownNextRound;
    }

    public boolean getPowerDown() { return powerDown; }

    /**
     * Sets a players programmed card into its register.
     * @param index The register index to set the card for.
     * @param card The card to set.
     */
    public void setProgrammedCard(int index, ICard card) {
        if (index >= 0 && index < 5) {
            programmedCards[index].addCard(card,null);
        } else {
            throw new IllegalArgumentException("Index must be between 0 and 4");
        }
    }

    /**
     * returns a programmed card form a specific position in
     * the register array
     * @param index position of the card in the register array
     * @return The card in the position if there is one, null if not present
     */
   public ICard getProgrammedCard(int index){
        if (index >= 0 && index < 5){
            return programmedCards[index].getCard();
        } else {
            throw new IllegalArgumentException("Index must be between 0 and 4");
        }
   }

    public boolean doneProgramming(){
       return doneProgramming;
    }

    /**
     * Method for triggering the laser possessed by the robot to fire.
     * Laser won't fire if the robot is dead.
     */
    @Override
    public void fireLaser() {
       if(!isDead){
           laser.fire();
       }
    }

    public CardSlot[] getAvailableCards() {
        return availableCards;
    }

    public CardSlot[] getProgrammedCards() {
        return programmedCards;
    }

    /**
     * Used by the {@link inf112.app.networking.RoboClient} to assign id's
     * to the robots that are decided by the server. This in order to distinguish which robots
     * belong to which clients
     * @param id ID to be assigned the robot
     */
    public void assignID(int id){
       if(this.id == -1){
           this.id = id;
       }
    }

    public int getID(){
       return id;
    }

    public void setDoneProgramming(boolean done) {
       this.doneProgramming = done;
    }

    public int getLives() {
        return lives;
    }

    /**
     * Subtracts a life from the {@link #lives} field. Updates the health light graphics
     * appropriately using the {@link CardUI#setHealthLight(int)} method if the robot
     * belongs to the user. Removes all {@link #damageTokens} unlocks, slots and triggers the
     * dying sound-effect
     */
    public void takeLife() {
       lives--;
       if(CardUI.getInstance().getUser().getCharacter().equals(this)){
           CardUI.getInstance().setHealthLight(lives);
       }
       removeDamageTokens(9);
       checkSlotsToLock();
       isDead = lives <= 0;
        try {
            sound.deathSound();
        } catch (NullPointerException ignored){ // Catch exception for test classes

        }
    }

    /**
     * Used by the {@link inf112.app.screens.GameScreen}
     * to know if it should display the {@link #loosingPlayer} sprite
     * instead of the normal one.
     * @return true if the robot has been hit by a laser, false if not
     */
    public boolean isHit() {
        return isHit;
    }
}
