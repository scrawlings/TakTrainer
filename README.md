# Tak: A Beautiful Game

Based on the writing of Patrick Rothfuss and developed as a game by James Ernest in collaboration with Patrick Rothfuss, Tak is a beautiful game.

Information on playing the game can be found here: http://cheapass.com/games/tak

And the beta rules can be found here: http://cheapass.com/sites/default/files/TakBetaRules3-10-16.pdf

This project implements the game with in the intention of making a robot player and also to analyse different strategies. In part it is also to develop my own playing because it really is a beautiful game that has captured my imagination.

## Robot

## Game discussion web site

## To Do

RETIRE VERSION 0.1 CODE: It seems most of the bots have performance issues so I'm going to revisit this from a performance first angle. 

  * board and stacks as compact array of primitives when possible, System.arrayCopy() is my new friend
  * pregenerate all partitions and look them up rather than calculating them
  * make a custom cons list for tile stacks, optimised for:
    * take the top sub stack and leave behind the rest
    * break the sub stack according to the partitions
    * add substacks to the top of other stacks (including empty cells)
    * quickly inspect the top element of a stack/cell

Stuff that once seemed right, and will probably be relevant in future: 

  * Have generated all the possible pile partitions validate them in each direction against the board.
  * Create a new board from a move the old board
  * Output moves in standard notation
  * Accept input moves in standard notation
  * Apply anaylsis to boards and ranking heuristics
  * Apply minimax analysis to play a game
  * Visualise the baord in some useful way
  * Play two robots
  * Random first moves
  * Remember heuristic choices and match to game outcomes
  * Push to GIT
  * Analyse performance and optimise
  * Consider which language I'd like to present this with

## Also To Do

Find real people to play with...
