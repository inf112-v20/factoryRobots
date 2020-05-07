# Manual tests for 
 - cards 
 - menu 
 - in game buttons
 - rounds

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
 - Expect: When "Sound" is clicked, it will pause the soundtrack currently playing and continue if clicked again.
 - Actual: The soundtrack gets paused when clicked once and resumed when clicked twice.

5. Switch map arrows
 - Expect: When the arrows are clicked within the map selection menu, it will change its current map to another map.
 - Actual: The current map was changed to another map.
 
6. How to play
 - Expect: When "How to play" is clicked, it will bring the user to the guide screen.
 - Actual: The user was brought to the guide screen.
 
7. Credits
 - Expect: When "Credits" is clicked, it will bring the user to the credits screen.
 - Actual: The user was brought to the guide screen.
 
8. Join game
 - Expect: When "join game" is clicked, it will show the user a player name and ip address bar.
 - Actual: The user was shown a player name and ip address bar.
 
9. Host game
 - Expect: When "Host game" is clicked, it will show the user a player name bar and a select course option.
 - Actual: The user was shown a player name bar and a select course option.
 
10. Select course
 - Expect: At all places a "Select course" button is found, it will bring the user to the map selection menu.
 - Actual: The user was brought to the map selection menu.

## In game buttons

### Testing the in game buttons fuctionality

How: Run the application running Main class. Proceed by clicking on either Singleplayer or Host game, then start the game. You can now click the buttons to do the tests.

1. Lock in program
 - Expect: If the user have filled inn all their available card slots and then click "Lock in program", then the user's robot will initiate the commands once all users are ready.
 - Actual: The user's commands was anitiated by it's robot.
 
2. Power down
 - Expect: If the user has filled in all their available card slots and then click "Power down" and then "Lock in program", the robot will play out its commands, then remove all damage tokens and play out its commands again in the next round.
 - Actual: The user's commands was played out by it's robot and all damage tokens was removed the next round as the same commands was initiated.
 
 ## Rounds

### Testing the round functionality

How: Run the application running Main class. Proceed by clicking on either Singleplayer or Host game, then start the game. You can now test rounds after initiating commands to the user's robot.

1. Rounds
 - Expect: When a round is started, all the user's robots will act on their given commands, one command at the time till all commands are acted out.
 - Actual: All user's robots are acting out their given commands, one command at the time.

All tests passed
