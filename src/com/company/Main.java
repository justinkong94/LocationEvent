package com.company;
import java.util.*;

public class Main {
    //all of these values can be modified to fit any scenario
    private final static int NUMBEROFCLOSESTEVENTS = 5;
    //world size constraints
    private final static int XAXISMIN = -10;
    private final static int XAXISMAX = 10;
    private final static int YAXISMIN = -10;
    private final static int YAXISMAX = 10;
    //assume max number of tickets for each event is 20
    private final static int MAXNUMBEROFTICKETS = 20;
    //assume max price of each ticket is 50 dollars
    private final static int MAXTICKETPRICE = 50;
    //probabillity of event at any coordinate in (%) percentages
    private final static int EVENTPROBABILITY = 10;

    public static void main(String[] args) {
        Scanner sc = null;
        Boolean isRunning = true;
        int xAxisRange = XAXISMAX - XAXISMIN + 1;
        int yAxisRange = YAXISMAX - YAXISMIN + 1;
        int inputXCoordinate;
        int inputYCoordinate;
        int numberOfFoundEvents = 0;
        int currentListSize;
        ArrayList<Node> listOfNodes = new ArrayList<Node>();
        //world object stores the world data and all the event data
        World world = new World(xAxisRange,yAxisRange,XAXISMIN,YAXISMIN,MAXNUMBEROFTICKETS,MAXTICKETPRICE);

        GenerateEvents(world,xAxisRange,yAxisRange);

        while(isRunning) {
            try {
                sc = new Scanner(System.in);
                System.out.println("Please input coordinates: (Input exit to quit)");
                System.out.printf(">");
                String input = sc.next();

                if(input.equals("exit") || input.equals("Exit")){
                    System.out.println("Exiting...");
                    isRunning = false;
                    break;
                }
                String[] inputSplit = input.split(",",2);
                if(inputSplit.length != 2){
                    throw new InputException("Not a coordinate.");
                }
                inputXCoordinate = Integer.parseInt(inputSplit[0]);
                inputYCoordinate = Integer.parseInt(inputSplit[1]);
                if(inputXCoordinate < XAXISMIN || inputXCoordinate > XAXISMAX){
                    throw new InputException("Invalid coordinates.");
                }
                if(inputYCoordinate < YAXISMIN || inputYCoordinate > YAXISMAX){
                    throw new InputException("Invalid coordinates.");
                }
                System.out.println("\nClosest Events to (" + inputXCoordinate + "," + inputYCoordinate + "):\n");
                //Check current coordinate for events
                numberOfFoundEvents += SearchNode(world,listOfNodes,inputXCoordinate,inputYCoordinate,inputXCoordinate - XAXISMIN,inputYCoordinate - YAXISMIN);
                while(numberOfFoundEvents < NUMBEROFCLOSESTEVENTS){
                    currentListSize = listOfNodes.size();
                    if(currentListSize <= 0){
                        break;
                    }
                    for(int i=0;i<currentListSize;i++){
                        numberOfFoundEvents = SearchNeighbours(world,listOfNodes,inputXCoordinate,inputYCoordinate,numberOfFoundEvents,listOfNodes.get(i).getXIndex(),listOfNodes.get(i).getYIndex(),xAxisRange,yAxisRange);
                    }
                    for(int i=0;i<currentListSize;i++){
                        listOfNodes.remove(0);
                    }
                }
                System.out.println();
                //resets all variables, arrays and Arraylists for new coordinates input
                numberOfFoundEvents = 0;
                listOfNodes.clear();
                world.ClearSearchedIndex();
            } catch(InputException e){
                System.out.println(e);
            } catch(NumberFormatException e){
                System.out.println("Invalid input.");
            } catch(Exception e){
                e.printStackTrace(System.err);
                System.out.println("Unknown error.");
            }
        }
    }
    /**
     * Generate events in the world based on specified worldSize
     * @param world, xAxisRange, yAxisRange
     */
    private static void GenerateEvents(World world, int xAxisRange, int yAxisRange){
        int eventGeneration;
        int eventNumber = 0;
        Random rd = new Random();

        for(int i=0;i<xAxisRange;i++){
            for(int j=0;j<yAxisRange;j++){
                eventGeneration = rd.nextInt(100);
                //if eventGeneration is less than EVENTPROBABILITY, create an event at this coordinate
                if(eventGeneration < EVENTPROBABILITY) {
                    eventNumber++;
                    world.AddEvent(eventNumber,i,j);
                }
            }
        }
    }
    /**
     * Searches the 4 neighbouring nodes around the input coordinate
     * @param world, listOfNodes, inputXCoord, inputYCoord, numberOfFoundEvents, currentXIndex, currentYIndex, xAxisRange, yAxisRange
     * @return numberOfFoundEvents
     */
    private static int SearchNeighbours(World world, ArrayList<Node> listOfNodes, int inputXCoord, int inputYCoord, int numberOfFoundEvents, int currentXIndex, int currentYIndex, int xAxisRange, int yAxisRange){
        //check if neighbour node is at world boundary, check if neighbour node has been visited and if the maximum numberOfFoundEvents has been reached
        if(currentXIndex > 0 && world.GetSearchedIndex(currentXIndex-1,currentYIndex) <= 0 && numberOfFoundEvents < NUMBEROFCLOSESTEVENTS){
            numberOfFoundEvents += SearchNode(world,listOfNodes,inputXCoord,inputYCoord,currentXIndex-1,currentYIndex);
        }
        if(currentYIndex < yAxisRange - 2 && world.GetSearchedIndex(currentXIndex,currentYIndex+1) <= 0 && numberOfFoundEvents < NUMBEROFCLOSESTEVENTS){
            numberOfFoundEvents += SearchNode(world,listOfNodes,inputXCoord,inputYCoord,currentXIndex,currentYIndex+1);
        }
        if(currentYIndex > 0 && world.GetSearchedIndex(currentXIndex,currentYIndex-1) <= 0 && numberOfFoundEvents < NUMBEROFCLOSESTEVENTS){
            numberOfFoundEvents += SearchNode(world,listOfNodes,inputXCoord,inputYCoord,currentXIndex,currentYIndex-1);
        }
        if(currentXIndex < xAxisRange - 2 && world.GetSearchedIndex(currentXIndex+1,currentYIndex) <= 0 && numberOfFoundEvents < NUMBEROFCLOSESTEVENTS){
            numberOfFoundEvents += SearchNode(world,listOfNodes,inputXCoord,inputYCoord,currentXIndex+1,currentYIndex);
        }
        return numberOfFoundEvents;
    }
    /**
     * Adds a new node, search details of node and set node value to has been searched. Prints details of node if events are found at node.
     * @param world, listOfNodes, inputXCoord, inputYCoord, xIndex, yIndex
     * @return 1 if valid event was found, 0 if not found
     */
    private static int SearchNode(World world,ArrayList<Node> listOfNodes,int inputXCoord,int inputYCoord,int xIndex,int yIndex){
        int coordinateDetails = world.GetCoordinatesData(xIndex,yIndex);
        world.SetSearchedIndex(xIndex,yIndex);
        listOfNodes.add(new Node(xIndex,yIndex));

        if(coordinateDetails > 0){
            printEventDetails(world.GetEvent(coordinateDetails),inputXCoord,inputYCoord);
            return 1;
        }else{
            return 0;
        }
    }
    /**
     * Print details of events(event number,cheapest ticket price,distance of event from input coordinate)
     * @param currentEvent, inputXCoord, inputYCoord
     */
    private static void printEventDetails(Event currentEvent, int inputXCoord, int inputYCoord){
        double cheapestTicket = currentEvent.getCheapestTicket();
        if(cheapestTicket > 0){
            System.out.printf("Event %03d - $%.2f, Distance %d\n",currentEvent.getEventNumber(),cheapestTicket,currentEvent.getDistance(inputXCoord,inputYCoord));
        }else{
            System.out.printf("Event %03d - No tickets available, Distance %d\n",currentEvent.getEventNumber(),currentEvent.getDistance(inputXCoord,inputYCoord));
        }
    }
}
