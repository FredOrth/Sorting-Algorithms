package SortingVariations;
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
        if(args[1].equals("BaseCase")){
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
        }
        else if(args[0].equals("OBJECT")){
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
            else{
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
        
        else if(args[1].equals("Cutoff")){
            int cutoff = Integer.parseInt(args[3]);
            if(args[2].equals("INTEGERS")){
            Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
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
