package kol1_1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedArray {
    private int [] array;
    private int size;
    private int totalOccurences;
    private int maxPerThread;
    private int threadsWorking;

    Semaphore semaphore;    // semafor za sunkronizacija na site tredovi da zavrsat

    Lock lock= new ReentrantLock(); // lock za spodelena promenliva

    public SharedArray(int[] array, int size) {
        this.array = array;
        this.size = size;
        //inicijalizacija na niza
    }

    // setirame kolku paralelni nitki ke rabotat
    public void setThreadsWorking(int threadsWorking) {
        this.threadsWorking = threadsWorking;
        semaphore= new Semaphore(0);

    }

    //koga nitkata ke zavrsi so broenje:
    public void threadDone(int threadFound){
        lock.lock();        // zaklucuva spodelena promenliva
        this.totalOccurences+=threadFound;  //inkrementira
        if(threadFound>this.maxPerThread){
            this.maxPerThread=threadFound;  //setira max
        }
        lock.unlock();              //otklucuva spodelena promenliva
        semaphore.release();        // osloboduva eden permit
    }

    public int getTotalOccurences() throws InterruptedException {
        semaphore.acquire(threadsWorking);  // mora da pocekame site nitki da zavrsat
        semaphore.release(threadsWorking);  // osloboduvame pak ist broj na permiti
        return totalOccurences;             // sega sme sigurni deka site zavrsile so broenje
    }

    public int checkMax() throws InterruptedException {
        semaphore.acquire(threadsWorking);  // mora da pocekame site nitki da zavrsat
        semaphore.release(threadsWorking);
        return maxPerThread;
    }

    public int[] getArray() {
        return array;       // nema potreba od zaklucuvanje- nizata ne se menuva
    }
}
