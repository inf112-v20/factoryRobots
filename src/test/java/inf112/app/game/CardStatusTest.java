package inf112.app.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardStatusTest {
    @Test
    public void compareToTest() {
        MoveCard greaterCard = new MoveCard(200,true);
        MoveCard equalCard = new MoveCard(200,true);
        MoveCard lesserCard = new MoveCard(150,true);

        assertTrue("Failure, cards should be equal",greaterCard.compareTo(equalCard)==0);
        assertTrue("Failure, greaterCard should be larger than lesserCard",greaterCard.compareTo(lesserCard)>0);
        assertTrue("Failure, lesserCard should be smaller than greaterCard",lesserCard.compareTo(greaterCard)<0);
    }
}