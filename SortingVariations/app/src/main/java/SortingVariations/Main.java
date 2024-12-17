package SortingVariations;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    
    public static void main(String[] args) {

        // int cutoff = args.length > 1 ? Integer.parseInt(args[1]) : 10;
        int cutoff = 10;
        // //String sortType = "recursive";

        Integer[] arr = new Integer[]{4,5,6,4,3,2,9,10,11,12,13,6,7,8,9,10,11,12,1,2,3,4,5,6};//4,5,6,4,3,2,9,10,11,12,13,6,7,8,9,10,11,12,
        //4,5,6
        //4,3,2 Merge to 6
        //9,10,11,12,13 merge to 11
        //6,7,8,9,10,11,12 merge to 18
        //1,2,3,4,5,6 no merge 

        BinomialSortIndex<Integer> bio = new BinomialSortIndex<>(3, true);
        bio.sort(arr);
        System.out.println(Arrays.toString(arr));

        Integer[] testingArray1 = new Integer[]{76,3,16,3,1,94,1,6,34,4,4,4,4,4,5};
        //2 = 1
        //5 = 4

        bio.sort(testingArray1);

        System.out.println(Arrays.toString(testingArray1));


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
