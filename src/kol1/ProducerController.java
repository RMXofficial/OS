package kol1;

import aud1.SemaphoreBiblioteka;

import java.util.concurrent.Semaphore;

public class ProducerController {
    public static int NUM_RUN=50;
    static Semaphore accessBuffer;
    static Semaphore lock;
    static Semaphore canCheck;

    public static void init() {
        accessBuffer=new Semaphore(1);
        lock=new Semaphore(1);
        canCheck=new Semaphore(10);
    }

    public static class Buffer {
        public int numChecks = 0;

        public void produce() {
            System.out.println(" producer is producing ");
        }

        public void check() {
            System.out.println(" controller is checking ");
        }
    }

    public static class Producer extends Thread {
        private final Buffer buffer;
        public Producer(Buffer b){
            this.buffer=b;
        }
        public void execute() throws InterruptedException{
            accessBuffer.acquire();
            this.buffer.produce();
            accessBuffer.release();
        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_RUN; i++) {
                try {
                    execute();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public static class Controller extends Thread{
        private final Buffer buffer;
        public Controller(Buffer b){
            this.buffer=b;
        }
        public void execute() throws InterruptedException{
            lock.acquire();
            if (this.buffer.numChecks==0){
                accessBuffer.acquire();
            }
            this.buffer.numChecks++;
            lock.release();

            canCheck.acquire();
            this.buffer.check();
            lock.acquire();
            this.buffer.numChecks--;
            canCheck.release();
            if (this.buffer.numChecks==0){
                accessBuffer.release();
            }
            lock.release();
        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_RUN; i++) {
                try {
                    execute();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}