package SortingVariations;

import java.lang.reflect.Array;
import java.util.Stack;

public class BinomialSort<T extends Comparable<T>> implements Sorter<T> {
    private int counter = 0;
    private int cutoff;
    private InsertionSort<T> insertionSort;

    public BinomialSort(int cutoff){
        this.cutoff = cutoff;
        this.insertionSort = new InsertionSort<>();
    }

    @Override
    public void sort(T[] a) {
        counter = 0;
        //Setup
        Stack<T[]> stack = new Stack<>();
        int cutoffCounter = 0;
        int i = 0;
        while(i<a.length){
            // System.out.println("new number: " + a[i]);
            int sequence = findSequence(i, a);
            T[] comps = makeArray(a, i, sequence);
            i += comps.length;

            while(stack.size()>1){
                if(stack.peek().length < comps.length){
                    comps = merge(comps, stack.pop());
                }else{
                    break;
                }
            }
            while(!stack.isEmpty()){
                if(stack.peek().length < comps.length*2){
                    comps = merge(comps, stack.pop());
                }else{
                    break;
                }
            }
            stack.add(comps);
    }
    while(stack.size()>1){
        stack.push(merge(stack.pop(), stack.pop()));
    }

    T[] finishedArray = stack.pop();
    for(int j = 0; j<a.length; j++){
        a[j] = finishedArray[j];
    }

    }

    private T[] makeArray(T[] a, int i, int sequence){
        int arrLength;
        // System.out.println("Numbers:");
        if(sequence > cutoff){
            arrLength = sequence;
        }else{
            arrLength = cutoff;
            
            }
            T[] comps = (T[]) Array.newInstance(a.getClass().getComponentType(), arrLength);
            for(int j = 0; j<comps.length; j++){
                // System.out.println(a[i]);
                comps[j] = a[i];
                i++;
        }
        if(comps.length<= cutoff){
            insertionSort.sort(comps);
        }
        return comps;
    }
        
    

    // public int findSequence(int i, T[]a){
    //     int j = i;
    //     System.out.println("This is the sequence:");
    //     if(a[j].compareTo(a[j+1])<0){
    //         counter++;
    //         j++;
    //         while(j<a.length-1){
    //             if(a[j].compareTo(a[j+1])<0){
    //                 System.out.println(a[j]);
    //                 System.out.println(a[j+1]);
    //             counter++;
    //             j++;
    //         }else{
    //             break;
    //         }
    //         }
    //     }else{
    //         counter++;
    //         j++;
    //         while(j<a.length-1){
    //         if(a[j].compareTo(a[j+1])>=0){
    //             counter++;
    //             j++;
    //         }else{
    //             break;
    //         }
    //         }
    //         for(int k = i; k<j/2; k++){
    //             T bigElement = a[j-(k-i)];
    //             a[j-(k-i)] = a[k];
    //             a[k]= bigElement;
    //         }
    //     }
    //     return j;
    // }

    private int findSequence(int i, T[]a){
        int j = i;
        if(j==a.length-1){
            return 1;
        }
        if(a[j].compareTo(a[j+1])<0){
            j++;
            counter++;
            while(j<a.length-1){
                counter++;
                if(a[j].compareTo(a[j+1])<0){
                    j++;
                }else{
                    break;
                }
            }
        }else{
            j++;
            counter++;
            while(j<a.length-1){
                counter++;
                if(a[j].compareTo(a[j+1])>= 0){
                    j++;
                }else{
                    break;
                }
            }
            j++;
            for(int k= 0; k<(j-i)/2; k++){
                T smallElm = a[i+k];
                a[i+k] = a[j-k-1];
                a[j-k-1] = smallElm; 
            }
        }
        return j-i;
        
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

