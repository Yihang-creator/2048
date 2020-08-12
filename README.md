# My Personal Project --- 2048

## *What will the application do?*


- play the 2048 game using keyboard upwards arrow(or W), downwards arrow(or S),
 leftwards arrow(or A) and rightwards arrow(or D) to slide numbered tiles on a 4x4 grid to combine tiles to create the number 2048.

- record the ***current score***
- record the ***highest score ever***
- start a new game in the middle of a game
- restart the game if no further moves can be done (in other words, the game ends)



## *Who will use it?*
* anyone who wants play the game




## *Why is this project of interest to you?*
I have been playing this game since my junior high school, and 
I now want to design this game from scratch on my own.

## User stories
- As a user, I want to be able to input the next direction the tiles move in.
- As a user, I want to be able to see how the tiles are distributed after each move.
- As a user, I want to be able to save the game progress anytime after I started a game 
and exit the game. 
- As a user, I want to be able to choose whether to load the game progress or start a new game and erase
 saved progress.
- As a user, I want to be able to restart a new game in the middle of a game.
- As a user, I want to be able to save the score I get in this game to history ranking list.
- As a user, I want to be able to access the ranking list.

## ***Instructions for Grader***

* when there is a last game saved, a window asking whether to load the last game will pop out.
 Press yes to continue or press no to start a new game.
* pressing "ranking" button to look at ranking, magnify the window to see.
* pressing "restart" button to restart the game
* save button save the current score to the ranking. When user clicks this button, a new window will pop out asking 
the user to fill out the name. press confirm to save. User can continue the game after saving.
* the game will automatically save progress if the user clicks X in the window.
the saved progress can be loaded when launching the game next time.
* press "w""s""a""d" or up/down/left/right to move tiles

## Phase 4 - Task 2
Test and design a class that is robust. 
- robust classes include RankingList, Reader and Writer
- RankingList robust methods: sortRankingList, getHighestName, getHighestScore
- Reader robust methods: readMatrix, readRanking, readFile
- Writer robust methods: Writer

## Phase4 - Task 3