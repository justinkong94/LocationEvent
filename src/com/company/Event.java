package com.company;

import java.util.*;
import java.math.*;

/**
 * Created by Justin on 17-Nov-16.
 */
class Event {
    private int eventNumber = 0;
    private int xCoordinate = 0;
    private int yCoordinate = 0;
    private int numberOfTickets = 0;
    private ArrayList<Double> ticketPrices = new ArrayList<Double>();

    //constructor
    Event(int eventNumber, int xCoordinate, int yCoordinate, int maxNumberOfTickets, int maxTicketPrice){
        this.eventNumber = eventNumber;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        Random rd = new Random();
        numberOfTickets = rd.nextInt(maxNumberOfTickets);

        for(int j=0;j<numberOfTickets;j++){
            //generates a random int in a 100 times larger range then divide by 100 to obtain ticket price with 2 decimal points
            //ensures that generated ticket is at least 1 cent
            int temp = rd.nextInt(maxTicketPrice * 100) + 1;
            ticketPrices.add((double)temp/100);
        }
    }

    int getEventNumber(){
        return this.eventNumber;
    }

    int getDistance(int inputXCoord, int inputYCoord){
        int distance = 0;
        distance += Math.abs(inputXCoord - xCoordinate);
        distance += Math.abs(inputYCoord - yCoordinate);
        return distance;
    }

    double getCheapestTicket(){
        double cheapestTicket;
        if(numberOfTickets > 0){
            //set first ticket as cheapest to start comparison
            cheapestTicket = ticketPrices.get(0);
        }else{
            //no tickets available
            return -1;
        }
        for(int i=1;i<ticketPrices.size();i++){
            if(ticketPrices.get(i) < cheapestTicket){
                cheapestTicket = ticketPrices.get(i);
            }
        }
        return cheapestTicket;
    }
}
