package com.company;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        final int COORDINATEDIMENSION = 2;
        final int XAXISMIN = -10;
        final int XAXISMAX = 10;
        final int YAXISMIN = -10;
        final int YAXISMAX = 10;

        Scanner sc;
        Boolean isRunning = true;
        int xCoordinate = 0;
        int yCoordinate = 0;

        int xAxisRange = XAXISMAX - XAXISMIN + 1;
        int yAxisRange = YAXISMAX - YAXISMIN + 1;
        //worldSize stores the unique numeric identifier for each event
        int[][] worldSize = new int[xAxisRange][yAxisRange];
        //eventDetails stores event data on number of tickets and individual ticket prices
        ArrayList[][] eventDetails;
        int numberOfEvents = 0;
        Boolean isEvent;
        Random rd = new Random();

        //generate events in the world based on specified worldSize
        for(int i=0;i<xAxisRange;i++){
            for(int j=0;j<yAxisRange;j++){
                //assume probability of an event occurring at each coordinate is 50%.
                isEvent = rd.nextBoolean();
                //if isEvent is true, create an event at this coordinate
                if(isEvent) {
                    numberOfEvents++;
                    worldSize[i][j] = numberOfEvents;
                }
            }
        }

        //assume max tickets for each event is 100
        //assume each coordinate can hold a maximum of one event
        //assume max price of each ticket is 100 dollars
        //assume probability of each event occurring at each coordinate is 50%.

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
}
