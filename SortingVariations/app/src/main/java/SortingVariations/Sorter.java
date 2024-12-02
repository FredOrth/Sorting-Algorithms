package SortingVariations;

public interface Sorter<T extends Comparable<T>> {

    void sort(T[] a);
    
}
