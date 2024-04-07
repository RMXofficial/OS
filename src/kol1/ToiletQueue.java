package kol1;

import java.util.concurrent.Semaphore;

public class ToiletQueue {
    public class Toilet{
        public void vlezi(){
            System.out.println("vleguva ");
        }
        public void izlezi(){
            System.out.println("izleguva ");
        }
    }
    static Semaphore toilets;
    static Semaphore mLock;
    static Semaphore wLock;
    static int numM;
    static int numW;
    public static void init(){
        toilets=new Semaphore(1);
        mLock=new Semaphore(1);
        wLock=new Semaphore(1);

    }
    public static class Man extends Thread{
        private Toilet toilet;
        public Man(Toilet toilet){
            this.toilet=toilet;
        }
        public void enter() throws InterruptedException{
            mLock.acquire();
            if (numM==0){
                toilets.acquire();
            }
            numM++;
            this.toilet.vlezi();
            mLock.release();
        }
        public void exit() throws InterruptedException {
            mLock.acquire();
            this.toilet.izlezi();
            numM--;
            if (numM==0){
                toilets.release();
            }
            mLock.release();
        }

        @Override
        public void run() {
            super.run();
        }
    }
    public static class Woman extends Thread{
        private Toilet toilet;
        public Woman(Toilet toilet){
            this.toilet=toilet;
        }
        public void enter() throws InterruptedException{
            wLock.acquire();
            if (numW==0){
                toilets.acquire();
            }
            numW++;
            this.toilet.vlezi();
            wLock.release();
        }
        public void exit() throws InterruptedException {
            wLock.acquire();
            this.toilet.izlezi();
            numW--;
            if (numW==0){
                toilets.release();
            }
            wLock.release();
        }
    }
}
