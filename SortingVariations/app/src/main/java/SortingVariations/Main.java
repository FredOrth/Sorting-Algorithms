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
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,0,false,false,0);
                sorterMethod(sorter, reader(args[2]));
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
                int cutoff = Integer.parseInt(args[3]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
                sorterMethod(sorter, reader(args[2]));
            }
            scanner.close();
    }






    public static Object[] reader(String inputType){
        Scanner scanner = new Scanner(System.in);

        if(inputType.equals("INTEGERS")){
            int n = scanner.nextInt();
                Integer[] arr = new Integer[n];
                    for(int i = 0; i<n; i++){
                        arr[i] = scanner.nextInt();
                    }
            // System.out.println("This is n: " + n + " this is array length" + arr.length);
            return arr;
        }else if(inputType.equals("OBJECT")){
            int n = scanner.nextInt();
                ObjectClass[] arr = new ObjectClass[n];
                String[] strings = scanner.nextLine().split(" ");
                for(int i = 0; i<n; i++){
                    arr[i] = new ObjectClass(strings[i]);
                }
            System.out.println("This is n: " + n + " this is array length" + arr.length);
            return arr;
        }else{
            int n = scanner.nextInt();
            scanner.next();
            String[] arr = scanner.nextLine().split(" ");
            System.out.println("This is n: " + n + " this is array length" + arr.length);
            return arr;
        }
    }

    public static <T extends Comparable<T>> void sorterMethod(Sorter sorter, Object[] arr){
            Long start = System.nanoTime();
                int comp = sorter.sort((Comparable[]) arr);
                Long end = System.nanoTime();
                System.out.println("Array type: " + arr.getClass().getComponentType());
                System.out.println(arr.length + " " + (end-start)/1_000_000_000.0 + " " + comp);
    }
}
