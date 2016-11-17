package com.company;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner sc = null;

        try{
            System.out.println("Please input coordinates:");
            sc = new Scanner(System.in);
            int xCoordinate = sc.nextInt();
        }catch (Exception e) {
            e.printStackTrace(System.err);
            System.out.println("Invalid input");
        }
    }
}
