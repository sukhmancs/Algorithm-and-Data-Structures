import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * Main class for the Ice Sheet program
 * This class contains the main method and is responsible for calling the helper methods
 * to read the data from the file, find the fracture points, and find the crack points.
 *
 * The program is split into two parts:
 * Part A: Find the fracture points
 * Part B: Find the crack points
 *
 * @author Sukhmanjeet Singh, Student ID 000838215
 */
public class Main {
    public static void main(String[] args) {

        // Part A: Find the fracture points
        int[][][] iceSheetsArray = HelperClass.readFileToArray(); // Read the data from the file and store it in a 3D array
        int[][] fracturePoints = HelperClass.findFracturePoints(); // Find the fracture points and store them in a 2D array
        int[] maxFracturePoints = HelperClass.findMaxFracturePointsSheet(fracturePoints);  // Find the ice sheet with the most fracture points

        // Get the number of rows and columns in the fracturePoints array
        int rows = fracturePoints.length;
        int columns = fracturePoints[0].length;
        int totalFracturePoints = rows * columns;

        // Format the fracturePoints array and print the results
        String points = HelperClass.format2DArray(fracturePoints);
        System.out.println("\n\033[1mPart A:\033[0m\n");
        System.out.println(MessageFormat.format("Total fracture points: {0}", totalFracturePoints));
        System.out.println(MessageFormat.format("Ice sheet {0} has the most fracture points: {1}", maxFracturePoints[0] + 1, maxFracturePoints[1]));
        System.out.println("Fracture points:\n\n" + points);
        System.out.println("\n\033[1mPart B: \n");

        // Part B: Find the crack points and print the results
        HelperClass.findCrackPoints(iceSheetsArray, fracturePoints);
    }
}

/**
 * Helper class for the Ice Sheet program
 * This class contains the helper methods to read the data from the file, find the fracture points, and find the crack points.
 * The methods are called from the Main class.
 * The methods are also responsible for formatting the output.
 * The methods are also responsible for printing the results.
 *
 * @author Sukhmanjeet Singh, Student ID 000838215
 */
class HelperClass {

    /**
     * Read the data from the file and store it in a 3D array
     * @return 3D array containing the data from the file
     */
    public static int[][][] readFileToArray() {
        String filePath = "data/ICESHEETS.TXT";  // Path to the file

        // Define 3D array to store data
        int[][][] iceSheets = null;

        // Read the first line of the file to get the dimensions of the array
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // Read the first line of the file to get the dimensions of the array
            String lengthLine = br.readLine();
            int size = Integer.parseInt(lengthLine);

            // Initialize the array
            iceSheets = new int[size][][];

            // Read the rest of the file
            for (int i = 0; i < size; i++) {
                String[] dimensions = br.readLine().split(" ");  // Read the first line of the array
                // Get the dimensions of the array
                int rows = Integer.parseInt(dimensions[0]);
                int cols = Integer.parseInt(dimensions[1]);

                // Initialize the 2D array
                iceSheets[i] = new int[rows][cols];

                // Read the rest of the array
                for (int j = 0; j < rows; j++) {
                    String[] row = br.readLine().split(" "); // Read the row
                    for (int k = 0; k < cols; k++) {      // Loop through the columns
                        iceSheets[i][j][k] = Integer.parseInt(row[k]);
                    }
                }
            }
        } catch (IOException e) {  // Catch any exceptions if the file is not found
            System.out.println("Error reading file: " + e.getMessage());
        }

        return iceSheets; // Return the 3D array
    }

    /**
     * Find the fracture points and store them in a 2D array
     * @return 2D array containing the fracture points
     */
    public static int[][] findFracturePoints() {
        int[][][] iceSheets = readFileToArray();   // Read the data from the file and store it in a 3D array
        int[][] fracturePoints = new int[iceSheets.length][]; // Initialize the 2D array to store the fracture points

        // Define the threshold and the number to divide by
        int htThreshold = 200;
        int divisibleBy = 50;

        // Loop through the ice sheets
        for (int i = 0; i < iceSheets.length; i++) {
            int[][] iceSheet = iceSheets[i];  // Get the current ice sheet

            // Get the number of rows and columns in the current ice sheet
            int rows = iceSheet.length;
            int cols = iceSheet[0].length;

            // Keep track of the fracture points in the current ice sheet
            int[] currentFracturePoints = new int[rows * cols];
            int count = 0; // Keep track of the number of fracture points

            for (int j = 0; j < rows; j++) { // Loop through the rows
                int[] row = iceSheet[j];
                for (int k = 0; k < cols; k++) {  // Loop through the columns
                    int cell = row[k];

                    // Check if the cell is above the threshold and is evenly divisible by 50
                    if (cell >= htThreshold && cell % divisibleBy == 0) {
                        currentFracturePoints[count++] = cell;
                    }
                }
            }

            // Truncate zeros from the currentFracturePoints array
            for (int k = 0; k < currentFracturePoints.length; k++) {
                if (currentFracturePoints[k] == 0) {
                    currentFracturePoints = Arrays.copyOfRange(currentFracturePoints, 0, k);
                    break;  // Break out of the loop once the array is without zeros
                }
            }

            // Initialize the second dimension of fracturePoints
            fracturePoints[i] = new int[currentFracturePoints.length];

            // Copy 1D array to 2D array using System.arraycopy
            System.arraycopy(currentFracturePoints, 0, fracturePoints[i], 0, currentFracturePoints.length);
        }
        return fracturePoints; // Return the array of fracture points
    }

    /**
     * Format the 2D array
     * @param inputArray 2D array to be formatted
     * @return formatted 2D array
     */
    public static String format2DArray(int[][] inputArray) {
        StringBuilder sb = new StringBuilder();  // Initialize StringBuilder
        for (int i = 0; i < inputArray.length; i++) {  // Handle 2D inputArray
            sb.append("Ice Sheet ").append(i + 1).append(":\n");
            sb.append(Arrays.toString(inputArray[i])).append("\n");
        }

        return sb.toString();
    }

    /**
     * Format the 3D array
     * @param inputArray 3D array to be formatted
     * @return formatted 3D array
     */
    public static String format3DArray(int[][][] inputArray) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputArray.length; i++) {  // Handle 3D inputArray
            sb.append("Ice Sheet ").append(i + 1).append(":\n");
            for (int j = 0; j < inputArray[i].length; j++) {
                sb.append(Arrays.toString(inputArray[i][j])).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Find the ice sheet with the most fracture points
     * @param fracturePoints 2D array containing the fracture points
     * @return array containing the index of the ice sheet with the most fracture points and the number of fracture points
     */
    public static int[] findMaxFracturePointsSheet(int[][] fracturePoints) {
        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < fracturePoints.length; i++) {  // Loop through the rows
            if (fracturePoints[i].length > max) {  // Check if the current row has more fracture points than the previous row
                max = fracturePoints[i].length;
                maxIndex = i;
            }
        }
        return new int[]{maxIndex, max};  // Return the index of the ice sheet with the most fracture points and the number of fracture points
    }

    /**
     * Find the crack points and print the results
     * @param iceSheets 3D array containing the data from the file
     * @param fracturePoints 2D array containing the fracture points
     */
    public static void findCrackPoints(int[][][] iceSheets, int[][] fracturePoints) {

        // Initialize variables to store crack points, locations of crack points, and total number of fracture points that lead to a crack
        int[] currentCrackPointsArray = new int[fracturePoints.length];
        String[] currentCrackPointLocations = new String[fracturePoints.length];
        int totalPointsLeadingToCrack = 0;
        int totalFracturePointsCrackPoints = 0;

        for (int i = 0; i < iceSheets.length; i++) { // Loop through the ice sheets
            totalPointsLeadingToCrack = 0;      // Reset totalPointsLeadingToCrack for each ice sheet
            totalFracturePointsCrackPoints = 0; // Reset totalFracturePointsCrackPoints for each ice sheet
            System.out.println(MessageFormat.format("\t\t\t\t    \033[1m\033[3mIce Sheet {0}:\033[0m", i + 1));
            for (int j = 0; j < iceSheets[i].length; j++) {        // Loop through the rows
                for (int k = 0; k < iceSheets[i][j].length; k++) { // Loop through the columns
                    int cell = iceSheets[i][j][k];
                    if (isFracturePoint(fracturePoints, cell)) {   // Make sure it is a fracture point
                        if (iceSheets[i][j][k] != -1) {            // Check that ice sheet value is present
                            Object[] crackPoints = checkAdjacentValues(iceSheets, i, j, k, fracturePoints); // Check crack points for one cell in the ice sheet

                            // Store crack points and locations
                            currentCrackPointsArray = (int[]) crackPoints[0];
                            currentCrackPointLocations = (String[]) crackPoints[1];

                            // Calculate the total number of fracture points that lead to a crack
                            if (currentCrackPointsArray.length > 0) {
                                totalPointsLeadingToCrack++;
                            }

                            // Check if the fracture point is also a crack point
                            if (isFracturePointCrackPoint(fracturePoints, currentCrackPointsArray)) {
                                totalFracturePointsCrackPoints++;
                            }

                            // Print crack points information if crack points exist
                            if (currentCrackPointsArray.length > 0) {
                                // Iterate through crack points and print information
                                for (int x = 0; x < currentCrackPointsArray.length; x++) {
                                    System.out.println(MessageFormat.format("Crack point: {0}", (Object) currentCrackPointsArray[x]));
                                    System.out.println(MessageFormat.format("Crack point location at row x column: {0}", (Object) currentCrackPointLocations[x]));
                                }
                            }
                        }
                    }
                }
            }

            // Display per ice sheet crack information if crack points exist
            if (totalPointsLeadingToCrack > 0) {
                System.out.println(MessageFormat.format("Total number of fracture points that lead to a crack: {0}", totalPointsLeadingToCrack));
                System.out.println(MessageFormat.format("Total number of fracture points that are also crack points: {0}", totalFracturePointsCrackPoints));
                System.out.println(MessageFormat.format("Fraction of fracture points that are also crack points: {0}\n",
                        String.format("%.3f", (double) totalFracturePointsCrackPoints / totalPointsLeadingToCrack)));
            } else {
                System.out.println("No crack points found for this ice sheet.\n");
            }
        }
    }

    /**
     * Check if the fracture point is also a crack point
     * @param fracturePointts 2D array containing the fracture points
     * @param crackPoints array containing the crack points
     * @return true if the fracture point is also a crack point, false otherwise
     */
    public static boolean isFracturePointCrackPoint(int[][] fracturePointts, int[] crackPoints) {
        for (int i = 0; i < fracturePointts.length; i++) {         // Loop through the rows
            for (int j = 0; j < fracturePointts[i].length; j++) {  // Loop through the columns
                for (int k = 0; k < crackPoints.length; k++) {     // Loop through the crack points
                    if (fracturePointts[i][j] == crackPoints[k]) { // Check if the fracture point is also a crack point
                        return true;
                    }
                }
            }
        }
        return false; // Return false if the fracture point is not a crack point
    }

    /**
     * Check if the cell is a fracture point
     * @param fracturePoints 2D array containing the fracture points
     * @param cell cell to be checked
     * @return true if the cell is a fracture point, false otherwise
     */
    public static boolean isFracturePoint(int[][] fracturePoints, int cell) {
        for (int i = 0; i < fracturePoints.length; i++) {        // Loop through the rows
            for (int j = 0; j < fracturePoints[i].length; j++) { // Loop through the columns
                if (cell == fracturePoints[i][j]) { // True if the cell is a fracture point
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check the adjacent values of a cell
     * @param iceSheets 3D array containing the data from the file
     * @param sheet current ice sheet
     * @param row current row
     * @param col current column
     * @param fracturePoints 2D array containing the fracture points
     * @return array containing the crack points and the locations of the crack points
     */
    public static Object[] checkAdjacentValues(
            int[][][] iceSheets,
            int sheet,
            int row,
            int col,
            int[][] fracturePoints) {

        // Initialize adjacent values
        int leftAdjacent = 0;
        int rightAdjacent = 0;
        int topAdjacent = 0;
        int bottomAdjacent = 0;

        // Create arrays to store crack points, crack point locations, and fraction of crack points
        int[] crackPoints = new int[fracturePoints[sheet].length];
        String[] crackPointLocations = new String[fracturePoints[sheet].length];

        // Initialize count
        int count = 0;

        if (col > 0) { // Check if the left adjacent value is not at the left edge of the array
            int shiftedColumn = col - 1;
            leftAdjacent = iceSheets[sheet][row][shiftedColumn];  // Get the left adjacent value

            if (leftAdjacent % 10 == 0) {          // Check if the left adjacent value is a crack point
                crackPoints[count] = leftAdjacent; // Add crack point to crackPoints array
                crackPointLocations[count] = row + " x " + shiftedColumn;
                count++; // Increment count
            }
        }

        if (col < iceSheets[sheet][row].length - 1) {  // Check if the right adjacent value is not at the right edge of the array
            int shiftedColumn = col + 1;
            rightAdjacent = iceSheets[sheet][row][shiftedColumn];

            if (rightAdjacent % 10 == 0) {  // Check if the right adjacent value is a crack point
                crackPoints[count] = rightAdjacent;
                crackPointLocations[count] = row + " x " + shiftedColumn;
                count++;
            }
        }

        if (row > 0) {  // Check if the top adjacent value is not at the top edge of the array
            int shiftedRow = row - 1;
            topAdjacent = iceSheets[sheet][shiftedRow][col];

            if (topAdjacent % 10 == 0) {  // Check if the top adjacent value is a crack point
                crackPoints[count] = topAdjacent;
                crackPointLocations[count] = shiftedRow + " x " + col;
                count++;
            }
        }

        if (row < iceSheets[sheet].length - 1) {  // Check if the bottom adjacent value is not at the bottom edge of the array
            int shiftedRow = row + 1;
            bottomAdjacent = iceSheets[sheet][shiftedRow][col];

            if (bottomAdjacent % 10 == 0) {  // Check if the bottom adjacent value is a crack point
                crackPoints[count] = bottomAdjacent;
                crackPointLocations[count] = shiftedRow + " x " + col;
            }
        }

        // Remove 0 values from crackPoints array
        crackPoints = Arrays.stream(crackPoints).filter(value -> value != 0).toArray();

        // Remove null values from crackPointLocations array
        crackPointLocations = Arrays.stream(crackPointLocations).filter(value -> value != null).toArray(String[]::new);

        return new Object[]{crackPoints, crackPointLocations};  // Return crack points and crack point locations
    }
}
