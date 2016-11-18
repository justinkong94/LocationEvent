package com.company;

/**
 * Created by Justin on 18-Nov-16.
 */
class Node {
    private int xIndex;
    private int yIndex;

    Node(int xIndex, int yIndex){
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }

    int getXIndex(){
        return xIndex;
    }

    int getYIndex(){
        return yIndex;
    }
}
