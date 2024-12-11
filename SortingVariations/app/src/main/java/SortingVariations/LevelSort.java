package SortingVariations;

import java.lang.reflect.Array;
import java.util.Stack;



public class LevelSort<T extends Comparable<T>> implements Sorter<T> {

    public enum sortMode {
        adaptive,
        nonAdaptive
    }

    private sortMode sortMode;
    
    public LevelSort(sortMode mode){ // main call could be LevelSort<Integer> adaptiveSort = new LevelSort<>(SortMode.adaptive);
        this.sortMode = mode;
    }

    @Override
    public void sort(T[] a) {
        T[] comps = (T[]) Array.newInstance(a.getClass().getComponentType(), 1);

        //the stack, here it shoulb be able to be of int[], since each run is represented by its start and end indices.
        Stack<int[]> stackOfRuns = new Stack<>();

        //With current knowledge, to also keep a stack of levels, which corresponds to each run, if we don't use a Run class atm.
        Stack<Integer> stackOfLevels = new Stack<>();

        //looking for pre-sorted runs
        int start = 0;
        for (int i = 1; i <= a.length; i++) {
            //a run end is found if we reach the end of the array, or the next element(run) is smaller.
            if (i == a.length || a[i].compareTo(a[i-1]) < -0) {
                stackOfRuns.push(new int[] {start, i});
                stackOfLevels.push(-1); //placeholder level
            }
            //next run start set to i
            start = i;
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
    
}
