<div align="center">
  <h1>Quoridor</h1>
  <img alt="CircleCI" src="https://img.shields.io/circleci/build/github/Fedrosauro/Quoridor/master?token=509aa96fdc03de36ac62a35ae7b5eabaeb084b1f">
</div>

This is the final project of the Software Development Methods course.

Group:

- Caiola Ludovica [IN2000211]
- Pellizzaro Federico [IN2000196]
- Vassallo Giacomo [IN2000200]

## Rules

Quoridor is an abstract game for 2 players (or 4 players).

### Setup

Each player has a meeple, and a set of walls.

The players stay on opposite sides of the board.

Each player puts his meeple on the bord in the center of the nearest row.

### Goal of the Game

The board is a 10x10 matrix where there is room for walls between each pair of tiles.

A player wins when his meeple is moved to the opposite side of the board.

### Moves

A player can either:

- move his meeple to an adjacent square in any direction (forward, backward, left, right)
- place a wall

Note that when a player has no spare walls, only the movement is allowed

**_NOTE:_** Given a set of coordinate, the wall will be placed from the north or east side of the tile, for horizontal and vertical wall respectively. The picture shows the coordinate (2,2) with wall dimension equals to 2:
<div align="center">
  <img alt="CircleCI" src="https://raw.githubusercontent.com/Fedrosauro/Images/main/Immagine%202023-02-20%20143439.png">
</div>


## Features

- Variable board size
- 4 players game mode
- Variable wall size
- Variable number of walls per player

## Resources

[Board Game Geek](https://boardgamegeek.com/boardgame/624/quoridor)
