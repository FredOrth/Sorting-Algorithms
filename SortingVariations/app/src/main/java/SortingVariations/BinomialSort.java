package SortingVariations;

import java.lang.reflect.Array;
import java.util.Stack;

public class BinomialSort<T extends Comparable<T>> implements Sorter<T> {
    private int counter = 0;
    private int cutoff;
    private InsertionSort<T> insertionSort;

    public BinomialSort(int cutoff){
        this.cutoff = cutoff;
    }

    @Override
    public void sort(T[] a) {
        //Setup
        Stack<T[]> stack = new Stack<>();
        int cutoffCounter = 0;

        for(int i = 0; i<a.length; i++){
            int sequence = findSequence(i, a);
            T[] comps = makeArray(a, i, sequence);
            i += comps.length;

            //Stack
    }
    }

    private T[] makeArray(T[] a, int i, int sequence){
        int arrLength;
        if(sequence > cutoff){
            arrLength = sequence;
        }else{
            arrLength = cutoff;
            
            }
            T[] comps = (T[]) Array.newInstance(a.getClass().getComponentType(), arrLength);
            for(int j = 0; j<comps.length; j++){
                comps[j] = a[i];
                i++;
        }
        if(comps.length<= cutoff){
            insertionSort.sort(comps);
        }
        return comps;
    }
        
    

    public int findSequence(int i, T[]a){
        int j = i;
        if(a[j].compareTo(a[j+1])<0){
            counter++;
            i++;
            while(j<a.length-1){
                if(a[j].compareTo(a[j+1])<0){
                counter++;
                j++;
            }else{
                break;
            }
            }
        }else{
            counter++;
            while(j<a.length-1){
            if(a[j].compareTo(a[j+1])>=0){
                counter++;
                j++;
            }else{
                break;
            }
            }
            for(int k = i; k<j/2+1; k++){
                T bigElement = a[j-(k-i)];
                System.out.println(bigElement);
                System.out.println(a[k]);
                a[j-(k-i)] = a[k];
                a[k]= bigElement;
            }
        }
        return j;
    }

    public T[] merge(T[] a, T[]b){
        int aLength = a.length;
        int bLength = b.length;
        T[] arrToReturn = (T[]) Array.newInstance(a.getClass().getComponentType(), aLength + bLength);
        int aCounter = 0;
        int bCounter = 0;
        while(aCounter < aLength || bCounter<bLength){
            if(aCounter == a.length){
                for(int i = bCounter; i<bLength; i++){
                    arrToReturn[aCounter+bCounter] = b[bCounter];
                    bCounter++;
                }
                    break;
                }else if (bCounter == b.length){
                    for(int i = aCounter; i<aLength; i++){
                        arrToReturn[aCounter+bCounter] = a[aCounter];
                        aCounter++;
                        }
                        break;
                    }else if(a[aCounter].compareTo(b[bCounter])<0){
                        arrToReturn[aCounter+bCounter] = a[aCounter];
                        aCounter++;
                        counter++;
                    }else{
                        arrToReturn[aCounter+bCounter] = b[bCounter];
                        bCounter++;
                        counter++;
                    }
            
        
    }
    return arrToReturn;
  }
    
}

