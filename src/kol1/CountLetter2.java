package kol1;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class CountLetter2 {

    /**
     * Promenlivata koja treba da go sodrzi brojot na pojavuvanja na bukvata A
     */
    private int count = 0;
    /**
     * Semaphore for synchronization
     */
    private Semaphore semaphore;

    public void init() {
        semaphore = new Semaphore(1);
    }

    class Counter extends Thread {
        private String data;

        public Counter(String data) {
            this.data = data;
        }

        public void count(String data) throws InterruptedException {
            if (data.equals("A")) {
                semaphore.acquire();
                count++;
                semaphore.release();
            }
        }

        @Override
        public void run() {
            try {
                count(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            CountLetter2 environment = new CountLetter2();
            environment.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void start() throws Exception {
        init();

        HashSet<Thread> threads = new HashSet<>();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Enter a string: ");
            String input = bf.readLine();

            String[] data = input.split("");

            for (String ch : data) {
                Counter c = new Counter(ch);
                threads.add(c);
            }

            for (Thread t : threads) {
                t.start();
            }

            for (Thread t : threads) {
                t.join();
            }

            System.out.println("Total count of 'A': " + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
