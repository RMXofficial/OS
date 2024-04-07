package kol1_1;

public class ParallelSearchThread extends Thread {

    SharedArray arr;
    int start;
    int end;
    int searching;

    //inicijalizacija
    public ParallelSearchThread(SharedArray arr, int start, int end, int searching){
        this.arr=arr;
        this.start=start;
        this.end=end;
        this.searching=searching;
    }

    @Override
    public void run() {
        super.run();

        // se zema spodelenata niza po referenca
        int [] array=arr.getArray();

        int localCount=0; // lokalen brojach
        for (int i=start;i<end;i++){
            if(array[i]==searching){    //ako sme nasle edno pojavuvanje na brojot
                localCount++;
            }
        }

        arr.threadDone(localCount); // otkako ke zavrsime, signalizirame na spodeleniot resurs

        try {
            if(arr.checkMax()==localCount){ // proverka za dali nie sme go nasle max brojot
                System.out.println("I found the max occurences!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
