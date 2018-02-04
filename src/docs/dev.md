---
title: "BSc IN S5 / OOP with Java / Wall-J / Wall-J / Devel. notes"
author: [Pacien TRAN-GIRARD, Adam NAILI]
date: 2018-02-04
...

# Preamble

"Wall-J: the Space Cleaner" is a game in which a player uses bombs to push garbage into trash cans in user definable
worlds.

Being part of the "OOP with Java" course at [UPEM](http://www.u-pem.fr/), this application has been entirely
written in Java and makes use of the Zen5, [JBox2D](http://www.jbox2d.org/) and
[JUnit5](http://junit.org/junit5/) libraries.

## Licensing

This work is licensed under the terms of the
[Creative Commons BY-NC-SA 4.0 license](https://creativecommons.org/licenses/by-nc-sa/4.0/) by its authors:
Pacien TRAN-GIRARD and Adam NAILI.

Build-time and embedded run-time dependencies of this program are licensed under their own respective terms.

---

# Compilation and usage

## Building the project

Compilation and production of the different output files is done through Ant and requires a JDK $\geq$ 8.
All the required dependencies of the program are shipped with the project archive,
as the use of Maven was not permitted.

Available Ant targets are:

- `compile`: compiles the Java sources
- `jar`: generates an autonomous executable Java Archive
- `javadoc`: generates the API documentation
- `clean`: deletes the generated resources
- `docs`: compiles the PDF reports (requires Pandoc and PlantUML)
- `zip`: generates the project archive file

Unit tests can be executed through JUnitStarter, the JUnit Ant plugin not supporting JUnit 5.

## Running the program

_Refer to the user manual (user.pdf)._

\newpage

# Considerations

## Specification refinement

The initial specifications have been refined, defining unspecified and implied constraints.

A _board_ or _world_ has been defined as a grid of blocks of the same size.
Their respective properties such as their traversability by the player have been detailed in the user manual.

Additional constraints on board validity have been added to ensure their correctness during their manipulation in the
program. Those conditions have been described in the user manual as well.


## Unit testing

Most utility, logic functions and algorithms have been covered by automatic unit tests to reduce the risk of
small-but-yet-critical mistakes and regressions during the development.
Those automatic tests are ran with the help of the JUnit 5 test framework.

Components and modules related to the graphical user interface could not have been easily tested automatically.
Quality control of such parts of the program have been handled manually.


## Architecture

The architecture of the program is loosely based on the _Model-View-Controller_ pattern,
combined with an event-driven approach.

Input and game generated events are propagated recursively through each sub-module,
which can return new events to be broadcast on the next tick.

Those events are fed to controller methods which alter their parent state holding object.

This architecture ensures a low class coupling while allowing the program to be extended.

\newpage

## Implementation details

### Physics

This game makes use of the JBox2D physics engine to simulate a physical world.
Each block is linked to a corresponding JBox2D body which is used to determine its position and detect collisions.

Bomb explosion management uses the physics engine's ray cast feature to determine which garbage blocks are affected by a blast.
This solution is more realistic than a blast radius-based approach, while being less resource demanding than a particle propagation system.

### Event propagation

Events are broadcast to each and every game entity.
This simple method has been profiled and validated for the low number of events propagated in this game.
A more complex mailbox-driven approach has been considered but was not implemented due to its unnecessary complexity and
the unavailability of a dependency injection framework.


## Additional features

None of the suggested additional feature was implemented.

Those could have been easily added as follows:

- Bouncy walls can be added by defining a new block type with a greater restitution coefficient or a custom collision handler.
- Imploding bombs can be added by defining a new block and a new implosion event types, which would be handled similarly
to the explosion events with a reversed impulse vector.
- A timer can be added in the viewer.


## Notes

- Body in the physics engine should have been scaled down.
- Zen5 could not handle _RETURN_ key events.
- Zen5 ScreenInfo are not updated after a window resize.
- Ant is hell. Gradle is better. 

---

# References

- Javadocs of JBox2D, Zen5, JUnit
- Documentations of the JDK and the Ant build tool
- [A* search algorithm article on Wikipedia](https://en.wikipedia.org/wiki/A*_search_algorithm)
- [JBox2D tutorial request on StackOverflow](https://stackoverflow.com/a/8929199)
- [Hello World JBox2D with JavaFX 2.0](http://thisiswhatiknowabout.blogspot.com/2011/11/hello-world-jbox2d-with-javafx-20.html)
- [Box2D C++ tutorials - Explosions](http://www.iforce2d.net/b2dtut/explosions)
