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
            Sorter<Integer> sorter = SorterFactory.getSorter(sortType,30,false,false,0);
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
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,30,true,false,0);
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
        
            //String sorted = scanner.nextLine();

        else if(args[1].equals("Cutoff")){
            int cutoff = Integer.parseInt(args[3]);
            if(args[2].equals("INTEGERS")){
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
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
            else if(args[2].equals("STRINGS")){
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
            else if(args[2].equals("PRESORTED")){
            Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
            while(scanner.hasNextLine()){
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());
                // System.out.println(scanner.nextInt());



                String[] strArr = scanner.nextLine().split(" ");
                int n = Integer.parseInt(strArr[0]);
                int sortedNess = Integer.parseInt(strArr[1]);

                Integer[] arr = new Integer[n];
                for(int i = 0; i<n; i++){
                    arr[i] = Integer.parseInt(strArr[i]);
                }

                
                // int n = scanner.nextInt();
                // int presortedNess = scanner.nextInt();
                // Integer[] arr = new Integer[n];

                // for(int i = 0; i<n; i++){
                //     arr[i] = scanner.nextInt();
                // }
                // Long start = System.nanoTime();
                // int comp = sorter.sort(arr);
                // Long end = System.nanoTime();
                // System.out.println(n + " " + (end-start)/1_000_000_000.0 + " " + comp + " " + presortedNess);
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
