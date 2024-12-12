package SortingVariations;

import java.lang.reflect.Array;
import java.util.Stack;

import org.checkerframework.checker.units.qual.s;



public class LevelSort<T extends Comparable<T>> implements Sorter<T> {

    public enum sortMode {
        adaptive,
        nonAdaptive
    }

    private sortMode sortMode;
    private InsertionSort<T> insertionSort;
    private int c;
    
    
    public LevelSort(sortMode mode, int c){ // main call could be LevelSort<Integer> adaptiveSort = new LevelSort<>(SortMode.adaptive);
        this.sortMode = mode;
        this.insertionSort = new InsertionSort<>();
        this.c = c;
    }

    @Override
    public void sort(T[] a) {
        T[] comps = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length); // a.length or 1?

        //the stack, here it shoulb be able to be of int[], since each run is represented by its start and end indices.
        Stack<int[]> stackOfRuns = new Stack<>();

        //With current knowledge, to also keep a stack of levels, which corresponds to each run, if we don't use a Run class atm.
        Stack<Integer> stackOfLevels = new Stack<>();

        //looking for pre-sorted runs
        int start = 0;
        // for (int i = 1; i <= a.length; i++) {
        //     //a run end is found if we reach the end of the array, or the next element(run) is smaller.
        //     if (i == a.length || a[i].compareTo(a[i-1]) < -0) {
        //         stackOfRuns.push(new int[] {start, i});
        //         stackOfLevels.push(-1); //placeholder level
        //     }
        //     //next run start set to i
        //     start = i;
        // }

        while (start < a.length) {
            int[] newRun = createRun(a, start, c);
            stackOfRuns.push(newRun);
            stackOfLevels.push(-1); // placehodler for level
            start = newRun[1];
        }

        //level invariant matained handling and merging
        while(stackOfRuns.size() > 1){
            int[] newRun = stackOfRuns.pop(); // new run due to being popped 1st
            int[] leftRun = stackOfRuns.pop(); // left run as it was popped last

            int newLevel = calculateBoundaryLeveL(leftRun[0], leftRun[1], newRun[1]);

            // merge(a, aRun[0], aRun[1], bRun[0], bRun[1], comps);

            // stackOfRuns.push(new int[] {bRun[0], aRun[1]});

            //check if it's been merging at the right level
            System.out.println("Boundary merged at level: " + newLevel);

            //Merge while invariant is violated
            while (!stackOfRuns.isEmpty() && stackOfLevels.peek() < newLevel) {
                int[] topRun = stackOfRuns.pop();
                int toplevel = stackOfLevels.pop();
                leftRun = mergeRuns(topRun, leftRun, a, comps);
                newLevel = calculateBoundaryLeveL(leftRun[0], leftRun[1], newRun[1]);
            }

            stackOfRuns.push(new int[] {leftRun[0], leftRun[1]});
            stackOfLevels.push(newLevel);
        }
        //call merge if needed.
        if (stackOfRuns.size() == 1) {
            int[] finalRun = stackOfRuns.pop();
            merge(a, finalRun[0], finalRun[1], a.length, a.length, comps); //since array is already sorted 2nd run starts and ends with a.length
        }
    }   

    // merge runs, does what it implies. Combines two runs returns their resulting new range.
    private int[] mergeRuns(int[] leftRun, int[] rightRun, T[] a, T[] comps) {
        merge(a, leftRun[0], leftRun[1], rightRun[0], rightRun[1], comps);
        return new int[] { leftRun[0], rightRun[1] };
    }


    public void merge(T[] a, int aStart, int aEnd, int bStart, int bEnd, T[] comps) { //might have to change the argument naming here to not make them too confusing.
        int i = aStart, j =bStart;
        
        for (int k = aStart; k < bEnd; k++) {
            if (i < aEnd && (j >= bEnd || a[i].compareTo(a[j])<= 0)) {
                comps[k] = a[i++];
            } else {
                comps[k] = a[j++];
            }
        }
        System.arraycopy(comps, aStart, a, aStart, bEnd - aStart); 
    }
    

    public int calculateBoundaryLeveL(long aRun, long bRun, long cRun) { // convert from ints[] or T[] instead?
        //logic for finding midpoints
        long ml = (aRun + bRun) / 2;
        long mr = (bRun + cRun) / 2;

        // we calculate the XOR to find the parts that are differing
        long xor = ml ^ mr;

        //boundary determination
        int level = Long.numberOfLeadingZeros(xor);

        return level;
    }
    
    public int[] createRun(T[] a, int runStart, int c) { // why can't I acess the instanciation?
            int actualLength = 1;
            if (this.sortMode == sortMode.adaptive) {
                // adaptive ie. explore how long is the run
                actualLength = findLongestRun(a, runStart, a.length);
            }
            if (actualLength >= c ) {
                return new int[]{runStart, runStart + actualLength};
            } else {
                int runEnd = Math.min(runStart + c, a.length);
                T[] subArray = createSubarray(a, runStart, runEnd);
                insertionSort.sort(subArray);
                System.arraycopy(subArray, 0, a, runStart, subArray.length);
                return new int[] {runStart, runEnd};
            }
    }

    public int findLongestRun(T[] a, int runStart, int runEnd) {
        boolean ascending = true; // it is sorted
        int i = runStart + 1;

        if (i < runEnd && a[i].compareTo(a[i-1]) < 0) {
            ascending = false;
        }

        while (i < runEnd) {
            if (ascending && a[i].compareTo(a[i-1]) < 0) break;
            if (!ascending && a[i].compareTo(a[i-1]) < 0) break;
            i++;
        }

        if (!ascending) {
            reverse(a, runStart, i - 1);
        }

        return i - runStart;
    }

    public void reverse(T[] a, int runStart, int runEnd) { // use the sequence select thing instead?
        while (runStart < runEnd) {
            T temp = a[runStart];
            a[runStart] = a[runEnd];
            a[runEnd] = temp;
            runStart++;
            runEnd--;
        }
    }


    private T[] createSubarray(T[] a, int runStart, int runEnd) {
        int length = runEnd - runStart;
        T[] subarray = (T[]) Array.newInstance(a.getClass().getComponentType(), length);
        System.arraycopy(a, runStart, subarray, 0, length);
        return subarray;
    }
}
