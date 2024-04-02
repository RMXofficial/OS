package aud1;

public class zadaca1 {
    public static void main(String[] args) {
        System.out.println("execution into the main class");
        ThreadClass t1 = new ThreadClass();
        //t1.run();
        t1.start();
    }
}
class ThreadClass extends Thread{
    @Override
    public void run() {
        System.out.println("execution into the thread class");
    }
}
