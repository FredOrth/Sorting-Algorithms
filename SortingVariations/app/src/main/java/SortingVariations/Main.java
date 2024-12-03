package SortingVariations;

import java.util.Scanner;

public class Main {

    
    public static void main(String[] args) {
        // if (args.length < 2) {
        //     System.out.println("Usage: java Main <mergeSortType>");
        //     return;
        // }

        // String sortType = args[0];
        String sortType = "recursive";
        Integer[] unsortedArray = {4, 1, 3, 9, 7};



        Scanner scanner = new Scanner(System.in);

        
        int n = scanner.nextInt();
        Integer[] arr = new Integer[n];
        for(int i = 0; i<n; i++){
            arr[i] = scanner.nextInt();
        }  
        


        Sorter<Integer> sorter = SorterFactory.getSorter(sortType);
        
        sorter.sort(arr);
        
        //int comparisons = sorter.sort(unsortedArray);

        // System.out.println("Sorted Array: " + Arrays.toString(unsortedArray));
        // System.out.println("Comparisons: " + comparisons);
    }

}
