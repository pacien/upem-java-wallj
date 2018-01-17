---
title: "BSc IN S5 / OOP with Java / Wall-J / Wall-J / Devel. notes"
author: [Pacien TRAN-GIRARD, Adam NAILI]
date: 2018-01-14
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

_"Sir, the testing?"_, Caroline reminds.
Most utility, logic functions and algorithms have been covered by automatic unit tests to reduce the risk of
small-but-yet-critical mistakes and regressions during the development.
Those automatic tests are ran with the help of the JUnit 5 test framework.

Components and modules related to the graphical user interface could not have been easily tested automatically.
Quality control of such parts of the program have been handled manually.

## Architecture

The architecture of the program is loosely based on the _Model-View-Controller_ pattern, combined with an event-driven
approach, separating state objects from actions and events handlers.

A more complete package and class diagram is attached to this report.

TODO:

- include a simplified diagram here
- explain event handling and propagation
- explain why not mailbox/event subscription system
- explain how it minimizes coupling
- explain how we can add stuff like a second robot and whatnot

## Implementation details

TODO:

- per-module remarks:
    - tell who did what in parallel
    - using a ray tracer for explosions

## Additional features

None yet.

## Notes

TODO:

- complain about Ant, beg for Gradle and dependency management
- check that zen5 is actually using a double buffer
- include git commit log?

---

# References

TODO:

- jbox2d, zen5, jdk, ant, junit docs
- A* on wikipedia
- common sense
