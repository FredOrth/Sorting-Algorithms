package SortingVariations;

import java.util.Arrays;
import java.util.Scanner;
import SortingVariations.Util.StableTestClass;

public class Main {

    
    public static void main(String[] args) {

        // int cutoff = args.length > 1 ? Integer.parseInt(args[1]) : 10;
        int cutoff = 10;
        // //String sortType = "recursive";


        String sortType = args[0];
        Scanner scanner = new Scanner(System.in);
        // Integer[] arr = null;
        // int n = 0;

        if(args[1].equals("cutoff")){
            cutoff = Integer.parseInt(args[2]);
            Integer[] unsortedArray = new Integer[scanner.nextInt()];
            for(int i = 0; i<unsortedArray.length; i++){
                unsortedArray[i] = scanner.nextInt();
            }
            Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false);
            sorter.sort(unsortedArray);
        }

        // This is just for debugging, run with ./gradlew run --args="recursiveMergeSort 3 debug"
        // If run from python it will be as normal
        else if (args.length > 2 && args[2].equalsIgnoreCase("debug")) {
            // Debug mode activated if the third argument is "debug"
            System.out.println("Debug Mode Enabled");
        
            // Simulated input for debugging
            // n = 5; // Size of the array
            // arr = new Integer[]{4, 1, 3, 9, 7}; // Predefined array
        } else {
            Integer[] unsortedArray = new Integer[scanner.nextInt()];
            for(int i = 0; i<unsortedArray.length; i++){
                unsortedArray[i] = scanner.nextInt();
            }
            Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false);
            sorter.sort(unsortedArray);
                }  
            
        
        
            
        
        
        
        // sorter.sort(arr);
        
        

        // System.out.println("Sorted Array: " + Arrays.toString(arr));
        // System.out.println("Comparisons: " + comparisons);
        scanner.close();
    }
}