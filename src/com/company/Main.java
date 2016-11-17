package com.company;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        final int COORDINATEDIMENSION = 2;

        Scanner sc;
        Boolean isRunning = true;
        String input;
        String[] inputSplit;
        int xCoordinate = 0;
        int yCoordinate = 0;

        while(isRunning) {
            try {
                System.out.println("Please input coordinates:");
                System.out.printf(">");
                sc = new Scanner(System.in);
                input = sc.next();
                inputSplit = input.split(",");

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
