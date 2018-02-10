# skjgen

Ski jumper generator for [Deluxe Ski Jump 4](http://www.mediamond.fi/dsj4/).

Note: this application is under development.

## Prerequisites

* [Deluxe Ski Jump 4](http://mediamond.fi/dsj4) – recommended 1.6.3
* [Java SE Runtime Environment 8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)

## Using the application

1. Prepare file containing the list of ski jumpers to generate.
2. Run DSJ4. Stay in main menu.
3. Run skjgen (bin/skjgen.bat).
4. Select file.
5. Click **Generate**.
6. Go to DSJ4 within 5 seconds.
7. Wait for generation.

## File format

Ski jumpers definitions are stored in a simple text file. Each line of the file contains information about one ski jumper. Each ski jumper may have 14 values delimited with horizontal TAB (\t):
* name (maximum 25 characters),
* country (maximum 3 characters),
* random suit flag (if set to **R**, suit will be randomized),
* 11 hex values of suit colors.

Name is the only required value. If any other value is empty, it won't be filled in game. Lines starting from **^** character are treated as a comment and will be omitted by parser. Empty lines are also omitted.

Note: not all characters are supported yet For now, please use only [ASCII](http://www.asciitable.com/) letters, digits and spaces.

Check sample file bundled with application.

## Building

To build the application, just run gradle wrapper with specified task:

````
gradlew.bat build
````

## License

Check [LICENSE.md](LICENSE.md) for full license text.