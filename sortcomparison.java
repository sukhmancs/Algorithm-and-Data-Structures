/**
 *
 * a:
 * cSort, fSort, bSort, aSort, eSort, gSort, dSort
 *
 * b:
 * gSort, fSort, aSort, dSort, cSort, bSort, eSort
 *
 * c:
 * The basic instruction set time is lowest for bSort. This does not impact the selection of the fastest algorithm
 * because the basic instruction set time is not the only factor to consider when selecting the fastest algorithm.
 * The number of comparisons and the execution time are also important factors to consider.
 *
 * d:
 * Sort Algorithm Identification
 * Sort Algorithm   Algorithm Name             Big O (time) Big O (space)
 * -----------------------------------------------------------
 * aSort            non-recursive Quicksort    O(N^2)       O(log N)
 * bSort            Selection Sort             O(N^2)       O(1)
 * cSort            Insertion Sort             O(N^2)       O(1)
 * dSort            Merge Sort                 O(N log N)   O(N)
 * eSort            Bubble Sort                O(N log N)   O(log N)
 * fSort            Shell Sort                 O(N log N)   O(log N)
 * gSort            Radix Sort                 O(N + K)     O(N + K)
 *
 *
 * @author Sukhmanjeet Singh, Student ID 000838215
 */

import java.util.Arrays;
import java.util.Random;

/**
 * Main class for Assignment 2
 * This class contains the main method to compare the sorting algorithms and the search algorithms
 * It also contains the printResults method to print the results for a given array size
 *
 * @author Sukhmanjeet Singh, Student ID 000838215
 */
public class Main {
    static long[] executionTimes = new long[7]; // Array to store execution times for each sorting method

    // Add counters for comparisons
    static long[] comparisonCounts = new long[7]; // One for each sorting method

    /**
     * Method to print the results for a given array size
     *
     * @param size
     */
    private static void printResults(int size) {
        System.out.println("Comparison for array size of " + size + " (Averaged over 5 runs)");
        System.out.println("==========================================================");
        System.out.printf("%-10s %-15s %-15s %-10s%n", "Algorithm", "Execution Time", "Compares", "Basic Step");
        System.out.println("----------------------------------------------------------");
        char sortName = 'a';
        for (int i = 0; i < 7; i++) {
            long averageExecutionTime = executionTimes[i] / 5; // Average over 5 runs
            long averageCompares = comparisonCounts[i] / 5; // Average over 5 runs
            double basicStep = Assignment2_Start.calculateBasicStepTime(averageExecutionTime, averageCompares);
            System.out.printf("%-10s %-15s %-15s %-10.1f%n", sortName++, averageExecutionTime, averageCompares, basicStep);
        }
        System.out.println("----------------------------------------------------------");
    }

    /**
     * Compare the search algorithms
     */
    private static void compareSearchAlgorithms() {
        int arraySize = 100000;
        int[] unsortedArray = Assignment2_Start.generateRandomArray(arraySize, 1000);
        int[] sortedArray = Assignment2_Start.copyArray(unsortedArray);

        // Use Radix Sort to sort the array
        Assignment2_Start.gSort(sortedArray);

        // Perform linear and binary searches
        long linearSearchTime = (long) Assignment2_Start.execTime(() -> Assignment2_Start.linearSearch(unsortedArray, -1));
        long binarySearchTime = (long) Assignment2_Start.execTime(() -> Assignment2_Start.binarySearch(sortedArray, -1));
        System.out.printf("For array size of %d:%n", arraySize);
        System.out.printf("Linear search took %d ns%n", linearSearchTime);
        System.out.printf("Binary search took %d ns%n", binarySearchTime);

        // Determine the number of linear searches that would justify sorting the data first
        int numberOfSearches = 1000;
        long timeSaved = linearSearchTime * numberOfSearches - binarySearchTime * numberOfSearches;
        if (timeSaved > 0) { // If time saved is positive
            System.out.printf("Number of Linear Searches needed to justify sorting: %d%n", numberOfSearches);
        } else {  // If time saved is negative
            System.out.printf("Number of Linear Searches needed to justify sorting: %d%n", numberOfSearches);
        }
    }

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        // Part 1 - Compare sorting algorithms
        final int[] SIZE_LIST = new int[]{20, 100, 10000, 50000};
        final int RUNS = 5;
        final int MAX_ALGORITHM = 7;
        final int BOUND = 1000;

        for (int arraySize : SIZE_LIST) { // For each array size
            int[] originalData = Assignment2_Start.generateRandomArray(arraySize, BOUND); // Generate a random array
            for (int i = 0; i < RUNS; i++) {  // For each run
                for (int j = 0; j < MAX_ALGORITHM; j++) {  // For each sorting method
                    int[] tempArray;
                    switch (j) {  // Perform the sorting method
                        case 0: // Quicksort
                            tempArray = Assignment2_Start.copyArray(originalData);
                            executionTimes[j] += (long) Assignment2_Start.execTime(() -> Assignment2_Start.aSort(tempArray));
                            comparisonCounts[j] += Assignment2_Start.aSort(tempArray);
                            break;
                        case 1: // Selection Sort
                            tempArray = Assignment2_Start.copyArray(originalData);
                            executionTimes[j] += (long) Assignment2_Start.execTime(() -> Assignment2_Start.bSort(tempArray));
                            comparisonCounts[j] += Assignment2_Start.bSort(tempArray);
                            break;
                        case 2: // Insertion Sort
                            tempArray = Assignment2_Start.copyArray(originalData);
                            executionTimes[j] += (long) Assignment2_Start.execTime(() -> Assignment2_Start.cSort(tempArray));
                            comparisonCounts[j] += Assignment2_Start.cSort(tempArray);
                            break;
                        case 3: // Merge Sort
                            tempArray = Assignment2_Start.copyArray(originalData);
                            executionTimes[j] += (long) Assignment2_Start.execTime(() -> Assignment2_Start.dSort(tempArray));
                            comparisonCounts[j] += Assignment2_Start.dSort(tempArray);
                            break;
                        case 4: // Bubble Sort
                            tempArray = Assignment2_Start.copyArray(originalData);
                            executionTimes[j] += (long) Assignment2_Start.execTime(() -> Assignment2_Start.eSort(tempArray));
                            comparisonCounts[j] += Assignment2_Start.eSort(tempArray);
                            break;
                        case 5: // Shell Sort
                            tempArray = Assignment2_Start.copyArray(originalData);
                            executionTimes[j] += (long) Assignment2_Start.execTime(() -> Assignment2_Start.fSort(tempArray));
                            comparisonCounts[j] += Assignment2_Start.fSort(tempArray);
                            break;
                        case 6: // Radix Sort
                            tempArray = Assignment2_Start.copyArray(originalData);
                            executionTimes[j] += (long) Assignment2_Start.execTime(() -> Assignment2_Start.gSort(tempArray));
                            comparisonCounts[j] += Assignment2_Start.gSort(tempArray);
                            break;
                    }
                }
            }
            printResults(arraySize); // Print the results for the current array size
        }

        // Part 2 - Compare search algorithms
        compareSearchAlgorithms();
    }
}

/**
 * Helper class for Assignment 2
 * This class contains the sorting algorithms and methods to compare the algorithms
 * It also contains methods to generate random arrays, calculate the basic step time,
 * copy an array and swap elements in an array, methods to calculate the average of a set of numbers,
 * and to calculate the execution time of a sort.
 *
 *
 * @author Sukhmanjeet Singh, Student ID 000838215
 */
class Assignment2_Start {

    static long qSortCompares = 0;  // Left in for comparison purposes only

    /**
     * Method to copy an array
     *
     * @param array the array to copy
     * @return the copied array
     */
    public static int[] copyArray(int[] array) {
        return Arrays.copyOf(array, array.length);
    }

    /**
     * Method to calculate the basic step time
     *
     * @param executionTime the execution time
     * @param comparisons the number of comparisons
     * @return the basic step time
     */
    public static double calculateBasicStepTime(long executionTime, long comparisons) {
        return (double) executionTime / comparisons;
    }

    /**
     * Method to generate a random array of a given size
     *
     * @param size the size of the array
     * @param bound the upper bound for the random numbers
     * @return the generated array
     */
    public static int[] generateRandomArray(int size, int bound) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < array.length; i++) {  // For each element in the array
            array[i] = rand.nextInt(bound);  // Generate a random number within the bound
        }
        return array;
    }

    /**
     * The performance method will run the specified sort method on the array and return the time it took to run
     * @param task - the method to use to sort the array
     * @return - the time it took to run the sort
     */
    public static double execTime(Runnable task) {
        long startTime = System.nanoTime();
        task.run();  // run the sort
        long endTime = System.nanoTime();
        return (endTime - startTime); // To convert convert nanoseconds to microseconds, change to (endTime - startTime) * 1E-6;
    }

    /**
     * avg method - returns the average of the numbers passed in
     * @param nums - the numbers to average
     * @return - the average of the numbers
     */
    public static double avg(double... nums) {
        return Arrays.stream(nums).average().orElseThrow();
    }

    /**
     * The swap method swaps the contents of two elements in an int array.
     *
     * @param array where elements are to be swapped.
     * @param a The subscript of the first element.
     * @param b The subscript of the second element.
     */
    public static void swap(int[] array, int a, int b) {
        int temp;

        temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /**
     * The eSort method uses the Bubble Sort algorithm to sort an int array.
     *
     * @param array The array to sort.
     * @return The number of comparisons made in the sort
     */
    public static long eSort(int[] array) {
        int compCounter = 0; // Counter for comparisons
        int lastPos;     // Position of last element to compare
        int index;       // Index of an element to compare

        // The outer loop positions lastPos at the last element
        // to compare during each pass through the array. Initially
        // lastPos is the index of the last element in the array.
        // During each iteration, it is decreased by one.
        for (lastPos = array.length - 1; lastPos >= 0; lastPos--) {
            // The inner loop steps through the array, comparing
            // each element with its neighbor. All of the elements
            // from index 0 thrugh lastPos are involved in the
            // comparison. If two elements are out of order, they
            // are swapped.
            for (index = 0; index <= lastPos - 1; index++) {
                // Compare an element with its neighbor.
                if (array[index] > array[index + 1]) {
                    // Swap the two elements.

                    swap(array, index, index + 1);
                }
                compCounter++; // Increment the counter
            }
        }
        return compCounter;
    }

    /** The cSort method uses the Insertion Sort algorithm to sort an int array.
     *
     * @param array The array to sort.
     * @return The number of comparisons made in the sort
     */
    public static long cSort(int[] array) {
        int compCounter = 0; // Counter for comparisons
        int unsortedValue;  // The first unsorted value
        int scan;           // Used to scan the array

        // The outer loop steps the index variable through
        // each subscript in the array, starting at 1. The portion of
        // the array containing element 0  by itself is already sorted.
        for (int index = 1; index < array.length; index++) {
            // The first element outside the sorted portion is
            // array[index]. Store the value of this element
            // in unsortedValue.
            unsortedValue = array[index];

            // Start scan at the subscript of the first element
            // outside the sorted part.
            scan = index;

            // Move the first element in the still unsorted part
            // into its proper position within the sorted part.
            while (scan > 0 && array[scan - 1] > unsortedValue) {
                array[scan] = array[scan - 1];  // Shift the larger value to the right
                scan--;         // Move to the next element
                compCounter++;  // Increment the counter
            }

            compCounter++; // Increment the counter

            // Insert the unsorted value in its proper position
            // within the sorted subset.
            array[scan] = unsortedValue;
        }
        return compCounter;
    }

    /**
     * The bSort method uses the Selection Sort algorithm to sort an int array.
     *
     * @param array The array to sort.
     * @return The number of comparisons made in the sort
     */
    public static long bSort(int[] array) {
        int compCounter = 0; // Counter for comparisons
        int startScan;   // Starting position of the scan
        int index;       // To hold a subscript value
        int minIndex;    // Element with smallest value in the scan
        int minValue;    // The smallest value found in the scan

        // The outer loop iterates once for each element in the
        // array. The startScan variable marks the position where
        // the scan should begin.
        for (startScan = 0; startScan < (array.length - 1); startScan++) {
            // Assume the first element in the scannable area
            // is the smallest value.
            minIndex = startScan;
            minValue = array[startScan];

            // Scan the array, starting at the 2nd element in
            // the scannable area. We are looking for the smallest
            // value in the scannable area.
            for (index = startScan + 1; index < array.length; index++) {
                if (array[index] < minValue) {
                    minValue = array[index];
                    minIndex = index;
                }
                compCounter++; // Increment the counter
            }

            // Swap the element with the smallest value
            // with the first element in the scannable area.
            array[minIndex] = array[startScan];
            array[startScan] = minValue;
        }
        return compCounter;
    }

    /**
     * The fSort method uses the Shell Sort algorithm to sort an int array.
     *
     * @param array The array to sort.
     * @return The number of comparisons made in the sort
     */
    public static long fSort(int array[]) {
        int compCounter = 0; // Counter for comparisons
        int n = array.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int key = array[i];
                int j = i;
                while (j >= gap && array[j - gap] > key) {
                    array[j] = array[j - gap];  // Shift the larger value to the right
                    j -= gap;
                    compCounter++; // Increment the counter
                }
                compCounter++; // Increment the counter even if the while loop is not entered
                array[j] = key;  // Insert the unsorted value in its proper position
            }
        }
        return compCounter;
    }

    /**
     * The gSort method uses the Radix Sort algorithm to sort an int array.
     *
     * @param array The array to sort.
     * @return The number of comparisons made in the sort
     */
    public static long gSort(int array[]) {
        int compCounter = 0; // Counter for comparisons
        int count = 0;

        int min = array[0];
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) // Find the minimum value
                min = array[i];
            else if (array[i] > max)  // Find the maximum value
                max = array[i];

            compCounter += 2; // Count comparisons when updating the min and max
        }
        int b[] = new int[max - min + 1];
        for (int i = 0; i < array.length; i++) {
            b[array[i] - min]++;
            compCounter++; // Count comparisons in array indexing
        }

        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i]; j++) {
                array[count++] = i + min;
                compCounter++; // Count comparisons when updating the array
            }
        }

        return compCounter;
    }


    /**
     * The non-recursive Quicksort - manages first call
     *
     * @param array an unsorted array that will be sorted upon method completion
     * @return the number of comparisons made in the sort
     */
    public static long aSort(int array[]) {
        qSortCompares = 0;
        return doASort(array, 0, array.length - 1, 0);
    }

    /**
     * The doQuickSort method uses the QuickSort algorithm to sort an int array.
     *
     * @param array The array to sort.
     * @param start The starting subscript of the list to sort
     * @param end The ending subscript of the list to sort
     * @param numberOfCompares The number of comparisons made in the sort (set to 0 on initial call)
     * @return The number of comparisons made in the sort
     */
    private static long doASort(int array[], int start, int end, long numberOfCompares) {
        int pivotPoint;

        if (start < end) {
            // Get the pivot point.
            pivotPoint = part1(array, start, end, numberOfCompares);
            // Note - only one +/=
            numberOfCompares += (end - start);
            // Sort the first sub list.
            numberOfCompares = doASort(array, start, pivotPoint - 1, numberOfCompares);

            // Sort the second sub list.
            numberOfCompares = doASort(array, pivotPoint + 1, end, numberOfCompares);
        }
        return numberOfCompares;
    }

    /**
     * The partition method selects a pivot value in an array and arranges the
     * array into two sub lists. All the values less than the pivot will be
     * stored in the left sub list and all the values greater than or equal to
     * the pivot will be stored in the right sub list.
     *
     * @param array The array to partition.
     * @param start The starting subscript of the area to partition.
     * @param end The ending subscript of the area to partition.
     * @return The subscript of the pivot value.
     */
    private static int part1(int array[], int start, int end, long numberOfCompares) {
        int pivotValue;    // To hold the pivot value
        int endOfLeftList; // Last element in the left sub list.
        int mid;           // To hold the mid-point subscript

        // see http://www.cs.cmu.edu/~fp/courses/15122-s11/lectures/08-qsort.pdf
        // for discussion of middle point - This improves the almost sorted cases
        // of using quicksort
        // Find the subscript of the middle element.
        // This will be our pivot value.
        mid = (start + end) / 2;
        // mid = start;

        // Swap the middle element with the first element.
        // This moves the pivot value to the start of
        // the list.
        swap(array, start, mid);

        // Save the pivot value for comparisons.
        pivotValue = array[start];

        // For now, the end of the left sub list is
        // the first element.
        endOfLeftList = start;

        // Scan the entire list and move any values that
        // are less than the pivot value to the left
        // sub list.
        for (int scan = start + 1; scan <= end; scan++) {
            qSortCompares++;
            if (array[scan] < pivotValue) {
                endOfLeftList++;
                swap(array, endOfLeftList, scan);
            }
            numberOfCompares++;
        }

        // Move the pivot value to end of the
        // left sub list.
        swap(array, start, endOfLeftList);

        // Return the subscript of the pivot value.
        return endOfLeftList;
    }

    /**
     * The dSort method uses the Merge Sort algorithm to sort an int array.
     *
     * @param inputArray The array to sort.
     * @return The number of comparisons made in the sort
     */
    public static long dSort(int inputArray[]) {
        int length = inputArray.length;
        // Create array only once for merging
        int[] workingArray = new int[inputArray.length];
        long count = 0;
        count = doDSort(inputArray, workingArray, 0, length - 1, count); // Call the recursive method
        return count;
    }

    /**
     * The doDSort method uses the Merge Sort algorithm to sort an int array.
     *
     * @param inputArray The array to sort.
     * @param workingArray The array to use for working storage
     * @param lowerIndex The starting subscript of the list to sort
     * @param higherIndex The ending subscript of the list to sort
     * @param count The number of comparisons made in the sort (set to 0 on initial call)
     * @return The number of comparisons made in the sort
     */
    private static long doDSort(int[] inputArray, int[] workingArray, int lowerIndex, int higherIndex, long count) {
        if (lowerIndex < higherIndex) {  // If the lower index is less than the higher index
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // Below step sorts the left side of the array
            count = doDSort(inputArray, workingArray, lowerIndex, middle, count+1);
            // Below step sorts the right side of the array
            count = doDSort(inputArray, workingArray, middle + 1, higherIndex, count+1);
            // Now merge both sides
            count = part2(inputArray, workingArray, lowerIndex, middle, higherIndex, count);
        }
        return count;
    }

    /**
     * The part2 method merges two sub arrays of inputArray[].
     *
     * @param inputArray The array to sort.
     * @param workingArray The array to use for working storage
     * @param lowerIndex The starting subscript of the list to sort
     * @param middle The middle of the array
     * @param higherIndex The ending subscript of the list to sort
     * @param count The number of comparisons made in the sort
     * @return The number of comparisons made in the sort
     */
    private static long part2(int[] inputArray, int[] workingArray, int lowerIndex, int middle, int higherIndex, long count) {

        for (int i = lowerIndex; i <= higherIndex; i++) { // Copy both parts into the workingArray
            workingArray[i] = inputArray[i];  // Copy the array to the working array
        }
        int i1 = lowerIndex;
        int i2 = middle + 1;
        int newIndex = lowerIndex;
        while (i1 <= middle && i2 <= higherIndex) {     // Merge the two halves
            if (workingArray[i1] <= workingArray[i2]) { // If the left half is less than the right half
                inputArray[newIndex] = workingArray[i1];
                i1++;
            } else {  // If the right half is less than the left half
                inputArray[newIndex] = workingArray[i2];
                i2++;
            }
            count++;
            newIndex++;
        }
        while (i1 <= middle) {   // If there are any remaining elements in the left half
            inputArray[newIndex] = workingArray[i1];  // Copy the remaining elements
            newIndex++;
            i1++;
            count++;
        }
        return count;  // Return the number of comparisons made
    }

    /**
     * A demonstration of recursive counting in a Binary Search
     * @param array - array to search
     * @param low - the low index - set to 0 to search whiole array
     * @param high - set to length of array to search whole array
     * @param value - item to search for
     * @param count - Used in recursion to accumulate the number of comparisons made (set to 0 on initial call)
     * @return
     */
    private static int[] binarySearchR(int[] array, int low, int high, int value, int count)
    {
        int middle;     // Mid point of search

        // Test for the base case where the value is not found.
        if (low > high)
            return new int[] {-1,count};

        // Calculate the middle position.
        middle = (low + high) / 2;

        // Search for the value.
        if (array[middle] == value)
            // Found match return the index
            return new int[] {middle, count};
        else if (value > array[middle])
            // Recursive method call here (Upper half of remaining data)
            return binarySearchR(array, middle + 1, high, value, count+1);
        else
            // Recursive method call here (Lower half of remaining data)
            return binarySearchR(array, low, middle - 1, value, count+1);
    }

    /**
     * The linearSearch method uses the Linear Search algorithm to search for a value in an int array.
     *
     * @param array The array to search.
     * @param value The value to search for.
     * @return The index of the value in the array, or -1 if the value is not found
     */
    public static int linearSearch(int[] array, int value) {
        int index = 0;
        // Continue to search the array until the value is found or the end of the array is reached
        while (index < array.length && array[index] != value) {
            index++;
        }
        return (index == array.length) ? -1 : index; // Return the index of the value or -1 if the value is not found
    }

    /**
     * The binarySearch method uses the Binary Search algorithm to search for a value in an int array.
     *
     * @param array The array to search.
     * @param value The value to search for.
     * @return The index of the value in the array, or -1 if the value is not found
     */
    public static int binarySearch(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;
        int middle;
        int position = -1;
        while (low <= high) { // While there is still data to search
            middle = (low + high) / 2;     // Calculate the middle position
            if (array[middle] == value) {  // If the value is found
                position = middle;
                break;
            } else if (array[middle] < value) {  // If the value is in the upper half
                low = middle + 1;
            } else {  // If the value is in the lower half
                high = middle - 1;
            }
        }
        return position; // Return the position of the value
    }
}