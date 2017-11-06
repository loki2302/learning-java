# javafx-experiment

On Ubuntu (when using OpenJDK), you need to install OpenJFX separately via `sudo apt-get install openjfx`.

It's possible to run it via Gradle on both Windows and Ubuntu: `./gradlew clean run`.

It's also possible to build a complete package with JRE and executable by running `./gradlew clean assemble`. On Windows this will create an *.exe (running this exe works fine). On Ubuntu it will create an executable as well, but I never managed to run it (though, running the app jar with `java -jar ...` works fine).
