package SortingVariations;

import java.util.Comparator;

public class InsertionMergeSort<T extends Comparable<T>> implements Sorter<T> {

    @Override
    public void sort(T[] a){
        int counter = 0;

        T[] aux = a.clone();

        // sort(a, aux, 0, a.length-1);

        System.out.println(counter);
    }

    private void sort(T[] a, T[] aux, int low, int high){
        // Using Comparable we force the user to convert to complex types, should be fine
        // Basically just a checker whether it's not valid. Or actually this is our base case?
        if (high <= low) return;

        // find mid
        int mid = low + (high - low) / 2;

        //recursively call sort of the low to mid and mid to high
        sort(a, aux, low, mid);
        sort(a, aux, mid + 1, high);

        // final merge? or no, this is the merge for all the recursive calls when it becmes small enough
        // merge(a, aux, low, mid, high);
    }


    

}
