package kol1;

import java.util.concurrent.Semaphore;

public class SiO2 {
    public static int NUM_RUN=50;
    static Semaphore si;
    static Semaphore o;
    static Semaphore siHere;
    static Semaphore ohere;
    static Semaphore ready;
    public static void init(){
        si=new Semaphore(1);
        o=new Semaphore(2);
        siHere=new Semaphore(0);
        ohere=new Semaphore(0);
        ready=new Semaphore(0);
    }
    public static class Si extends Thread{
        public void bond(){
            System.out.println("Si in now bonding");
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
        public void execute() throws InterruptedException{
            si.acquire();
            siHere.release(2);
            ohere.acquire(2);
            ready.release(2);
            bond();
            si.release();
        }
    }
    public static class O extends Thread{
        public void bond(){
            System.out.println("O in now bonding");
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
        public void execute() throws InterruptedException{
            o.acquire(1);
            siHere.acquire();
            ohere.release();
            ready.acquire();
            bond();
            o.release();
        }
    }
}
