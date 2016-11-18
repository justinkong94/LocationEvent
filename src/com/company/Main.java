package com.company;
import java.util.*;

public class Main {

    private final static int COORDINATEDIMENSION = 2;
    private final static int NUMBEROFCLOSESTEVENTS = 5;
    private final static int XAXISMIN = -10;
    private final static int XAXISMAX = 10;
    private final static int YAXISMIN = -10;
    private final static int YAXISMAX = 10;
    //assume max number of tickets for each event is 20
    private final static int MAXNUMBEROFTICKETS = 20;
    //assume max price of each ticket is 50 dollars
    private final static int MAXTICKETPRICE = 50;

    public static void main(String[] args) {
	// write your code here
        Scanner sc;
        Boolean isRunning = true;

        int xAxisRange = XAXISMAX - XAXISMIN + 1;
        int yAxisRange = YAXISMAX - YAXISMIN + 1;
        //world object stores the world data and all the event data
        World world = new World(xAxisRange,yAxisRange,XAXISMIN,YAXISMIN,MAXNUMBEROFTICKETS,MAXTICKETPRICE);

        GenerateEvents(world,xAxisRange,yAxisRange);

        //assume in the case that a few events have the same distance and causes list to exceed 5, events will be picked arbitrarily
        //assume events with no tickets are still shown
        //assume coordinates are linear in distance
        //assume there is at least 1 event
        //assume max tickets for each event is 20
        //assume each coordinate can hold a maximum of one event
        //assume max price of each ticket is 50 dollars
        //assume probability of each event occurring at each coordinate is 20%.
        //check for less than 5 events
        //make sure ticket prices are all 2 decimals
        //make sure event number are all 3 digits

        //distance not in order (maybe done)
        //no repeats of search nodes (maybe done)
        //no prints of more than 5 nearby events
        //check for events on same spot as user(maybe done)
        //arraylist listOfNodes out of bounds error sometimes(maybe done)
        //isRunning never quits


        while(isRunning) {
            try {
                System.out.println("Please input coordinates:");
                System.out.printf(">");
                sc = new Scanner(System.in);
                String input = sc.next();
                String[] inputSplit = input.split(",",2);

                if(inputSplit.length != COORDINATEDIMENSION){
                    throw new InputException("Not a coordinate.");
                }

                int inputXCoordinate = Integer.parseInt(inputSplit[0]);
                int inputYCoordinate = Integer.parseInt(inputSplit[1]);

                if(inputXCoordinate < XAXISMIN || inputXCoordinate > XAXISMAX){
                    throw new InputException("Invalid coordinates.");
                }
                if(inputYCoordinate < YAXISMIN || inputYCoordinate > YAXISMAX){
                    throw new InputException("Invalid coordinates.");
                }

                System.out.println("Closest Events to (" + inputXCoordinate + "," + inputYCoordinate + "):");
                int numberOfFoundEvents = 0;
                ArrayList<Node> listOfNodes = new ArrayList<Node>();

                listOfNodes.add(new Node(inputXCoordinate - XAXISMIN,inputYCoordinate - YAXISMIN));
                numberOfFoundEvents += CheckCoordinateDetails(world.GetCoordinates(inputXCoordinate - XAXISMIN,inputYCoordinate - YAXISMIN),world,inputXCoordinate,inputYCoordinate);
                world.SetSearchedIndex(inputXCoordinate - XAXISMIN,inputYCoordinate - YAXISMIN);

                while(numberOfFoundEvents < NUMBEROFCLOSESTEVENTS){
                    int currentListSize = listOfNodes.size();
                    for(int i=0;i<currentListSize;i++){
                        numberOfFoundEvents += SearchNeighbours(world,listOfNodes,inputXCoordinate,inputYCoordinate,listOfNodes.get(i).getXIndex(),listOfNodes.get(i).getYIndex(),xAxisRange,yAxisRange);
                    }
                    for(int i=0;i<currentListSize;i++){
                        listOfNodes.remove(0);
                    }
                }

                world.ClearSearchedIndex();

            } catch(InputException e){
                //e.printStackTrace();
                System.out.println(e);
            } catch(NumberFormatException e){
                //e.printStackTrace(System.err);
                System.out.println("Invalid input.");
            } catch(Exception e){
                e.printStackTrace(System.err);
                System.out.println("Unknown error.");
            }
        }
    }

    /**
     * generate events in the world based on specified worldSize
     * @param xAxisRange, yAxisRange
     */
    private static void GenerateEvents(World world, int xAxisRange, int yAxisRange){
        int eventGeneration;
        int eventNumber = 0;
        Random rd = new Random();

        for(int i=0;i<xAxisRange;i++){
            for(int j=0;j<yAxisRange;j++){
                //assume probability of an event occurring at each coordinate is 20%.
                eventGeneration = rd.nextInt(5);
                //if isEvent is true, create an event at this coordinate
                if(eventGeneration <= 0) {
                    eventNumber++;
                    world.AddEvent(eventNumber,i,j);
                }
            }
        }
    }

    private static int SearchNeighbours(World world, ArrayList<Node> listOfNodes, int inputXCoord, int inputYCoord, int currentXIndex, int currentYIndex, int xAxisRange, int yAxisRange){
        int coordinateDetails;
        int numberOfSearchedEvents = 0;

        if(currentXIndex > 0 && world.GetSearchedIndex(currentXIndex-1,currentYIndex) <= 0){
            world.SetSearchedIndex(currentXIndex-1,currentYIndex);
            listOfNodes.add(new Node(currentXIndex-1,currentYIndex));
            coordinateDetails = world.GetCoordinates(currentXIndex-1,currentYIndex);
            numberOfSearchedEvents += CheckCoordinateDetails(coordinateDetails,world,inputXCoord,inputYCoord);
        }
        if(currentYIndex < yAxisRange - 2 && world.GetSearchedIndex(currentXIndex,currentYIndex+1) <= 0){
            world.SetSearchedIndex(currentXIndex,currentYIndex+1);
            listOfNodes.add(new Node(currentXIndex,currentYIndex+1));
            coordinateDetails = world.GetCoordinates(currentXIndex,currentYIndex+1);
            numberOfSearchedEvents += CheckCoordinateDetails(coordinateDetails,world,inputXCoord,inputYCoord);
        }
        if(currentYIndex > 0 && world.GetSearchedIndex(currentXIndex,currentYIndex-1) <= 0){
            world.SetSearchedIndex(currentXIndex,currentYIndex-1);
            listOfNodes.add(new Node(currentXIndex,currentYIndex-1));
            coordinateDetails = world.GetCoordinates(currentXIndex,currentYIndex-1);
            numberOfSearchedEvents += CheckCoordinateDetails(coordinateDetails,world,inputXCoord,inputYCoord);
        }
        if(currentXIndex < xAxisRange - 2 && world.GetSearchedIndex(currentXIndex+1,currentYIndex) <= 0){
            world.SetSearchedIndex(currentXIndex+1,currentYIndex);
            listOfNodes.add(new Node(currentXIndex+1,currentYIndex));
            coordinateDetails = world.GetCoordinates(currentXIndex+1,currentYIndex);
            numberOfSearchedEvents += CheckCoordinateDetails(coordinateDetails,world,inputXCoord,inputYCoord);
        }

        return numberOfSearchedEvents;
    }

    private static int CheckCoordinateDetails(int coordinateDetails,World world,int inputXCoord,int inputYCoord){
        int foundEvent = 0;
        if(coordinateDetails > 0){
            foundEvent++;
            printEventDetails(world.GetEvent(coordinateDetails),inputXCoord,inputYCoord);
        }

        return foundEvent;
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
