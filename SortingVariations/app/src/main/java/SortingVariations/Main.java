package SortingVariations;
import java.util.Arrays;
import java.util.Scanner;

import SortingVariations.Util.ObjectClass;
public class Main {
    
    public static void main(String[] args) {
        // int cutoff = args.length > 1 ? Integer.parseInt(args[1]) : 10;
        //Default cutoff
      
       
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
                Integer[] arr = new Integer[n];
                    for(int i = 0; i<n; i++){
                        arr[i] = scanner.nextInt();
                    }
                    Long start = System.nanoTime();
                    int comp = sorter.sort(arr);
                    Long end = System.nanoTime();
                    System.out.println(n + " " + (end-start)/1_000_000_000.0 + " " + comp);
            }
        } else if(args[2].equals("OBJECT")){
            Sorter<ObjectClass> sorter = SorterFactory.getSorter(sortType,0,false,false,0);
            while(scanner.hasNextLine()){
                int n = scanner.nextInt();
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
            else{ //Strings
                Sorter<String> sorter = SorterFactory.getSorter(sortType,0,false,false,0);
                while(scanner.hasNextLine()){ 
                int n = scanner.nextInt();
                int number = 0;
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
                int cutoff = Integer.parseInt(args[4]);
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
                    long durationNs = end - start;
                    double time = (end - start) / 1_000_000_000.0;
                    System.out.println();
                    System.out.printf("%d,%d,%d ns,%d,%d%n", n, presortedness, durationNs, comparisons, cutoff);
                }
                } else {
                int cutoff = Integer.parseInt(args[4]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
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
                    long durationNs = end - start;
                    double time = (end - start) / 1_000_000_000.0;
                    System.out.println();
                    System.out.printf("%d,%d,%d ns,%d,%d%n", n, presortedness, durationNs, comparisons, cutoff);
                            }
                        }
                }
        else if(args[1].equals("Cutoff")){
            if(args[2].equals("INTEGERS")){
                int cutoff = Integer.parseInt(args[3]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,true,false,0);
                    int n = scanner.nextInt();
                    Integer[] arr = new Integer[n];
                        for(int i = 0; i<n; i++){
                            arr[i] = scanner.nextInt();
                        }
                        Long start = System.nanoTime();
                        int comp = sorter.sort(arr);
                        Long end = System.nanoTime();
                        System.out.println(n + " " + (end-start)/1_000_000_000.0 + " " + comp);
            }
            else if(args[2].equals("STRINGS")){
                int cutoff = Integer.parseInt(args[3]);
                Sorter<String> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
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
            else{
                int cutoff = Integer.parseInt(args[4]);
                Sorter<String> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
                    int n = scanner.nextInt();
                    scanner.nextLine();
                    String[] arr = scanner.nextLine().split(" ");
                    System.out.println(sorter.sort(arr));
                }
            }
            scanner.close();
    }
}
