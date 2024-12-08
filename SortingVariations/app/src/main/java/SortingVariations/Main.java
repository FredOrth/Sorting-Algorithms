package SortingVariations;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    
    public static void main(String[] args) {
        // if (args.length < 2) {
        //     System.out.println("Usage: java Main <mergeSortType>");
        //     return;
        // }

        Integer[] arr1 = {80, 100, 5, 43, 95, 52, 87, 34, 81, 79, 58, 51, 96, 29, 41, 19, 88, 28, 20, 63};
        IterativeMergeSort<Integer> sorter1 = new IterativeMergeSort<>();
        sorter1.sort(arr1);
        
        System.out.println(Arrays.toString(arr1));

        String sortType = args[0].strip();

        int cutoff = args.length > 1 ? Integer.parseInt(args[1]) : 10;
        //String sortType = "recursive";


        Scanner scanner = new Scanner(System.in);
        Integer[] arr = null;
        int n = 0;

        if(args[1].equals("cutoff"))

        // This is just for debugging, run with ./gradlew run --args="recursiveMergeSort 3 debug"
        // If run from python it will be as normal
        if (args.length > 2 && args[2].equalsIgnoreCase("debug")) {
            // Debug mode activated if the third argument is "debug"
            System.out.println("Debug Mode Enabled");
        
            // Simulated input for debugging
            n = 5; // Size of the array
            arr = new Integer[]{4, 1, 3, 9, 7}; // Predefined array
        } else {
        
            n = scanner.nextInt();
            arr = new Integer[n];
            for(int i = 0; i<n; i++){
                arr[i] = scanner.nextInt();
            }  
        }
            

        Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff);
        
        sorter.sort(arr);
        
        //int comparisons = sorter.sort(unsortedArray);

        // System.out.println("Sorted Array: " + Arrays.toString(arr));
        // System.out.println("Comparisons: " + comparisons);
        scanner.close();
    }

}
