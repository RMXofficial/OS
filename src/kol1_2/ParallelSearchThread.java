package kol1_2;

public class ParallelSearchThread extends Thread{
    private SharedArray sharedArray;
    private int start;
    private int end;
    private int targetNumber;

    public ParallelSearchThread(SharedArray sharedArray, int start, int end, int targetNumber) {
        this.sharedArray = sharedArray;
        this.start = start;
        this.end = end;
        this.targetNumber = targetNumber;
    }

    @Override
    public void run() {
        int occurances=0;
        int []array=sharedArray.getArray();
        for (int i = start; i < end; i++) {
            if (array[i]==targetNumber)
                occurances++;
        }
        sharedArray.updateOccurances(occurances);
    }
}
