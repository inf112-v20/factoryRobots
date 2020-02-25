package inf112.app.game;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 *
 * Class for all the cards in the game
 */
public abstract class GetSetCard {

    private final int point;
    private boolean isHidden;
    private boolean islocked;
    private final String name;
    String cardMessage;
    BufferedImage mainImage;
    BufferedImage pickImage;

    /**
     * abstract constructor used by sub-classes
     * @param point The amount of priority points the card should have
     * @param name The name of the card.
     */
    GetSetCard(int point, String name) {
        this.point = point;
        this.isHidden = false;
        this.islocked = false;
        this.name = name;

    }
    /**
     * functions to get card information
     */

    /**
     * return cards message
     * @return the cardMessage
     */
    public String getCardMessage() { return cardMessage;
    }

    /**
     *
     * @return priority point
     */
    public int getPoint() { return point;
    }

    /**
     * Returns the main icon for the card
     * This is used for a card in the register sloths
     * @return the Main icon
     */
    public ImageIcon getMainIcon() {
        return new ImageIcon(mainImage);
    }

    /**
     * standard icon for a card
     * this is used for a card in the "pick-a-card-list"
     * @return The pick icon for card
     */
    public ImageIcon getPickIcon() {
        return new ImageIcon(pickImage);
    }

    /**
     * returns true if card is in locked register
     * @return True if card is in a locked register, false if not
     */
    public boolean isLocked() {
        return islocked;
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
        islocked = b;
    }

    /**
     * sets card in hidden state
     * @param b True to set card as hidden, false to show it
     */
    public void setHidden(boolean b) {
        isHidden = b;
    }
}
