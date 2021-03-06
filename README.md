# LocationEvent
Inputs a pair of coordinates and returns a list of five closest events with cheapest tickets.

## Build and Run Instructions
1. Download Java JDK from here: http://www.oracle.com/technetwork/java/javase/downloads/index.html
2. Install Java JDK
3. Edit the PATH environment variable. In Windows 10:
  * Launch "Control Panel" ⇒ "System" ⇒ Click "Advanced system settings".
  * Switch to "Advanced" tab ⇒ "Environment Variables".
  * Under "System Variables", scroll down to select "Path" ⇒ "Edit...".
  * You will see a table listing the existing PATH entries. Click "New" ⇒ Enter the JDK's binary directory "c:\Program Files\Java\jdk1.8.0_xx\bin" (Replace xx with your installation's upgrade number)
4. Verify the JDK installation
```
Command : C:\..\ > java -version
java version "1.8.0_xx"
Java(TM) SE Runtime Environment (build 1.8.0_xx-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.5-b02, mixed mode)
```

Once JDK is installed and configured, download this project and compile the Java package on Windows command line:
```
Command : C:\..\LocationEvent > javac src/com/company/*.java
```

After compilation, run the Java package on Windows command line:
```
Command : C:\..\LocationEvent\src > java com.company.Main
```

This should execute the Java program.

## Assumptions
- Assume coordinates input has no blank spaces between x and y coordinate input.
- Assume in the case that a few events have the same distance and causes list to exceed 5, events will be picked arbitrarily.
- Assume events with no tickets are still displayed.
- Assume the distance between neighbouring coordinate in the world is similar.
- Assume max tickets for each event is 20.
- Assume each coordinate can hold a maximum of one event.
- Assume max price of each ticket is 50 dollars.
- Assume probability of each event occurring at any coordinate is 10%.
        
## Testing
Program is tested up to world size of 2000 X 2000.

## Additional Questions
### How might you change your program if you needed to support multiple events at the same location?
> Create a new object called EventContainer. The listOfEvents Array List will be modified to contain EventContainer objects. The EventContainer object will contain a list of Event objects happening at that specific location. This way, all events happening at a specific location can be found by referencing the EventContainer object tied to that location.

### How would you change your program if you were working with a much larger world size?
> With a large world size, using a 2D array to store Event objects would be impractical due to the huge memory space required to create and store the 2D array. I would use a HashMap to store the Event objects with the key being a Node object which will store the x and y coordinates. To search for events, I would use the neighbouring coordinates from the input coordinates to iteratively search the HashMap for valid Events.

## Extra Notes
These values can be easily changed in the program to suit any scenario: 
  1. Maximum ticket amount for each event
  2. Maximum price of each ticket
  3. Probability of each event occuring at any location
  4. World size constraints (x and y axis)
  5. Number of closest events to display to the user
