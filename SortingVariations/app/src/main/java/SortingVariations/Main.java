package SortingVariations;
//import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import SortingVariations.Util.ObjectClass;
public class Main {
    
    public static void main(String[] args) {
        // int cutoff = args.length > 1 ? Integer.parseInt(args[1]) : 10;
        //Default cutoff
        Integer[] input = { 1, 2, 3, 8, 7, 6, 5 }; // Ascending then descending
        LevelSortIndex<Integer> lvlAdaptive = new LevelSortIndex<>(4, true);
        System.out.println("Before Sorting: " + Arrays.toString(input));
        lvlAdaptive.sort(input);
        System.out.println("After Sorting: " + Arrays.toString(input));

        
        String sortType = args[0];
        Scanner scanner = new Scanner(System.in);
        // Integer[] arr = null;
        // int n = 0;
        if(args[1].equals("HorseRace")){
            if(args[3].equals("Arrays.sort")){
                while(scanner.hasNextLine()){
                    int n = scanner.nextInt();
                    scanner.nextLine();
                    Integer[] arr = new Integer[n];
                        for(int i = 0; i<n; i++){
                            arr[i] = scanner.nextInt();
                        }
                        Long start = System.nanoTime();
                        Arrays.sort(arr);
                        Long end = System.nanoTime();
                        System.out.println(n + " " + (end-start)/1_000_000_000.0);
                        scanner.nextLine();
                }
            }else if(args[3].equals("NonAdaptive")){
            Sorter<Integer> sorter = SorterFactory.getSorter(sortType,20,false,false,0);
            while(scanner.hasNextLine()){
                int n = scanner.nextInt();
                scanner.nextLine();
                Integer[] arr = new Integer[n];
                    for(int i = 0; i<n; i++){
                        arr[i] = scanner.nextInt();
                    }
                    Long start = System.nanoTime();
                    sorter.sort(arr);
                    Long end = System.nanoTime();
                    System.out.println(n + " " + (end-start)/1_000_000_000.0);
                    scanner.nextLine();
            }
            }else if (args[3].equals("Adaptive")) {
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType, 20,true,false,0);
                while(scanner.hasNextLine()){
                    int n = scanner.nextInt();
                    scanner.nextLine();
                    Integer[] arr = new Integer[n];
                        for(int i = 0; i<n; i++){
                            arr[i] = scanner.nextInt();
                        }
                        Long start = System.nanoTime();
                        sorter.sort(arr);
                        Long end = System.nanoTime();
                        System.out.println(n + " " + (end-start)/1_000_000_000.0);
                        scanner.nextLine();
            }
            }else{
                if(args[4].equals("Parrallel")){
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType, 20,false,true,0);
                while(scanner.hasNextLine()){
                    int n = scanner.nextInt();
                    scanner.nextLine();
                    Integer[] arr = new Integer[n];
                        for(int i = 0; i<n; i++){
                            arr[i] = scanner.nextInt();
                        }
                        Long start = System.nanoTime();
                        sorter.sort(arr);
                        Long end = System.nanoTime();
                        System.out.println(n + " " + (end-start)/1_000_000_000.0);
                        scanner.nextLine();
            }
            }
        }
    }
        else if(args[1].equals("BaseCase")){
            if(args[2].equals("INTEGERS")){
            Sorter<Integer> sorter = SorterFactory.getSorter(sortType,0,false,false,0);
            while(scanner.hasNextLine()){
                int n = scanner.nextInt();
                scanner.nextLine();
                Integer[] arr = new Integer[n];
                    for(int i = 0; i<n; i++){
                        arr[i] = scanner.nextInt();
                    }
                    Long start = System.nanoTime();
                    int comp = sorter.sort(arr);
                    Long end = System.nanoTime();
                    System.out.println(n + " " + (end-start)/1_000_000_000.0 + " " + comp);
                    scanner.nextLine();
            }
        } else if(args[2].equals("OBJECT")){
            Sorter<ObjectClass> sorter = SorterFactory.getSorter(sortType,0,false,false,0);
            while(scanner.hasNextLine()){
                int n = scanner.nextInt();
                scanner.nextLine();
                ObjectClass[] arr = new ObjectClass[n];
                String[] strings = scanner.nextLine().split(" ");
                for(int i = 0; i<n; i++){
                    arr[i] = new ObjectClass(strings[i]);
                }
                Long start = System.nanoTime();
                int comp = sorter.sort(arr);
                Long end = System.nanoTime();
                System.out.println(n + " " + (end-start)/1_000_000_000.0 + " " + comp);
        }
    }
            else{ // String
                Sorter<String> sorter = SorterFactory.getSorter(sortType,0,false,false,0);
                while(scanner.hasNextLine()){ 
                int n = scanner.nextInt();
                scanner.nextLine();
                String[] arr = scanner.nextLine().split(" ");
                Long start = System.nanoTime();
                int comp = sorter.sort(arr);
                Long end = System.nanoTime();
                System.out.println(n + " " + (end-start)/1_000_000_000.0 + " " + comp);
                }
                }
            }

            //String sorted = scanner.nextLine();
        else if(args[1].equals("PRESORTED")) {
            if (args[3].equals("Adaptive")){
                int cutoff = Integer.parseInt(args[3]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,true,false,0);
                while(scanner.hasNextLine()){
                    int n = Integer.parseInt(scanner.nextLine());
                    int presortedness = Integer.parseInt(scanner.nextLine());
                    String[] strArr = scanner.nextLine().split(" ");

                    Integer[] arr = new Integer[n];
                    for (int i = 0; i < n; i++) {
                        arr[i] = Integer.parseInt(strArr[i]);
                    }

                    long start = System.nanoTime();
                    int comparisons = sorter.sort(arr);
                    long end = System.nanoTime();
                    double time = (end - start) / 1_000_000_000.0;
                    System.out.println();
                    System.out.printf("%d,%d,%.3f,%d,%d,%d%n", n, time, presortedness, comparisons, cutoff);
                }
                } else {
                        int cutoff = Integer.parseInt(args[4]);
                        Sorter<Integer> sorter = SorterFactory.getSorter(sortType, cutoff, true, false, 0);
                        while (scanner.hasNextLine()) {
                            int n = Integer.parseInt(scanner.nextLine());
                            int presortedness = Integer.parseInt(scanner.nextLine());
                            String[] strArr = scanner.nextLine().split(" ");

                            Integer[] arr = new Integer[n];
                            for (int i = 0; i < n; i++) {
                                arr[i] = Integer.parseInt(strArr[i]);
                            }

                            long start = System.nanoTime();
                            int comparisons = sorter.sort(arr);
                            long end = System.nanoTime();
                            double time = (end - start) / 1_000_000_000.0;
                            System.out.printf("%s,%d,%.3f,%d,%d,%d%n", n, time, presortedness, comparisons, cutoff);
                            }
                        }
                }
        else if (args[1].equals("Cutoff")) {
    if (args[2].equals("INTEGERS")) {
        int cutoff = Integer.parseInt(args[3]);
        Sorter<Integer> sorter = SorterFactory.getSorter(sortType, cutoff, false, false, 0);
        while (scanner.hasNextLine()) {
            try {
                if (scanner.hasNextInt()) {
                    int n = scanner.nextInt();
                    Integer[] arr = new Integer[n];
                    for (int i = 0; i < n; i++) {
                        if (scanner.hasNextInt()) {
                            arr[i] = scanner.nextInt();
                        } else {
                            throw new InputMismatchException("Expected integer for array element");
                        }
                    }
                    scanner.nextLine(); // Consume the rest of the line
                    Long start = System.nanoTime();
                    int comp = sorter.sort(arr);
                    Long end = System.nanoTime();
                    System.out.println(n + " " + (end - start) / 1_000_000_000.0 + " " + comp);
                } else {
                    System.out.println("Invalid input. Skipping...");
                    scanner.nextLine(); // Skip invalid input
                }
            } catch (InputMismatchException e) {
                System.out.println("Error processing input: " + e.getMessage());
                scanner.nextLine(); // Clear the invalid input
            }
        }
    } else if (args[2].equals("NonAdaptive")) {
        int cutoff = Integer.parseInt(args[3]);
        Sorter<Integer> sorter = SorterFactory.getSorter(sortType, cutoff, false, false, 0);
        while (scanner.hasNextLine()) {
            try {
                if (scanner.hasNextInt()) {
                    int n = scanner.nextInt();
                    Integer[] arr = new Integer[n];
                    for (int i = 0; i < n; i++) {
                        if (scanner.hasNextInt()) {
                            arr[i] = scanner.nextInt();
                        } else {
                            throw new InputMismatchException("Expected integer for array element");
                        }
                    }
                    scanner.nextLine(); // Consume the rest of the line
                    Long start = System.nanoTime();
                    sorter.sort(arr);
                    Long end = System.nanoTime();
                    System.out.println(n + " " + (end - start) / 1_000_000_000.0);
                } else {
                    System.out.println("Invalid input. Skipping...");
                    scanner.nextLine(); // Skip invalid input
                }
            } catch (InputMismatchException e) {
                System.out.println("Error processing input: " + e.getMessage());
                scanner.nextLine(); // Clear the invalid input
            }
        }
    } else { // Adaptive
        int cutoff = Integer.parseInt(args[3]);
        Sorter<Integer> sorter = SorterFactory.getSorter(sortType, cutoff, true, false, 0);
        while (scanner.hasNextLine()) {
            try {
                if (scanner.hasNextInt()) {
                    int n = scanner.nextInt();
                    Integer[] arr = new Integer[n];
                    for (int i = 0; i < n; i++) {
                        if (scanner.hasNextInt()) {
                            arr[i] = scanner.nextInt();
                        } else {
                            throw new InputMismatchException("Expected integer for array element");
                        }
                    }
                    scanner.nextLine(); // Consume the rest of the line
                    Long start = System.nanoTime();
                    sorter.sort(arr);
                    Long end = System.nanoTime();
                    System.out.println(n + " " + (end - start) / 1_000_000_000.0);
                } else {
                    System.out.println("Invalid input. Skipping...");
                    scanner.nextLine(); // Skip invalid input
                }
            } catch (InputMismatchException e) {
                System.out.println("Error processing input: " + e.getMessage());
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
            }else{
                int cutoff = Integer.parseInt(args[4]);
                Sorter<String> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
                    int n = scanner.nextInt();
                    scanner.nextLine();
                    String[] arr = scanner.nextLine().split(" ");
                    System.out.println(sorter.sort(arr));
                }
                scanner.close();
            }
}
