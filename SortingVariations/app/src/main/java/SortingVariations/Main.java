package SortingVariations;

import java.util.Scanner;

public class Main {

    
    public static void main(String[] args) {

        // int cutoff = args.length > 1 ? Integer.parseInt(args[1]) : 10;

        //Default cutoff

        String sortType = args[0];
        Scanner scanner = new Scanner(System.in);
        // Integer[] arr = null;
        // int n = 0;

        if(args[1].equals("BaseCase")){
            if(args[2].equals("INTEGERS")){
            Sorter<Integer> sorter = SorterFactory.getSorter(sortType,1,true, false,0);
            while(scanner.hasNext()){
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
        }
            else{
                Sorter<String> sorter = SorterFactory.getSorter(sortType,1, true, false,0);
                while(scanner.hasNext()){
                int n = scanner.nextInt();
                scanner.nextLine();
                String[] arr = scanner.nextLine().split(" ");

                Long start = System.nanoTime();
                int comp = sorter.sort(arr);
                Long end = System.nanoTime();
                System.out.println(n + " " + (end-start)/1_000_000_000 + " " + comp);
                }
                }
            }
        

        else if(args[1].equals("Cutoff")){
            int cutoff = Integer.parseInt(args[3]);
            if(args[2].equals("INTEGERS")){
            Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff, true, false,0);

                int n = scanner.nextInt();
                Integer[] arr = new Integer[n];
                for(int i = 0; i<n; i++){
                    arr[i] = scanner.nextInt();
                }
                    System.out.println(sorter.sort(arr));
            }
        else{
                Sorter<String> sorter = SorterFactory.getSorter(sortType,cutoff,true, false,0);
                    int n = scanner.nextInt();
                    scanner.nextLine();
                    String[] arr = scanner.nextLine().split(" ");
                    System.out.println(sorter.sort(arr));
                }
            
        }

        scanner.close();
    }
}