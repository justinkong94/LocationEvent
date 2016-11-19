package com.company;

import java.util.*;

/**
 * Created by Justin on 18-Nov-16.
 */
class World {
    //world stores the unique numeric identifier for each event
    private int[][] world;
    private int[][] searchedIndex;
    private int XAXISMIN;
    private int YAXISMIN;
    private int MAXNUMBEROFTICKETS;
    private int MAXTICKETPRICE;
    private int xAxisRange;
    private int yAxisRange;
    private ArrayList<Event> listOfEvents;

    World(int xAxisRange, int yAxisRange, int XAXISMIN, int YAXISMIN, int MAXNUMBEROFTICKETS, int MAXTICKETPRICE){
        world = new int[xAxisRange][yAxisRange];
        searchedIndex = new int[xAxisRange][yAxisRange];
        this.XAXISMIN = XAXISMIN;
        this.YAXISMIN = YAXISMIN;
        this.MAXNUMBEROFTICKETS = MAXNUMBEROFTICKETS;
        this.MAXTICKETPRICE = MAXTICKETPRICE;
        this.xAxisRange = xAxisRange;
        this.yAxisRange = yAxisRange;
        listOfEvents = new ArrayList<Event>();
    }

    void AddEvent(int eventNumber, int currentXIndex, int currentYIndex){
        world[currentXIndex][currentYIndex] = eventNumber;
        listOfEvents.add(new Event(eventNumber,XAXISMIN+currentXIndex,YAXISMIN+currentYIndex,MAXNUMBEROFTICKETS,MAXTICKETPRICE));
    }

    int GetCoordinatesData(int xIndex, int yIndex){
        return world[xIndex][yIndex];
    }

    Event GetEvent(int eventNumber){
        return listOfEvents.get(eventNumber-1);
    }

    void SetSearchedIndex(int xIndex, int yIndex){
        searchedIndex[xIndex][yIndex] = 1;
    }

    int GetSearchedIndex(int xIndex, int yIndex){
        return searchedIndex[xIndex][yIndex];
    }

    void ClearSearchedIndex(){
        for(int i=0;i<xAxisRange;i++){
            for(int j=0; j<yAxisRange;j++){
                searchedIndex[i][j] = 0;
            }
        }
    }
}
