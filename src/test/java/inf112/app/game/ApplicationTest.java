package inf112.app.game;

import org.junit.Before;


public class ApplicationTest {
   // private Application testApp;

    @Before
    public void setUp() throws Exception {
       // testApp = new Application();
    }
    // # TODO Functionality moved into player class, redefine tests in PlayerTest
    /*

    @Test
    public void playerPositionUpdatesOnKeyUpTest() {
        Vector2 oldPos = testApp.getPlayerPos();
        testApp.keyUp(Input.Keys.LEFT);
        Vector2 newPos = testApp.getPlayerPos();
        assertNotEquals(oldPos,newPos);
    }

    @Test
    public void playerPositionUpdatesUp() {
        Vector2 oldPos = testApp.getPlayerPos();
        oldPos.add(0,1);
        testApp.keyUp(Input.Keys.UP);
        Vector2 newPos = testApp.getPlayerPos();
        assertEquals(oldPos,newPos);
    }

    @Test
    public void playerPositionUpdatesRight() {
        Vector2 oldPos = testApp.getPlayerPos();
        oldPos.add(1,0);
        testApp.keyUp(Input.Keys.RIGHT);
        Vector2 newPos = testApp.getPlayerPos();
        assertEquals(oldPos,newPos);
    }

    @Test
    public void playerPositionUpdatesLeft() {
        Vector2 oldPos = testApp.getPlayerPos();
        oldPos.add(-1,0);
        testApp.keyUp(Input.Keys.LEFT);
        Vector2 newPos = testApp.getPlayerPos();
        assertEquals(oldPos,newPos);
    }

    @Test
    public void playerPositionUpdatesDown() {
        Vector2 oldPos = testApp.getPlayerPos();
        oldPos.add(0,-1);
        testApp.keyUp(Input.Keys.DOWN);
        Vector2 newPos = testApp.getPlayerPos();
        assertEquals(oldPos,newPos);
    } */
}