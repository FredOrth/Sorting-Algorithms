package SortingVariations;

import java.util.Arrays;

public class Main {

    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main <mergeSortType>");
            return;
        }

        String sortType = args[0];
        Integer[] unsortedArray = {4, 1, 3, 9, 7};

        Sorter<Integer> sorter = SorterFactory.getSorter(sortType);
        
        sorter.sort(unsortedArray);
        //int comparisons = sorter.sort(unsortedArray);

        System.out.println("Sorted Array: " + Arrays.toString(unsortedArray));
        // System.out.println("Comparisons: " + comparisons);
    }

}
