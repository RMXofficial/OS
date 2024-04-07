package kol1_2;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int arraySize=10000;
        int []array=new int[arraySize];
        Random random=new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i]=random.nextInt(100)+1;
        }
        SharedArray sharedArray=new SharedArray(array,arraySize);
        int numThreads=5;
        int targetNumber=36;
        ArrayList<ParallelSearchThread> threads=new ArrayList<>();
        int chunksize=arraySize/numThreads;
        for (int i = 0; i < numThreads; i++) {
            int start=i*chunksize;
            int end;
            if (i==numThreads-1){
                end=arraySize;
            }
            else {
                end=(i+1)*chunksize;
            }
            ParallelSearchThread thread=new ParallelSearchThread(sharedArray,start,end,targetNumber);
            threads.add(thread);
            thread.start();
        }
        for (ParallelSearchThread thread:threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Total occurrences of " + targetNumber + ": " + sharedArray.getTotalOccurances());
        System.out.println("Maximum occurrences found by a thread: " + sharedArray.getMaxOccurances());
    }
}
