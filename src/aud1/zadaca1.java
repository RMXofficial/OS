package aud1;

public class zadaca1 {
    public static void main(String[] args) throws InterruptedException {
    //    System.out.println("execution into the main class");
        ThreadClass t1 = new ThreadClass("T1");
        ThreadClass t2 = new ThreadClass("T2");
        //t1.run();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("end of the program");
    }
}
class ThreadClass extends Thread{
    String name;
    public ThreadClass (String name){
        this.name=name;
    }
    @Override
    public void run() {
    //    System.out.println("execution into the thread class");
        for (int i = 0; i < 20; i++) {
            System.out.println("Thread "+name+": "+i);
        }
    }
}
