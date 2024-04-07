package kol1_1;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Random rand= new Random();

        int n=10000;
        int [] ar=new int[n];
        for(int i=0;i<n;i++){
            ar[i]= rand.nextInt(150);
        }

        SharedArray sa= new SharedArray(ar,n);      //inicijalizacija na spodelen resurs
        int threads=5;
        sa.setThreadsWorking(threads);

        int searchFor=137;

        ArrayList<ParallelSearchThread> apt= new ArrayList<ParallelSearchThread>();
        int chunk=n/threads;        // golemina na parce od nizata so koe ke raboti sekoj thread

        for(int i=0;i<threads;i++) {
            ParallelSearchThread pt = new ParallelSearchThread(sa, chunk*i , chunk*i+chunk, searchFor);
            // kreiraj i startuvaj nov thread
            apt.add(pt);
            pt.start();
        }

        try {
            System.out.println("Total found: "+sa.getTotalOccurences());    //ova ke ceka site da zavrsat so broenje
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i=0;i<threads;i++) {

            apt.get(i).join();
        }


    }
}

