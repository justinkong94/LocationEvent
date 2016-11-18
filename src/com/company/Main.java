package com.company;
import java.util.*;

public class Main {

    private final static int COORDINATEDIMENSION = 2;
    private final static int NUMBEROFCLOSESTEVENTS = 5;
    private final static int XAXISMIN = -5;
    private final static int XAXISMAX = 7;
    private final static int YAXISMIN = -3;
    private final static int YAXISMAX = 6;
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

        //no repeats of search nodes (maybe done)
        //no prints of more than 5 nearby events
        //check for events on same spot as user
        //arraylist listOfNodes out of bounds error sometimes
        //isRunning never quits


        while(isRunning) {
            try {
                System.out.println("Please input coordinates:");
                System.out.printf(">");
                sc = new Scanner(System.in);
                String input = sc.next();
                String[] inputSplit = input.split(",");

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

                int numberOfFoundEvents = 0;
                ArrayList<Node> listOfNodes = new ArrayList<Node>();
                listOfNodes.add(new Node(inputXCoordinate - XAXISMIN,inputYCoordinate - YAXISMIN));
                world.SetSearchedIndex(inputXCoordinate - XAXISMIN,inputYCoordinate - YAXISMIN);
                System.out.println("Closest Events to (" + inputXCoordinate + "," + inputYCoordinate + "):");
                //SearchNearestEvents(inputXCoordinate,inputYCoordinate,xAxisRange,yAxisRange,world,listOfEvents,NUMBEROFCLOSESTEVENTS);
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

    private static void SearchNearestEvents(int inputXCoordinate, int inputYCoordinate, int xAxisRange, int yAxisRange, int[][] world, ArrayList<Event> listOfEvents, int numOfEventsToSearch){
        int currentEventsFound = 0;
        ArrayList<Event> listOfClosestEvents = new ArrayList<Event>();
        //set all currentScan coordinates to array index which represents world.
        int currentScanMinXCoord = inputXCoordinate - XAXISMIN;
        int currentScanMaxXCoord = inputXCoordinate - XAXISMIN;
        int currentScanMinYCoord = inputYCoordinate - YAXISMIN;
        int currentScanMaxYCoord = inputYCoordinate - YAXISMIN;
        Boolean isSearchComplete = false;
        int[][] visitedCoordinates = new int[xAxisRange][yAxisRange];

        while(currentEventsFound < numOfEventsToSearch){
            if(currentScanMinXCoord > 0){
                currentScanMinXCoord--;
                isSearchComplete = false;
            }
            if(currentScanMinYCoord > 0){
                currentScanMinYCoord--;
                isSearchComplete = false;
            }
            if(currentScanMaxXCoord < xAxisRange - 2){
                currentScanMaxXCoord++;
                isSearchComplete = false;
            }
            if(currentScanMaxYCoord < yAxisRange - 2){
                currentScanMaxYCoord++;
                isSearchComplete = false;
            }

            if(isSearchComplete){
                break;
            }

            for(int i=currentScanMinXCoord;i<=currentScanMaxXCoord;i++){
                if(world[i][currentScanMaxYCoord] > 0 && visitedCoordinates[i][currentScanMaxYCoord] == 0){
                    listOfClosestEvents.add(listOfEvents.get(world[i][currentScanMaxYCoord]));
                    currentEventsFound++;
                }
                visitedCoordinates[i][currentScanMaxYCoord] = 1;

                if(world[i][currentScanMinYCoord] > 0 && visitedCoordinates[i][currentScanMinYCoord] == 0){
                    listOfClosestEvents.add(listOfEvents.get(world[i][currentScanMinYCoord]));
                    currentEventsFound++;
                }
                visitedCoordinates[i][currentScanMinYCoord] = 1;
            }
            for(int i=currentScanMinYCoord+1;i<currentScanMaxYCoord;i++){
                if(world[currentScanMinXCoord][i] > 0 && visitedCoordinates[currentScanMinXCoord][i] == 0){
                    listOfClosestEvents.add(listOfEvents.get(world[currentScanMinXCoord][i]));
                    currentEventsFound++;
                }
                visitedCoordinates[currentScanMinXCoord][i] = 1;

                if(world[currentScanMaxXCoord][i] > 0 && visitedCoordinates[currentScanMaxXCoord][i] == 0){
                    listOfClosestEvents.add(listOfEvents.get(world[currentScanMaxXCoord][i]));
                    currentEventsFound++;
                }
                visitedCoordinates[currentScanMaxXCoord][i] = 1;
            }

            isSearchComplete = true;
        }

        SortClosestEvents(inputXCoordinate,inputYCoordinate,listOfClosestEvents);

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

    private static void SortClosestEvents(int inputXCoordinate, int inputYCoordinate, ArrayList<Event> listOfClosestEvents){
        ArrayList<Integer> distanceOfEvents = new ArrayList<Integer>();

        for (Event listOfClosestEvent : listOfClosestEvents) {
            distanceOfEvents.add(listOfClosestEvent.getDistance(inputXCoordinate, inputYCoordinate));
        }

        for(int i=0;i<listOfClosestEvents.size();i++) {

            System.out.printf("Event %d : Distance %d\n",listOfClosestEvents.get(i).getEventNumber(),distanceOfEvents.get(i));
        }
        /*
        for(int i=0;i<NUMBEROFCLOSESTEVENTS;i++){
            int minimumDistanceIndex = 0;
            int minimumDistance = distanceOfEvents.get(0);

            for(int j=1;j<distanceOfEvents.size();j++){
                if(distanceOfEvents.get(j) < minimumDistance){
                    minimumDistanceIndex = j;
                    minimumDistance = distanceOfEvents.get(j);
                }
            }

            //printEventDetails(listOfClosestEvents.get(minimumDistanceIndex),minimumDistance);

            distanceOfEvents.remove(minimumDistanceIndex);
            listOfClosestEvents.remove(minimumDistanceIndex);
        }*/

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
