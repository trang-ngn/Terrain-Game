# Terrain-Game

Rules:

This is 2-Player mode game
There is a board with size m x n fields and a field is automatically filled at the beginning of the game.
Each player has 4 cards with different symbols on each card and he should choose one and place it on the board.
Each card is like a field with four parts and will be randomly filled with (*,-,o)
The 4-cards of each player will be new created after a round (after being placed)
The goal is to use strategies to get more points at the end
How to get point?:

For each card has been placed, the player will gain 2 points for each empty-neighbor-field, 5 points for same-symbol of the part of adjacent field and -2 points for different-symbol.
Others:

At the beginning of the game, who plays first will be chosen randomly.
Who gets more points will choose first at next round
At the end, who gets more points will win and if the scores are equal, who gets more points in the last round will win
The card is generated randomly with 3 symbols, in the middle is placed with "x", if 4 parts have the same symbols, x will be replaced with this symbol. If 2 neighbor fields have the same symbol, the border "/" and "" will be deleted


![Alt text](<Bildschirmfoto 2023-10-10 um 01.37.12.png>)