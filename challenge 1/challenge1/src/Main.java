import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    /**
     * problem statement:
     * Write a Java program to solve this challenge. The program should find all unique triplets in the given
     * array that sum up to the target sum.
     * input:
     * t: the number of test cases.
     * each test case:
     * n s
     * a[0] a[1] ... a[n-1]
     * n: size of the array.
     * s: expected sum.
     * a[i]: the i-th element value in the array.
     * output:
     * for each test case:
     * the number of triplets is x
     * x line where each line is
     * x y z
     * where: x + y + z = s
     * - - - -
     * solution explanation:
     * TODO: explain the solution
     * time complexity: O(n^2)
     * space complexity: O(1)
     **/
    static Scanner scanner;

    static void initializeIOFiles() {
        try {
            scanner = new Scanner(new File("input.txt"));
            System.setOut(new PrintStream("output.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            System.exit(404);
        }
    }

    static void solve(int t) {
        System.out.println("Test case: " + t);
        int numberOfTriplets = 0;
        int n = scanner.nextInt();
        int sum = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        Arrays.sort(a);
        for (int i = 0; i < n - 3; i++) {
            int j = i + 1;
            int k = n - 1;
            while (j != k) {
                long x = a[i] + a[j] + a[k];
                if (x == sum){
                    numberOfTriplets  += 1;
                    System.out.println(a[i] + " " + a[j] + " " + a[k]);
                }
                if (x > sum) k--;
                else j++;
            }
        }
        if(numberOfTriplets == 0){
            System.out.println("No triplet where found");
        }
    }

    public static void main(String[] args) {
        initializeIOFiles();
        int t = scanner.nextInt();
        for (int i = 1; i <= t; i++) {
            solve(i);
        }
    }
}
