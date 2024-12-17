package SortingVariations;

import java.util.Arrays;
import java.util.Scanner;

import SortingVariations.LevelSort.sortMode;
import SortingVariations.Util.StableTestClass;

public class Main {

    
    public static void main(String[] args) {

        // Integer[] arr2 = {11,5,22,23,24,25,1,6,5,4,3,9};
        // BinomialSort<Integer> bio = new BinomialSort<>(5, true);
        // bio.sort(arr2);
        // System.out.println(Arrays.toString(arr2));

        Integer[] testArray1 = { 0, 1, 2, 3, 5, 4, 6, 7 }; //{ 1, 2, 3, 5, 4, 6, 7, 8 }; // Mixed runs  we have a scenario, where left run is {0 -> 5 and right is 6 -> 8}
        Integer[] testArray2 = { 8, 7, 6, 5, 4, 3, 2, 1 }; // Reverse order
        String[] testArray3 = {"f","a","l","k","e","n"}; // a, e, f, k, l, n
        String[] testArray4 = { "n", "l", "a", "k", "e", "f" }; // a, e, f, k, l, n

        // System.out.println("Starting new sorting: testArray1");
        // LevelSort<Integer> sorterAdaptive = new LevelSort<>(LevelSort.sortMode.adaptive, 4);
        // sorterAdaptive.sort(testArray1);
        // System.out.println("ADAPTIVE sorted: " + Arrays.toString(testArray1));
        
        // System.out.println("Starting new sorting: ...");
        // LevelSort<Integer> sorterNonAdaptive = new LevelSort<>(LevelSort.sortMode.nonAdaptive, 4);
        // sorterNonAdaptive.sort(testArray1);
        // System.out.println("NON-ADAPTIVE sorted: " + Arrays.toString(testArray1));
        
        // System.out.println("\n" + //
        //                 "Starting new sorting: testArray2");
        // sorterNonAdaptive.sort(testArray2);
        // System.out.println("NON-ADAPTIVE sorted: " + Arrays.toString(testArray2));
        
        // System.out.println("Starting new sorting: ...");
        // sorterAdaptive.sort(testArray2);
        // System.out.println("ADAPTIVE sorted: " + Arrays.toString(testArray2));

        // System.out.println("\n" + //
        //                 "Starting new sorting: testArray3");
        LevelSort<String> sorterNonAdaptive2 = new LevelSort<>(LevelSort.sortMode.nonAdaptive, 4);
        LevelSortIndex<String> indexSorterNonadaptive = new LevelSortIndex<>(4, false);
        // sorterNonAdaptive2.sort(testArray3);
        // System.out.println("NON-ADAPTIVE sorted: " + Arrays.toString(testArray3));
        
        // System.out.println("Starting new sorting: ...");
        LevelSort<String> sorterAdaptive2 = new LevelSort<>(LevelSort.sortMode.adaptive, 4);
        LevelSortIndex<String> indexSorterAdaptive = new LevelSortIndex<>(4, true);
        // sorterAdaptive2.sort(testArray3);
        // System.out.println("ADAPTIVE sorted: " + Arrays.toString(testArray3));

        // System.out.println("\n" + //
        //                 "Starting new sorting: TestArray4");
        // sorterNonAdaptive2.sort(testArray4);
        // System.out.println("NON-ADAPTIVE sorted: " + Arrays.toString(testArray4));

        // System.out.println("Starting new sorting: ...");
        // sorterAdaptive2.sort(testArray4);
        // System.out.println("ADAPTIVE sorted: " + Arrays.toString(testArray4));

        System.out.println("\n" + "Starting new sorting: testArray1");
        LevelSortIndex<Integer> indexSorterAdaptive2 = new LevelSortIndex<>(4, true);
        indexSorterAdaptive2.sort(testArray1);
        System.out.println("ADAPTIVE sorted: " + Arrays.toString(testArray1));

        System.out.println("\n" + "Starting new sorting: testArray1");
        LevelSortIndex<Integer> indexSorterNonAdaptive2 = new LevelSortIndex<>(4, true);
        indexSorterNonAdaptive2.sort(testArray1);
        System.out.println("NONADAPTIVE sorted: " + Arrays.toString(testArray1));

        System.out.println("\n" + "Starting new sorting: testArray2");
        indexSorterAdaptive2.sort(testArray2);
        System.out.println("ADAPTIVE sorted: " + Arrays.toString(testArray2));

        System.out.println("\n" + "Starting new sorting: testArray2");
        indexSorterNonAdaptive2.sort(testArray2);
        System.out.println("NONADAPTIVE sorted: " + Arrays.toString(testArray2));

        System.out.println("\n" + //
                "Starting new sorting: testArray4");
        indexSorterNonadaptive.sort(testArray4);
        System.out.println("Index-NON-ADAPTIVE sorted: " + Arrays.toString(testArray4));

        System.out.println("Starting new sorting: testArray4");
        indexSorterAdaptive.sort(testArray4);
        System.out.println("Inxed-ADAPTIVE sorted: " + Arrays.toString(testArray4));

        System.out.println("\n" + //
                "Starting new sorting: testArray3");
        indexSorterNonadaptive.sort(testArray3);
        System.out.println("Index-NON-ADAPTIVE sorted: " + Arrays.toString(testArray3));

        System.out.println("Starting new sorting: testArray3");
        indexSorterAdaptive.sort(testArray3);
        System.out.println("Inxed-ADAPTIVE sorted: " + Arrays.toString(testArray3));
    }
}