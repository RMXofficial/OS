package aud1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class zadaca1 {
    public static void main(String[] args) throws InterruptedException {
        //    System.out.println("execution into the main class");
        Incrementor incrementor1 = new Incrementor();
        Incrementor incrementor2=new Incrementor();
        ThreadClass t1 = new ThreadClass("T1",incrementor1);
        ThreadClass t2 = new ThreadClass("T2",incrementor2);
        //t1.run();
        t1.start();
        t2.start();

        t1.join();
        t2.join();

//        if (t1.isAlive() && t2.isAlive()){
//            System.out.println("thread is still alive");
//            t1.interrupt();
//            t2.interrupt();
//        }
        System.out.println(incrementor1.getCount());
        System.out.println(incrementor2.getCount());
    }
}

class ThreadClass extends Thread {
    String name;
    Incrementor incrementor;

    public ThreadClass(String name, Incrementor incrementor) {
        this.name = name;
        this.incrementor=incrementor;
    }

    @Override
    public void run() {
        //    System.out.println("execution into the thread class");
        for (int i = 0; i < 200; i++) {
//            System.out.println("Thread " + name + ": " + i);
            try {
                incrementor.safeSemaphoreIncrement();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Incrementor {
    private static int count = 0;
    private static Lock lock = new ReentrantLock();
    private static Semaphore semaphore = new Semaphore(2);
    public static void unsafeIncrement() throws InterruptedException {
        count++;
        Thread.sleep(1);
    }
    public synchronized void safeIncrement(){
        count++;
    }
    public static void safeMutexIncrement(){
        lock.lock();
        count++;
        lock.unlock();
    }
    public static void safeSemaphoreIncrement() throws InterruptedException {
        semaphore.acquire();
        count++;
        semaphore.release();
    }
    public static int getCount(){
        return count;
    }
}
