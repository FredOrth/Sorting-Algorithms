package SortingVariations;

import java.lang.reflect.Array;
import java.util.Stack;

public class LevelSort<T extends Comparable<T>> implements Sorter<T> {

    @Override
    public void sort(T[] a) {
        T[] comps = (T[]) Array.newInstance(a.getClass().getComponentType(), 1);

        //the stack, here it shoulb be able to be of int[], since each run is represented by its start and end indices.
        Stack<int[]> stackOfRuns = new Stack<>();

        //looking for pre-sorted runs
        int start = 0;
        for (int i = 1; i <= a.length; i++) {
            //a run end is found if we reach the end of the array, or the next element(run) is smaller.
            if (i == a.length || a[i].compareTo(a[i-1]) < -0) {
                stackOfRuns.push(new int[] {start, i});
            }
            //next run start set to i
            start = i;
        }

        //level assingmnet handling
        while(stackOfRuns.size() > 1){
            int[] aRun = stackOfRuns.pop();
            int[] bRun = stackOfRuns.pop();

            //boundary for the merging
            int boundary = aRun[1]; // end of 1st run

            merge(a, aRun[1], boundary, bRun[0], bRun[1], comps);
        }
    }


    public void merge(T[] a, int aStart, int aEnd, int bStart, int bEnd, T[] comps) {
        int i = aStart, j =bStart;
        
        for (int k = aStart; k < bEnd; k++) {
            if (i < aEnd && (j >= bEnd || a[i].compareTo(a[j])<= 0)) {
                comps[k] = a[i++];
            } else {
                comps[k] = a[j++];
            }
        }
        System.arraycopy(comps, aStart, a, aStart, bEnd - aStart); // ???
    }
    
    
}
