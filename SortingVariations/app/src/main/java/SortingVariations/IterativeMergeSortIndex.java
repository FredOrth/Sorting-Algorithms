package SortingVariations;

import java.util.Stack;

public class IterativeMergeSortIndex<T extends Comparable<T>> extends Sorter1<T> implements Sorter<T>{
    private int cutoff; //Need to make this compatible with insertionSort, discuss with Frederik and Tobias
    private int counter;

    public IterativeMergeSortIndex(int cutoff){
        this.cutoff = cutoff;
        this.counter = 0;
    }


    @Override
    public void sort(T[] a){
        T[] aux = a.clone();
        Stack<Integer[]> stack = new Stack<>();
        int i = 0;

        while(i<a.length){
            Integer[] arr = new Integer[2];

            if(i+cutoff<=a.length){
                arr[0] = i;
                i+=cutoff-1;
                arr[1] = i;
                i++;
            }
            else{
                arr[0] = i;
                i=a.length-1;
                arr[1] = i;
                i++;
            }

            insertionSort(a, arr[0], arr[1]);

            while(stack.size()> 0){
                Integer[] topArray = stack.peek();
                if(topArray[1] - topArray[0] == arr[1] - arr[0]){
                    stack.pop();
                    merge(a,aux,topArray[0], arr[0]-1, arr[1]);
                    arr[0] = topArray[0];
                }else{
                    break;
                }
            }
            stack.add(arr);

        }

        while(stack.size()>1){
            Integer[] arr = stack.pop();
            merge(a,aux,stack.peek()[0], arr[0]-1, arr[1]);
            stack.peek()[1] = arr[1];
        }


    }
}

