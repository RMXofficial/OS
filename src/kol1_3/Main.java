package kol1_3;

import java.util.Random;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        int matrix[][] = new int[1000][1000];
        int target;
        Thread threads[] = new CountOccurrencesAndSumRows[1000];
        Scanner scanner = new Scanner(System.in);
        target = scanner.nextInt();
        SharedMatrix sharedMatrix = new SharedMatrix(matrix,target);
        Random rndm = new Random();
        int biggestNumber = -1;
        // Populate the matrix with random numbers and calculate sum of all elements
        int totalSum = 0;
        int maxSum = Integer.MIN_VALUE;
        int occurrences = 0;
        for (int i = 0; i < 1000; i++) {
            int sumInLine = 0; // Initialize sum for current line
            for (int j = 0; j < 1000; j++) {
                int value = rndm.nextInt(10); // Generate random number
                matrix[i][j] = value; // Assign random number to matrix element

                if (value > biggestNumber) {
                    biggestNumber = value;
                }
                sumInLine += value; // Add value to sum in line

                if (value == target) {
                    occurrences++; // Increment occurrences if value matches target
                }
            }
            maxSum = Math.max(maxSum, sumInLine); // Update maxSum if necessary

        }

        System.out.println("Occurrences of target value: " + occurrences);
        System.out.println("Maximum sum in a line: " + maxSum);
        System.out.println("Biggest Number: " + biggestNumber);
        for (int i = 0; i < 1000; i++) {
            threads[i] = new CountOccurrencesAndSumRows(sharedMatrix,i);
            threads[i].start();
        }
        for (int i = 0; i < 1000; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("A max sum in a line is: " + sharedMatrix.getMaxSum());
        System.out.println("Number of target occurrences: " + sharedMatrix.getCounter());
        System.out.println("Biggest number in the matrix is: " + sharedMatrix.getBiggestNumber());
    }
}