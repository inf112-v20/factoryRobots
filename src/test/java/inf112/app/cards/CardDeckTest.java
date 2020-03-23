package inf112.app.cards;

import inf112.app.GdxTestRunner;
import inf112.app.cards.CardDeck;
import inf112.app.cards.ICard;
import inf112.app.cards.MoveCard;
import inf112.app.cards.RotateCard;
import inf112.app.map.Direction.Rotation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(GdxTestRunner.class)
public class CardDeckTest {
    int[] correctNumbers;
    int moveOneCount = 18;
    int moveTwoCount = 12;
    int moveThreeCount = 6;
    int backUpCount = 6;
    int rotateLeftCount = 18;
    int rotateRightCount = 18;
    int uTurnCount = 6;

    @Before
    public void setUp() throws Exception {
        correctNumbers = new int[]{moveOneCount,moveTwoCount,moveThreeCount,backUpCount,rotateLeftCount,rotateRightCount,uTurnCount};
    }



    @Test
    public void verifyNumberOfDifferentCardsTest() {
        CardDeck deck = new CardDeck(true);
        while(!deck.isEmpty()){
            ICard card = deck.getCard();
            if(card instanceof MoveCard){
                MoveCard moveCard = (MoveCard) card;
                if(moveCard.isBackUp()){
                    correctNumbers[3] -= 1;
                } else if (moveCard.getSteps() == 1){
                    correctNumbers[0] -= 1;
                } else if (moveCard.getSteps() == 2){
                    correctNumbers[1] -= 1;
                } else if (moveCard.getSteps() == 3) {
                    correctNumbers[2] -= 1;
                }
            } else if(card instanceof RotateCard) {
                RotateCard rotateCard = (RotateCard) card;
                if(rotateCard.isUTurn()){
                    correctNumbers[6] -= 1;
                } else if(rotateCard.getRotation() == Rotation.LEFT){
                    correctNumbers[4] -= 1;
                } else if(rotateCard.getRotation() == Rotation.RIGHT){
                    correctNumbers[5] -= 1;
                }
            }
        }
        for(int i = 0; i<correctNumbers.length; i++){
            assertEquals("Failure, number should be zero",correctNumbers[i],0);
        }
    }
}