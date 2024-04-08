package kol1_3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedMatrix {
    private int biggestNumber;
    private int[][] matrix;
    private int counter;
    private int maxSum;
    private int target;
    Lock lock;

    public SharedMatrix(int[][] matrix, int target) {
        this.matrix = matrix;
        this.target = target;
        lock = new ReentrantLock();
        this.biggestNumber = Integer.MIN_VALUE;
    }

    public int[] getLine(int i) {
        return matrix[i];
    }

    public synchronized void threadDone(int localCounter, int localSum, int localBiggestNumber) {
        safeUpdateCounter(localCounter);
        updateMaxSum(localSum);
        updateBiggestNumber(localBiggestNumber);
    }

    public synchronized void safeUpdateCounter(int localCounter) {
        updateCounter(localCounter);
    }

    public synchronized void updateCounter(int localCounter) {
        this.counter += localCounter;
    }

    public synchronized void updateMaxSum(int localSum) {
        if (this.maxSum < localSum) {
            this.maxSum = localSum;
        }
    }

    public synchronized void updateBiggestNumber(int localBiggestNumber) {
        if (this.biggestNumber < localBiggestNumber) {
            this.biggestNumber = localBiggestNumber;
        }
    }
    public int getTarget() {
        return target;
    }

    public int getBiggestNumber() {
        return biggestNumber;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getCounter() {
        return counter;
    }

    public int getMaxSum() {
        return maxSum;
    }
}