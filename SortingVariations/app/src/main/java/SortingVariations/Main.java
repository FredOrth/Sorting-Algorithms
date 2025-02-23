package SortingVariations;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

import SortingVariations.Util.ObjectClass;
public class Main {
    
    public static void main(String[] args) throws IOException {
       
        String sortType = args[0];
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        //Horse race
        if(args[1].equals("HorseRace")){
            if(args[3].equals("Arrays.sort")){
                String input;
                while((input = reader.readLine()) != null){
                    int n = Integer.parseInt(input);

                    String[] stringArray = reader.readLine().split(" ");
                    int[] arr = Arrays.stream(stringArray)
                            .mapToInt(Integer::parseInt)
                            .toArray();

                        Long start = System.nanoTime();
                        Arrays.sort(arr);
                        Long end = System.nanoTime();
                        System.out.println(n + " " + (end-start)/1_000_000_000.0);

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
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
                sorterMethod(sorter, reader(args[2]));
            }
                }
            //Test for cutoff values
        else if(args[1].equals("Cutoff")){
                int cutoff = Integer.parseInt(args[3]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
                sorterMethod(sorter, reader(args[2]));
            }

    }


    public static Object[] reader(String inputType) throws IOException {
        // Scanner scanner = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());

        if(inputType.equals("INTEGERS")){
                String[] stringArray = reader.readLine().split(" ");
                return Arrays.stream(stringArray)
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new);

        }else if(inputType.equals("OBJECT")){

                ObjectClass[] arr = new ObjectClass[n];
                String[] strings = reader.readLine().split(" ");
                for(int i = 0; i<n; i++){
                    arr[i] = new ObjectClass(strings[i]);
                }
            return arr;
        }else{

            return reader.readLine().split(" ");

        }
    }

    public static <T extends Comparable<T>> void sorterMethod(Sorter sorter, Object[] arr){
                Long start = System.nanoTime();
                int comp = sorter.sort((Comparable[]) arr);
                Long end = System.nanoTime();
                System.out.println(arr.length + " " + (end-start)/1_000_000_000.0 + " " + comp);
                // System.out.println("Array type: " + arr.getClass().getComponentType());
    }
}
