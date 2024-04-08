package kol1_3;

public class CountOccurrencesAndSumRows extends Thread {
    SharedMatrix sharedMatrix;
    int[] line;
    int lineNumber;
    int target;

    public CountOccurrencesAndSumRows(SharedMatrix sharedMatrix, int lineNumber) {
        this.sharedMatrix = sharedMatrix;
        this.lineNumber = lineNumber;
        this.line = sharedMatrix.getLine(lineNumber);
        this.target = sharedMatrix.getTarget();
    }

    @Override
    public void run() {
        super.run();
        int localCounter = 0;
        int localSum = 0;
        int localBiggestNumber = Integer.MIN_VALUE;
        for (int currentNumber : line) {
            if (currentNumber == target) localCounter++;
            if (localBiggestNumber < currentNumber) localBiggestNumber = currentNumber;
            localSum += currentNumber;
        }
        sharedMatrix.threadDone(localCounter, localSum,localBiggestNumber);
    }
}