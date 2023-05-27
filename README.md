# Chess

Project created by students of the University of Latvia as part of group work for the course Object Oriented Programming

### Authors:
Mihails Kica **(mk20106)**\
Valērijs Zujevičs **(vz20009)**\
Andrejs Sizovs **(as20231)**\
Deniss Ozerskis **(do20030)**\
Rolands Ralfs Ļaudaks **(rl19071)**

## Pre requirement 
1. Latest version of [**Intellij Idea Community edition**](https://www.jetbrains.com/idea/download/#section=windows)
2. Preinstalled [**Java 17**](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

## Running
### Configure Java version:
* In the Intellij Idea, click ```Run->Edit Configurations...```
* Select java 17
* If the configuration was not created by default, create it with
```Run->Edit Configurations...->Add New Configuration->Application``` and configure the following settings:\
\
![Alt text](screenshots/configuration.png "Configuration")

**Note:** Make sure Gradle has downloaded all dependencies and the project is indexed by the IDE.

### Run project
* Choose a configuration and run the project
* Or go to ```com/oop/chess/Main.java``` and run main class

## Testing
### Run tests for the entire project
* From the project root, run the command ```./gradlew test```\
Everything, the tests that have been added will be run automatically.

### Running specific tests
* Go to test module ```src/test/java/com/oop/chess```
* Select the required class and run the test with Intellij Idea\
\
![Alt text](screenshots/tests.png "Tests")
\
In this case for each test a textual representation of the board will be displayed in the console before and after running the test.\
\
![Alt text](screenshots/testOutputExample.png "Test output example")
## Game Rules
1. 1
2. 2
3. 3
