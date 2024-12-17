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

        if(args[0].equals("recursiveMergeSort")){
            if(args[1].equals("INTEGERS")){
            Sorter<Integer> sorter = SorterFactory.getSorter(sortType,1,false,0);
            int n = scanner.nextInt();
            Integer[] arr = new Integer[n];
            for(int i = 0; i<n; i++){
                arr[i] = scanner.nextInt();
            }
            System.out.print(sorter.sort(arr));
        }
            else{
                Sorter<String> sorter = SorterFactory.getSorter(sortType,1,false,0);
            int n = scanner.nextInt();
            scanner.nextLine();
            String[] arr = scanner.nextLine().split(" ");
            System.out.print(sorter.sort(arr));
            }
        }

        else if(args[1].equals("cutoff")){
            int cutoff = Integer.parseInt(args[2]);
            Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false,0);
            while(scanner.hasNext()){
                int n = scanner.nextInt();
                Integer[] arr = new Integer[n];
                for(int i = 0; i<n; i++){
                    arr[i] = scanner.nextInt();
                }
                Long start = System.nanoTime();

                Integer comparisons = sorter.sort(arr);

                Long end = scanner.nextLong();
                System.out.println(n + " " + (end-start)/ 1_000_000_000.0 + comparisons);
            }
            
        }

        scanner.close();
    }
}