package kol1;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MatrixSearch {
    private static final int MATRIX_ROWS = 100;
    private static final int MATRIX_COLS = 100;
    private static final int SEARCH_TARGET = 54;
    private static final int NUM_THREADS = 5;

    private static int[][] matrix = new int[MATRIX_ROWS][MATRIX_COLS];
    private static int[] counts = new int[NUM_THREADS];

    private static Semaphore semaphore = new Semaphore(1);
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        initializeMatrix();
        Thread[] threads = new Thread[NUM_THREADS];

        int chunkSize = MATRIX_ROWS / NUM_THREADS;
        for (int i = 0; i < NUM_THREADS; i++) {
            final int startRow = i * chunkSize;
            final int endRow = (i == NUM_THREADS - 1) ? MATRIX_ROWS : (i + 1) * chunkSize;
            threads[i] = new Thread(() -> searchRows(startRow, endRow));
            threads[i].start();
        }

        int maxCount = Integer.MIN_VALUE;
        int maxCountThread = -1;

        for (int i = 0; i < NUM_THREADS; i++) {
            try {
                threads[i].join();
                System.out.println("Thread " + i + " found " + counts[i] + " occurrences.");
                if (counts[i] > maxCount) {
                    maxCount = counts[i];
                    maxCountThread = i;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Thread " + maxCountThread + " found the highest number of occurrences: " + maxCount);
    }

    private static void initializeMatrix() {
        Random random = new Random();
        for (int i = 0; i < MATRIX_ROWS; i++) {
            for (int j = 0; j < MATRIX_COLS; j++) {
                matrix[i][j] = random.nextInt(100) + 1; // Random number between 1 and 100
            }
        }
    }

    private static void searchRows(int startRow, int endRow) {
        int threadId = (int) Thread.currentThread().getId() % NUM_THREADS;
        int count = 0;
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < MATRIX_COLS; j++) {
                if (matrix[i][j] == SEARCH_TARGET) {
                    count++;
                }
            }
        }

        try {
            semaphore.acquire();
            counts[threadId] = count;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
