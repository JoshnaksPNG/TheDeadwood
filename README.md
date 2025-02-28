# The Deadwood

## What is The Deadwood?

*The Deadwood* is a Java-based implementation of the game *Deadwood* by Cheapass Games. Players play as actors living on a movie lot, competing to act in the most movies, and earn the most money.

## Compiling and Running

### Requirements
* This project is a Maven based project. Anyone looking to compile this project must have set up Maven 4.0.0 or later. \
* This project makes use of Java 20. Anyone looking to compile this project must have Java 20 (or above) installed and set up.

### Compilation
* Download the repository to your desired directory, either through cloning the repo, or through downloading an archive.
* Build the project using Maven with the following command:
  ``` mvn build ```

* This will build the project, leaving a JAR under the ```target``` directory

### Running 
* Now that the project is built, run the project by running the following command: \
  ``` java -cp target/Deadwood-1.0-SNAPSHOT.jar org.runtime.Main ```

* The Game should now be running.
  
