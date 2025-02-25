package SortingVariations;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

import SortingVariations.Util.ObjectClass;
public class Main {
    
    public static void main(String[] args) throws IOException {

        // Integer[] arr2 = new Integer[]{11,27,10,18,12,5,7,12,23,24,19,29,6,28,4,28,3,16,15,10,14,5,25,8,22,19,6,22,28,30
        // };
        Random random = new Random();
        for(int j = 0; j<10; j++){
        Integer[] arr2 = new Integer[10000000];
        System.out.println(j);
        for(int i = 0; i<arr2.length; i++){
            arr2[i] = random.nextInt();
        }
        LevelSortIndex<Integer> sort = new LevelSortIndex<>(j, true);
        System.out.println(sort.sort(arr2));}
        System.out.println("Done");
       
        String sortType = args[0];
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        //Horse race
        if(args[1].equals("HorseRace")){
            if(args[3].equals("Arrays.sort")){
                String input;
                while((input = reader.readLine()) != null){
                    String[] stringArray = reader.readLine().split(" ");
                    Integer[] arr = Arrays.stream(stringArray)
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new);

                        Long start = System.nanoTime();
                        Arrays.sort(arr);
                        Long end = System.nanoTime();
                        System.out.println(arr.length + " " + (end-start)/1_000_000_000.0);

                }
            }else if(args[3].equals("NonAdaptive")){
                int cutoff = Integer.parseInt(args[4]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
                sorterMethod(sorter, reader(args[2]));
            }else if (args[3].equals("Adaptive")) {
                int cutoff = Integer.parseInt(args[4]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType, cutoff,true,false,0);
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
