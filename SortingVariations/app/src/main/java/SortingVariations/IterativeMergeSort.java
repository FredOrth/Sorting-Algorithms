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
                if(stack.peek().length == stack.peekItem(2).length){
                    //merge
                }
            }
        }
    }

    // stack.push(new T[]{comp});
    //         if(stack.size() >1){
    //             if(stack.peek())
    //         }

}
