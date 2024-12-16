package SortingVariations;

import java.lang.reflect.Array;
import java.util.Stack;

public class IterativeMergeSortC<T extends Comparable<T>> implements Sorter<T>{
    private int cutoff; //Need to make this compatible with insertionSort, discuss with Frederik and Tobias
    private int counter;

    public IterativeMergeSortC(int cutoff){
        this.cutoff = cutoff;
        this.counter = 0;
    }


    @Override
    public Integer sort(T[] a){
        Stack<T[]> stack = new Stack<>();
        int cutoffCounter = 0;
        InsertionSort<T> insertionSort = new InsertionSort<>();

        while(cutoffCounter < a.length){
            int arrLength;
            if(cutoffCounter+cutoff < a.length){
                arrLength=cutoff;
            }else{
                arrLength=a.length-cutoffCounter;
            }
            T[] comps = (T[]) Array.newInstance(a.getClass().getComponentType(), arrLength);
            for(int i = 0; i<comps.length; i++){
                    comps[i] = a[cutoffCounter];
                    cutoffCounter++;
                }

                insertionSort.sort(comps);
                
            while(stack.size() > 1){
                if(stack.peek().length == comps.length){
                    comps = merge(comps, stack.pop());  
                }else{
                    break;
                }
            }
            stack.push(comps);
        }

        while(stack.size()>1){
            stack.push(merge(stack.pop(), stack.pop()));
        }
        T[] finishedArray = stack.pop();
        for(int i = 0; i<a.length; i++){
            a[i] = finishedArray[i];
        }
        
        // System.out.println(counter);
        return counter;
        //Maybe???
        // System.arraycopy(finishedArray, 0, a, 0, a.length);
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

