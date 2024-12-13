package SortingVariations;

import java.util.Arrays;
import java.util.Scanner;

import SortingVariations.Util.StableTestClass;

public class Main {

    
    public static void main(String[] args) {

        // Integer[] arr2 = {11,5,22,23,24,25,1,6,5,4,3,9};
        // BinomialSort<Integer> bio = new BinomialSort<>(5, true);
        // bio.sort(arr2);
        // System.out.println(Arrays.toString(arr2));

        Integer[] testArray1 = { 0, 1, 2, 3, 5, 4, 6, 7 }; //{ 1, 2, 3, 5, 4, 6, 7, 8 }; // Mixed runs  we have a scenario, where left run is {0 -> 5 and right is 6 -> 8}
        Integer[] testArray2 = { 8, 7, 6, 5, 4, 3, 2, 1 }; // Reverse order

        System.out.println("Starting new sorting: ...");
        LevelSort<Integer> sorterAdaptive = new LevelSort<>(LevelSort.sortMode.adaptive, 4);
        sorterAdaptive.sort(testArray1);
        System.out.println("Adaptive sorted: " + Arrays.toString(testArray1));
        
        System.out.println("Starting new sorting: ...");
        LevelSort<Integer> sorterNonAdaptive = new LevelSort<>(LevelSort.sortMode.nonAdaptive, 4);
        sorterNonAdaptive.sort(testArray2);
        System.out.println("Non-Adaptive sorted: " + Arrays.toString(testArray2));
        
        System.out.println("Starting new sorting: ...");
        sorterNonAdaptive.sort(testArray1);
        System.out.println("Non-Adaptive sorted: " + Arrays.toString(testArray1));
        
        System.out.println("Starting new sorting: ...");
        sorterAdaptive.sort(testArray2);
        System.out.println("Adaptive sorted: " + Arrays.toString(testArray2));
    }
}

//         StableTestClass str = new StableTestClass(1, 0);
//         StableTestClass str1 = new StableTestClass(1, 1);
//         StableTestClass str2 = new StableTestClass(1, 2);
//         StableTestClass str3 = new StableTestClass(1, 3);
//         StableTestClass str4 = new StableTestClass(1, 4);
//         StableTestClass str5 = new StableTestClass(1, 5);
//         StableTestClass str6 = new StableTestClass(1, 6);
//         StableTestClass[] arr1 = {str,str1,str2,str3,str4,str5,str6};

//         IterativeMergeSortC<StableTestClass> po = new IterativeMergeSortC<>(1);
//         po.sort(arr1);
//         for(int i = 0; i<arr1.length; i++){
//             System.out.println(arr1[i].getComparable() + " " + arr1[i].getTester());
//         }

//         // // if (args.length < 2) {
//         // //     System.out.println("Usage: java Main <mergeSortType>");
//         // //     return;
//         // // }

//         // Integer[] arr1 = {80, 100, 5, 43, 95, 52, 87, 34, 81, 79, 58, 51, 96, 29, 41, 19, 88, 28, 20, 63};
//         // IterativeMergeSort<Integer> sorter1 = new IterativeMergeSort<>();
//         // sorter1.sort(arr1);
        
//         // System.out.println(Arrays.toString(arr1));

//         String sortType = args[0].strip();

//         int cutoff = args.length > 1 ? Integer.parseInt(args[1]) : 10;
//         // //String sortType = "recursive";


//         Scanner scanner = new Scanner(System.in);
//         // Integer[] arr = null;
//         // int n = 0;

//         if(args[1].equals("cutoff"))

//         // This is just for debugging, run with ./gradlew run --args="recursiveMergeSort 3 debug"
//         // If run from python it will be as normal
//         if (args.length > 2 && args[2].equalsIgnoreCase("debug")) {
//             // Debug mode activated if the third argument is "debug"
//             System.out.println("Debug Mode Enabled");
        
//             // Simulated input for debugging
//             // n = 5; // Size of the array
//             // arr = new Integer[]{4, 1, 3, 9, 7}; // Predefined array
//         } else {
//             Integer[] unsortedArray = new Integer[scanner.nextInt()];
//             for(int i = 0; i<unsortedArray.length; i++){
//                 unsortedArray[i] = scanner.nextInt();
//             }
//             Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff);
//             sorter.sort(unsortedArray);
//                 }  
            
        
        
            
        
        
        
//         // sorter.sort(arr);
        
        

//         // System.out.println("Sorted Array: " + Arrays.toString(arr));
//         // System.out.println("Comparisons: " + comparisons);
//         scanner.close();
//     }

// }
