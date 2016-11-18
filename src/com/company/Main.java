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
    private final static int EVENTPROBABILITY = 5;

    public static void main(String[] args) {
        Boolean isRunning = true;
        int xAxisRange = XAXISMAX - XAXISMIN + 1;
        int yAxisRange = YAXISMAX - YAXISMIN + 1;
        //world object stores the world data and all the event data
        World world = new World(xAxisRange,yAxisRange,XAXISMIN,YAXISMIN,MAXNUMBEROFTICKETS,MAXTICKETPRICE);

        GenerateEvents(world,xAxisRange,yAxisRange);

        //assume coordinates input has no spaces.
        //assume in the case that a few events have the same distance and causes list to exceed 5, events will be picked arbitrarily
        //assume events with no tickets are still shown
        //assume coordinates are linear in distance
        //assume there is at least 1 event
        //assume max tickets for each event is 20
        //assume each coordinate can hold a maximum of one event
        //assume max price of each ticket is 50 dollars
        //assume probability of each event occurring at each coordinate is 20%.
        //check for less than 5 events (done)
        //make sure ticket prices are all 2 decimals
        //make sure event number are all 3 digits

        //distance not in order (maybe done)
        //no repeats of search nodes (maybe done)
        //no prints of more than 5 nearby events (done)
        //check for events on same spot as user(maybe done)
        //arraylist listOfNodes out of bounds error sometimes(maybe done)
        //isRunning never quits
        //need to test validity of searches

        while(isRunning) {
            try {
                int inputXCoordinate;
                int inputYCoordinate;
                int numberOfFoundEvents = 0;
                ArrayList<Node> listOfNodes = new ArrayList<Node>();
                Scanner sc = new Scanner(System.in);

                System.out.println("Please input coordinates: (Input exit to quit)");
                System.out.printf(">");
                String input = sc.next();

                if(input.equals("exit") || input.equals("Exit")){
                    System.out.println("Exiting...");
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
                listOfNodes.add(new Node(inputXCoordinate - XAXISMIN,inputYCoordinate - YAXISMIN));
                numberOfFoundEvents += CheckCoordinateDetails(world.GetCoordinates(inputXCoordinate - XAXISMIN,inputYCoordinate - YAXISMIN),world,inputXCoordinate,inputYCoordinate);
                world.SetSearchedIndex(inputXCoordinate - XAXISMIN,inputYCoordinate - YAXISMIN);

                while(numberOfFoundEvents < NUMBEROFCLOSESTEVENTS){
                    int currentListSize = listOfNodes.size();

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
     * generate events in the world based on specified worldSize
     * @param World world, int xAxisRange, int yAxisRange
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

    private static int SearchNeighbours(World world, ArrayList<Node> listOfNodes, int inputXCoord, int inputYCoord, int numberOfFoundEvents, int currentXIndex, int currentYIndex, int xAxisRange, int yAxisRange){
        int coordinateDetails;

        if(currentXIndex > 0 && world.GetSearchedIndex(currentXIndex-1,currentYIndex) <= 0 && numberOfFoundEvents < NUMBEROFCLOSESTEVENTS){
            world.SetSearchedIndex(currentXIndex-1,currentYIndex);
            listOfNodes.add(new Node(currentXIndex-1,currentYIndex));
            coordinateDetails = world.GetCoordinates(currentXIndex-1,currentYIndex);
            numberOfFoundEvents += CheckCoordinateDetails(coordinateDetails,world,inputXCoord,inputYCoord);
        }
        if(currentYIndex < yAxisRange - 2 && world.GetSearchedIndex(currentXIndex,currentYIndex+1) <= 0 && numberOfFoundEvents < NUMBEROFCLOSESTEVENTS){
            world.SetSearchedIndex(currentXIndex,currentYIndex+1);
            listOfNodes.add(new Node(currentXIndex,currentYIndex+1));
            coordinateDetails = world.GetCoordinates(currentXIndex,currentYIndex+1);
            numberOfFoundEvents += CheckCoordinateDetails(coordinateDetails,world,inputXCoord,inputYCoord);
        }
        if(currentYIndex > 0 && world.GetSearchedIndex(currentXIndex,currentYIndex-1) <= 0 && numberOfFoundEvents < NUMBEROFCLOSESTEVENTS){
            world.SetSearchedIndex(currentXIndex,currentYIndex-1);
            listOfNodes.add(new Node(currentXIndex,currentYIndex-1));
            coordinateDetails = world.GetCoordinates(currentXIndex,currentYIndex-1);
            numberOfFoundEvents += CheckCoordinateDetails(coordinateDetails,world,inputXCoord,inputYCoord);
        }
        if(currentXIndex < xAxisRange - 2 && world.GetSearchedIndex(currentXIndex+1,currentYIndex) <= 0 && numberOfFoundEvents < NUMBEROFCLOSESTEVENTS){
            world.SetSearchedIndex(currentXIndex+1,currentYIndex);
            listOfNodes.add(new Node(currentXIndex+1,currentYIndex));
            coordinateDetails = world.GetCoordinates(currentXIndex+1,currentYIndex);
            numberOfFoundEvents += CheckCoordinateDetails(coordinateDetails,world,inputXCoord,inputYCoord);
        }

        return numberOfFoundEvents;
    }

    private static int CheckCoordinateDetails(int coordinateDetails,World world,int inputXCoord,int inputYCoord){
        if(coordinateDetails > 0){
            printEventDetails(world.GetEvent(coordinateDetails),inputXCoord,inputYCoord);
            return 1;
        }else{
            return 0;
        }
    }

    private static void printEventDetails(Event currentEvent, int inputXCoord, int inputYCoord){
        double cheapestTicket = currentEvent.getCheapestTicket();

        if(cheapestTicket > 0){
            System.out.printf("Event %03d - $%.2f, Distance %d\n",currentEvent.getEventNumber(),cheapestTicket,currentEvent.getDistance(inputXCoord,inputYCoord));
        }else{
            System.out.printf("Event %03d - No tickets available, Distance %d\n",currentEvent.getEventNumber(),currentEvent.getDistance(inputXCoord,inputYCoord));
        }
    }
}
