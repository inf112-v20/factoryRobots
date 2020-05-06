# Manual tests for cards, menu and in game buttons

## Card test

### Testing the card functionality

How: Run the application running Main class. Proceed by clicking on either singleplayer or multiplayer, then click select to start the game. You can now click on cards to do the tests.

1. Selection of action-cards
 - Expect: When card is selected, it moves to player-cardbar.
 - Actual: Card is placed from left to right order in cardbar.

2. Removal of action-cards
 - Expect: Move card from player-cardbar to cardstock when clicked.
 - Actual: Card is removed and placed at previous cardstock position.

3. Random action-cards priority number
 - Expect: When we start the game, the priority number on all action-cards is random.
 - Actual: all priority numbers on action-cards was random after repeatedly restarting the game.

All tests passed

## Menu test

### Testing the menu functionality

How: Run the application running Main class. A menu page has popped up and you can now do the tests.

1. Exit
 - Expect: At all places an exit button is found, it will exit the program if clicked.
 - Actual: Clicking the exit button made the program quit.

2. Return
 - Expect: At all places a return button is found, it will return the user to the previous menu location if clicked.
 - Actual: The user was returned to previous menu location.

3. Singleplayer
 - Expect: When "Singleplayer" is clicked, it will bring the user to the game options menu.
 - Actual: The user was brought to the game options menu.

4. Sound
 - Expect: Has no functionality yet, therefore nothing should happen.
 - Actual: Nothing happened.

5. Switch map arrows
 - Expect: Has no functionality yet, therefore nothing should happen.
 - Actual: Nothing happened.
 
6. How to play
 - Expect: When "How to play" is clicked, it will bring the user to the guide screen.
 - Actual: The user was brought to the guide screen.
 
7. Credits
 - Expect: When "Credits" is clicked, it will bring the user to the credits screen.
 - Actual: The user was brought to the guide screen.
 
8. Join game
 - Expect: When "join game" is clicked, it will show the user a player name and ip address bar.
 - Actual: The user was shown a player name and ip address bar.

All tests passed
