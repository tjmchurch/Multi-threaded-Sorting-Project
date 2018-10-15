/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project4_church;

import java.util.Queue;

/**
 *
 * @author trent_000
 */
public class Merge extends Thread {

    private int[] arr1;
    private int[] arr2;
    private int[] finalArray;

    public Merge(int[] arr1, int[] arr2) {
        this.arr1 = arr1;
        this.arr2 = arr2;
    }

    public void makeMerge() {
        finalArray = new int[arr1.length + arr2.length];
        int point1 = 0;
        int point2 = 0;
        for (int i = 0; i < finalArray.length; i++) {
            if (point1 == arr1.length) {
                finalArray[i] = arr2[point2];
                point2++;
            } else if (point2 == arr2.length) {
                finalArray[i] = arr1[point1];
                point1++;
            } else if (arr1[point1] > arr2[point2]) {
                finalArray[i] = arr2[point2];
                point2++;
            } else {
                finalArray[i] = arr1[point1];
                point1++;
            }
        }

    }

    public int[] getFinalArray() {
        return finalArray;
    }

   
    

    @Override
    public void run() {
        makeMerge();
    }

}
