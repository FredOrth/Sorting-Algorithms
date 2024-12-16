package SortingVariations;

import java.lang.reflect.Array;
import java.util.Stack;

public class BinomialSort<T extends Comparable<T>> implements Sorter<T> {
    private int counter = 0;
    private int cutoff;
    private InsertionSort<T> insertionSort;
    private boolean adaptive;

    public BinomialSort(int cutoff, boolean adaptive){
        this.cutoff = cutoff;
        this.insertionSort = new InsertionSort<>();
        this.adaptive = adaptive;
    }

    @Override
    public Integer sort(T[] a) {
        counter = 0;
        if(a.length == 0){
            return 0;
        }
        //Setup
        Stack<T[]> stack = new Stack<>();
        int i = 0;
        while(i<a.length){
            T[] comps = makeArray(a, i);
            i+=comps.length;
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
            assert stack.size() == 0 || stack.peek().length >= comps.length*2;
            stack.add(comps);
    }
    while(stack.size()>1){
        stack.push(merge(stack.pop(), stack.pop()));
    }

    T[] finishedArray = stack.pop();
    for(int j = 0; j<a.length; j++){
        a[j] = finishedArray[j];
    }

    return counter;
    }

    private T[] makeArray(T[] a, int i){
        int arrLength;
        int sequence = findSequence(i, a);
        if(adaptive && sequence> cutoff){
            arrLength = sequence;
        }else{
            if(a.length-i<cutoff){
                arrLength = a.length-i;
            }else{
            arrLength = cutoff;}
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

    private int findSequence(int i, T[]a){
        int j = i;
        if(j==a.length-1){
            return 1;
        }
        if(a[j].compareTo(a[j+1])<=0){
            j++;
            counter++;
            while(j<a.length-1){
                counter++;
                if(a[j].compareTo(a[j+1])<0){
                    j++;
                }else{
                    j++;
                    break;
                }
            }
        }else{
            j++;
            counter++;
            while(j<a.length-1){
                counter++;
                if(a[j].compareTo(a[j+1])> 0){
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

