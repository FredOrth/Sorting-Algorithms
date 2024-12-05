package SortingVariations;

// import java.util.Stack;

import java.util.Comparator;

public class IterativeMergeSort<T extends Comparable<T>> implements Sorter<T>{




    @Override
    public void sort(T[] a){
        Stack<T[]> stack = new Stack<>();
        for (T comp : a) {
            T[] comps = (T[]) new Object[]{comp};
            stack.push(comps);
            if(stack.size() > 1){
                while(stack.peek().length == stack.peekItem(2).length){
                    stack.push(merge(stack.pop(), stack.pop()));
                }
            }
        }
        while(stack.size()>1){
            stack.push(merge(stack.pop(), stack.pop()));
        }
        a = stack.pop();
    }

    public T[] merge(T[] a, T[]b){
        int aLength = a.length;
        int bLength = b.length;
        T[] arrToReturn = (T[]) new Object[aLength + bLength];
        int aCounter = 0;
        int bCounter = 0;
        while(aCounter < aLength && bCounter<bLength){
            if(aCounter == a.length){
                for(int i = bCounter; i<bLength; i++){
                    arrToReturn[aCounter+bCounter] = b[bCounter];
                }
                    break;
                }else if (bCounter == b.length){
                    for(int i = aCounter; i<aLength; i++){
                        arrToReturn[aCounter+bCounter] = a[aCounter];
                        }
                        break;
                    }else if(a[aCounter].compareTo(b[bCounter])<0){
                        arrToReturn[aCounter+bCounter] = a[aCounter];
                        aCounter++;
                    }else{
                        arrToReturn[aCounter+bCounter] = b[bCounter];
                        bCounter++;
                    }
            
        
    }
    return arrToReturn;
  }

}

