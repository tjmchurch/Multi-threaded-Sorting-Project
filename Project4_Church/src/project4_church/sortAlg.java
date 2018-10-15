package project4_church;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author trent_000
 */
public class sortAlg extends Thread {

    private char selection;
    private int[] sA;

    public sortAlg(char selection, int[] sA) {
        this.selection = selection;
        this.sA = sA;
    }

    public int[] getsA() {
        return sA;
    }

    public static void selectionSort(int[] sA) {
        for (int i = 0; i < sA.length; i++) {
            int lowest = i;
            for (int j = i + 1; j < sA.length; j++) {
                if (sA[lowest] > sA[j]) {
                    lowest = j;
                }
            }
            int temp = sA[i];
            sA[i] = sA[lowest];
            sA[lowest] = temp;
        }

    }

    public static void bubbleSort(int[] sA) {

        for (int i = 0; i < sA.length; i++) {
            for (int j = 1; j < sA.length; j++) {
                if (sA[j - 1] > sA[j]) {
                    int temp = sA[j];
                    sA[j] = sA[j - 1];
                    sA[j - 1] = temp;
                }
            }
        }

    }

    public static void insertionSort(int[] sA) {

        for (int i = 1; i < sA.length; i++) {
            int temp = sA[i];
            for (int j = i - 1; j > -1; j--) {
                if (temp < sA[j]) {
                    sA[j + 1] = sA[j];
                    if (j == 0) {
                        sA[0] = temp;
                    }
                } else {
                    sA[j + 1] = temp;
                    break;
                }
            }
        }
    }
    public static void quickSort(int[] sA){
        quickSort(sA, 0,sA.length-1);
    
    
    }
    public static void quickSort(int[] sA, int low, int high) {
        //to start low=0
        //to start high = sA.length-1
        if (sA.length == 0) {
            return;
        }
        if (low >= high) {
            return;
        }
        // pick the pivot
        int middle = low + (high - low) / 2;
        int pivot = sA[middle];
        int temp1 = low;
        int temp2 = high;
        while (temp1 <= temp2) {
            while (sA[temp1] < pivot) {
                temp1++;
            }
            while (sA[temp2] > pivot) {
                temp2--;
            }
            if (temp1 <= temp2) {
                int temp = sA[temp1];
                sA[temp1] = sA[temp2];
                sA[temp2] = temp;
                temp1++;
                temp2--;
            }
        }
        // recursively sort two sub parts
        if (low < temp2) {
            quickSort(sA, low, temp2);
        }
        if (high > temp1) {
            quickSort(sA, temp1, high);
        }
    }

    @Override
    public void run() {
        switch (selection) {
            case 's':
                selectionSort(sA);
                break;
            case 'b':
                bubbleSort(sA);
                break;
            case 'i':
                insertionSort(sA);
                break;
            case 'q':
                quickSort(sA);
                break;
                
        }
    }

}
