package com.company;

import java.util.*;

/**
 * Created by Justin on 17-Nov-16.
 */
class Event {
    private int eventNumber = 0;
    private int numberOfTickets = 0;
    private ArrayList<Double> ticketPrices = new ArrayList<Double>();

    Event(int eventNumber, int maxNumberOfTickets, int maxTicketPrice){
        this.eventNumber = eventNumber;
        Random rd = new Random();
        numberOfTickets = rd.nextInt(maxNumberOfTickets);

        for(int j=0;j<numberOfTickets;j++){
            //generates a random int in a 100 times larger range then divide by 100 to obtain ticket price with 2 decimal points
            //ensures that generated ticket is at least 1 cent
            int temp = rd.nextInt(maxTicketPrice * 100) + 1;
            ticketPrices.add((double)temp/100);
        }
    }

    public double getCheapestTicket(){
        //set first ticket as cheapest to start comparison
        double cheapestTicket = ticketPrices.get(0);
        for(int i=1;i<ticketPrices.size();i++){
            if(ticketPrices.get(i) < cheapestTicket){
                cheapestTicket = ticketPrices.get(i);
            }
        }

        return cheapestTicket;
    }
}
