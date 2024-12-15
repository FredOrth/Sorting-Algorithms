package SortingVariations;

public class Sorter1<T extends Comparable<T>>{
    

    protected int merge(T[] a, T[] aux, int low, int mid, int high){
        int counter = 0;

        for (int k = low; k<=high; k++) {
            aux[k] = a[k];
        }


        int i = low;
        int j = mid+1;

        for (int k = low; k <= high; k++) {
            if(i > mid){
            a[k] = aux[j++];}
            else if (j > high)
            { a[k] = aux[i++];}
            else if((aux[j].compareTo(aux[i]))<0) 
            {a[k] = aux[j++];
            counter++;}
            else 
            {a[k] = aux[i++];
            counter++;}
            
        }
        return counter;
    }

    protected int insertionSort(T[] a, int low, int high) {
        int counter = 0;
        for (int i = low + 1; i <= high; i++) {
            T key = a[i];
            int j = i - 1;

            while (j >= low && a[j].compareTo(key) > 0) {
                counter++; // Couning that its[j] > key
                a[j + 1] = a[j];
                j--;
            }
    
            // if while loop condition failscount comparison
            if (j >= low) {
                counter++; // Counting comparison when while loop eends
            }

            a[j + 1] = key;
        }
        return counter;
    }
}
