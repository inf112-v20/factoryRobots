package inf112.app.cards;

import inf112.app.GdxTestRunner;
import inf112.app.cards.MoveCard;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(GdxTestRunner.class)
public class CardStatusTest {
    @Test
    public void compareToTest() {
        MoveCard greaterCard = new MoveCard(200,true,null);
        MoveCard equalCard = new MoveCard(200,true,null);
        MoveCard lesserCard = new MoveCard(150,true,null);

        assertTrue("Failure, cards should be equal",greaterCard.compareTo(equalCard)==0);
        assertTrue("Failure, greaterCard should be larger than lesserCard",greaterCard.compareTo(lesserCard)>0);
        assertTrue("Failure, lesserCard should be smaller than greaterCard",lesserCard.compareTo(greaterCard)<0);
    }
}