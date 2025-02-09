package SortingVariations;
import java.util.Arrays;
import java.util.Scanner;

import SortingVariations.Util.ObjectClass;
public class Main {
    
    public static void main(String[] args) {
       
        String sortType = args[0];
        Scanner scanner = new Scanner(System.in);

        //Horse race
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
                sorterMethod(sorter, reader(args[2]));
            }else if (args[3].equals("Adaptive")) {
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType, 20,true,false,0);
                sorterMethod(sorter, reader(args[2]));
            }else{
                if(args[4].equals("Parrallel")){
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType, 20,false,true,0);
                sorterMethod(sorter, reader(args[2]));
            }
        }
    }
        //Basecase for mergesort
        else if(args[1].equals("BaseCase")){
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,0,false,false,0);
                sorterMethod(sorter, reader(args[2]));
            }
            //Presorted test
        else if(args[1].equals("Presort")) {
            if (args[3].equals("Adaptive")){
                
                int cutoff = Integer.parseInt(args[4]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,true,false,0);
                sorterMethod(sorter, reader(args[2]));
            }else{
                int cutoff = Integer.parseInt(args[4]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,true,false,0);
                sorterMethod(sorter, reader(args[2]));
            }
                }
            //Test for cutoff values
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
            return arr;
        }else if(inputType.equals("OBJECT")){
            int n = scanner.nextInt();
                ObjectClass[] arr = new ObjectClass[n];
                String[] strings = scanner.nextLine().split(" ");
                for(int i = 0; i<n; i++){
                    arr[i] = new ObjectClass(strings[i]);
                }
            return arr;
        }else{
            int n = scanner.nextInt();
            scanner.next();
            String[] arr = scanner.nextLine().split(" ");
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
