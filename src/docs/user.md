---
title: "BSc IN S5 / OOP with Java / Wall-J / User manual"
author: [Pacien TRAN-GIRARD, Adam NAILI]
date: 2018-02-04
...

# Preamble

"Wall-J: the Space Cleaner" is a game in which a player uses bombs to push garbage into trash cans in user definable
worlds.

Being part of the "OOP with Java" course at [UPEM](http://www.u-pem.fr/), this application has been entirely
written in Java and makes use of the Zen5 and [JBox2D](http://www.jbox2d.org/) libraries.

## Licensing

This work is licensed under the terms of the
[Creative Commons BY-NC-SA 4.0 license](https://creativecommons.org/licenses/by-nc-sa/4.0/) by its authors:
Pacien TRAN-GIRARD and Adam NAILI.

Build-time and embedded run-time dependencies of this program are licensed under their own respective terms.

---

# Usage

## Prerequisites

This application requires the Java Runtime Environment version $\geq$ 8.

A graphical session is also needed to display the game window.

## Running the program

The program can be started by running the following command:

    java -jar wallj.jar

A custom world set contained within a directory can be loaded by supplying its path as command line argument as such:

    java -Dlevels=/home/sforthright/bnl/op_cleanup/ -jar wallj.jar

World definition files stored within the given directories and named as `level$i.txt`, where `$i` is a padded integer,
are loaded in alphanumerical order.

\newpage

# Game-play

## Rules

The player controls a robot, dropping bombs whose explosion can push garbage into trash cans.

Garbage bounce when colliding other elements such as walls, unexploded bombs or other garbage blocks,
and disappear of the screen when reaching a garbage can.

The explosion of each of the dropped bombs can be delayed using independent timers, up to 9 seconds.
The robot must drop exactly 3 bombs on each level.

A stage is considered done once the world has been cleared of all its garbage.
The player is then allowed to move on to the next stage.
The game is finished once all stages are completed.

## World

The world is rectangular grid composed of the following type of tiles:

Entity type Appearance   Pushable by bomb Traversable by robot
----------- ------------ ---------------- --------------------
Wall        Black square No               No
Trash can   Red square   No               No
Bomb        Black disk   No               No
Free        Empty        No               Yes
Garbage     Brown disk   Yes              Yes
Robot       Blue disk    /                /

\newpage

## Controls

Trigger                                 Action
-------------------------------         -------------------------------------------------------
Click on a traversable tile             Moves the robot to the pointed location
`SPACE` on a free tile                  Drops a bomb at the current location
`SPACE` on a tile with a bomb           Increases the timer of the bomb at the current location
`S` while controlling the robot         Puts Wall-j into a safe place before activating the bombs
`R` at any moment                       Restarts the current level or continues to the next one if solved
`Q` at any moment                       Exits the game


## Custom levels

Custom world can be defined by the user in plain text files, each of which containing a single world.
Such a definition file most contain no empty line, leading or trailing tabulation or space.

A tile map is a rectangular character array from the following alphabet:

Block type  Character Bounding Must be reachable
----------  --------- -------- -----------------
Free        (space)   No       Yes
Garbage     G         No       Yes
Trash can   T         Yes      Yes
Wall        W         Yes      No

A world is defined as valid if its blocks fulfill the following criteria:

* The bounding box of the defined world must be made of bounding blocks.
* The interior space formed by traversable blocks must be unique and simple.
* Reachable blocks are either adjacent or belonging to the interior space.
* The world must contain at least one trash can and one garbage block.
* The world must have enough free tiles to contain all droppable bombs.

Only valid worlds can be loaded into the game.
The validity of a world may not guaranty the solvability of the puzzle.

\newpage

__Example of invalid world definition:__
```
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW         W
W                              W                               W   WWW   W
W                              W                               W   WTW   W
                      WWWWWWWWWW                               W   WWW   W
                      W        W                               W         W
                      W        W                               WWWWWWWWWWW
                      WWWWWWWWWW                                         W
W                              W                                         W
W                              W                                         W
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
```

__Example of valid world definition:__
```
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
W                                                              WWWWWWWWWWW
W                                                              WWWWWWWWWWW
T                     WWWWWWWWWW                               WWWWWWWWWWW
T                     WWWWWWWWWW        GGGGGGGGGGGGG          WWWWWWWWWWW
T                     WWWWWWWWWW        GGGGGGGG               WWWWWWWWWWW
T                     WWWWWWWWWW        GGGGGGGGGGGGG                    W
W                              W                                         W
W                              W                                         W
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
```
