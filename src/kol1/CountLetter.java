package kol1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class CountLetter {
    private int count=0;
    private Semaphore semaphore;
    public void init(){
        semaphore=new Semaphore(1);
    }
    class Counter extends Thread{
        private String data;

        public Counter(String data) {
            this.data = data;
        }
        public void count(String data){
            if (data.equals("A")){
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count++;
                semaphore.release();
            }
        }

        @Override
        public void run() {
            count(data);
        }
    }
    public void start(){
        init();
        HashSet<Thread> threads=new HashSet<>();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        try {
            String input=bufferedReader.readLine();
            String []data=input.split("");
            for (String ch: data){
                Counter c=new Counter(ch);
                threads.add(c);
            }
            for (Thread t : threads) {
                t.start();
            }

            for (Thread t : threads) {
                t.join();
            }
            System.out.println("Total count of 'A': " + count);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        CountLetter countLetter=new CountLetter();
        countLetter.start();
    }
}