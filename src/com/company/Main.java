package com.company;
import java.util.*;

public class Main {

    private final static int COORDINATEDIMENSION = 2;
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
        int xCoordinate = 0;
        int yCoordinate = 0;

        int xAxisRange = XAXISMAX - XAXISMIN + 1;
        int yAxisRange = YAXISMAX - YAXISMIN + 1;
        //worldSize stores the unique numeric identifier for each event
        int[][] worldSize = new int[xAxisRange][yAxisRange];
        ArrayList<Event> listOfEvents = new ArrayList<Event>();

        GenerateEvents(xAxisRange,yAxisRange,worldSize,listOfEvents);

        //assume there is at least 1 event
        //assume max tickets for each event is 20
        //assume each coordinate can hold a maximum of one event
        //assume max price of each ticket is 50 dollars
        //assume probability of each event occurring at each coordinate is 50%.
        //check for less than 5 events

        while(isRunning) {
            try {
                System.out.println("Please input coordinates:");
                System.out.printf(">");
                sc = new Scanner(System.in);
                String input = sc.next();
                String[] inputSplit = input.split(",");

                if(inputSplit.length != COORDINATEDIMENSION){
                    throw new ArrayIndexOutOfBoundsException();
                }

                xCoordinate = Integer.parseInt(inputSplit[0]);
                yCoordinate = Integer.parseInt(inputSplit[1]);

            } catch(ArrayIndexOutOfBoundsException e){
                //e.printStackTrace(System.err);
                System.out.println("Not a coordinate.");
            } catch(NumberFormatException e){
                //e.printStackTrace(System.err);
                System.out.println("Invalid input.");
            } catch(Exception e){
                e.printStackTrace(System.err);
            }
        }
    }

    /**
     * generate events in the world based on specified worldSize
     * @param xAxisRange, yAxisRange, worldSize, listOfEvents
     */
    private static void GenerateEvents(int xAxisRange, int yAxisRange, int[][]worldSize, ArrayList<Event> listOfEvents){
        Boolean isEvent;
        int eventNumber = 0;
        Random rd = new Random();

        for(int i=0;i<xAxisRange;i++){
            for(int j=0;j<yAxisRange;j++){
                //assume probability of an event occurring at each coordinate is 50%.
                isEvent = rd.nextBoolean();
                //if isEvent is true, create an event at this coordinate
                if(isEvent) {
                    eventNumber++;
                    worldSize[i][j] = eventNumber;
                    listOfEvents.add(new Event(eventNumber,MAXNUMBEROFTICKETS,MAXTICKETPRICE));
                }
            }
        }
    }
}
