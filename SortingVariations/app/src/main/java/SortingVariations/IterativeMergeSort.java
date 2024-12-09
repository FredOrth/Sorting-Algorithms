package SortingVariations;

import java.lang.reflect.Array;
import java.util.Arrays;

import SortingVariations.Util.Stack;

public class IterativeMergeSort<T extends Comparable<T>> implements Sorter<T>{




    @Override
    public void sort(T[] a){
        Stack<T[]> stack = new Stack<>();
        for (T comp : a) {
            // T[] comps = (T[]) new Object[]{comp};
            T[] comps = (T[]) Array.newInstance(a.getClass().getComponentType(), 1);
            comps[0] = comp;
            stack.push(comps);
            while(stack.size() > 1){
                System.out.println(Arrays.toString(stack.peekItem(1)));
                System.out.println(Arrays.toString(stack.peek()));
                if(stack.peek().length == stack.peekItem(1).length){
                    stack.push(merge(stack.pop(), stack.pop()));
                }else{
                    break;
                }
            }
        }
        while(stack.size()>1){ // two while loops for the same logic?
            stack.push(merge(stack.pop(), stack.pop()));
        }
        T[] finishedArray = stack.pop(); // Are we saying the "sortedArray" is equal to 1 pop of the stack ? Or is this assuming we have merged all of them, meaning there is only one run left on the stack?
        for(int i = 0; i<a.length; i++){
            a[i] = finishedArray[i]; // repopulating our initial array of items that we want to return?
        }
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
                    }else{
                        arrToReturn[aCounter+bCounter] = b[bCounter];
                        System.out.println(a[aCounter]);
                        System.out.println(b[bCounter]);
                        bCounter++;
                    }
            
        
    }
    return arrToReturn;
  }

}

