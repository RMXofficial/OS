package kol1_2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedArray {
    private int[] array;
    private int size;
    private int totalOccurances;
    private int maxOccurances;
    private Semaphore semaphore;
    private Lock lock;

    public SharedArray(int[] array, int size) {
        this.array = array;
        this.size = size;
        this.semaphore=new Semaphore(1);
        this.lock=new ReentrantLock();
    }

    public int getTotalOccurances() {
        return totalOccurances;
    }

    public int getMaxOccurances() {
        return maxOccurances;
    }
    public int[] getArray() {
        return array;
    }

    public int getSize() {
        return size;
    }
    public void updateOccurances(int occurances){
        try {
            semaphore.acquire();
            totalOccurances+=occurances;
            if (occurances>maxOccurances)
                maxOccurances=occurances;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        semaphore.release();
    }
}
