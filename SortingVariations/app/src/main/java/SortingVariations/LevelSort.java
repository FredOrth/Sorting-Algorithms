package SortingVariations;

import java.lang.reflect.Array;
import java.util.Arrays;
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
    private int counter = 0;
    
    
    public LevelSort(sortMode mode, int c){ // main call could be LevelSort<Integer> adaptiveSort = new LevelSort<>(SortMode.adaptive);
        this.sortMode = mode;
        this.insertionSort = new InsertionSort<>();
        this.c = c;
    }

    @Override
    public void sort(T[] a) { //change to return type Integer
        T[] comps = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length); // a.length or 1?
        int counter = 0;

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
            int level = calculateBoundaryLeveL(start, newRun[1]-1, a.length-1);
            stackOfRuns.push(newRun);
            stackOfLevels.push(level); // placehodler for level
            start = newRun[1];
        }

        int lastKnownBoundaryLevel = Integer.MAX_VALUE;

        // Handle merging runs based on boundary levels
        while (stackOfRuns.size() > 1) {
            int[] newRun = stackOfRuns.pop(); // Pop the new run
            int[] leftRun = stackOfRuns.pop(); // Pop the left run

            int newLevel = calculateBoundaryLeveL(leftRun[0], leftRun[1] - 1, newRun[1] - 1); // Recalculate the new
                                                                                              // boundary level for
                                                                                              // merging

            System.out.println("Attempting to merge: Left Run = [" + leftRun[0] + ", " + (leftRun[1] - 1)
                    + "], New Run = [" + (newRun[0]) + ", " + (newRun[1] - 1) + "]");
            System.out.println("Boundary merged at level: " + newLevel);

            // Continue merging if necessary based on boundary levels
            System.out.println("Current newLevel: " + newLevel);
            System.out.println("Toplevel on stack: " + stackOfLevels.peek());
            System.out.println("Merging runs if newLevel <= toplevel...");

            // Merge runs as long as the newLevel is less than or equal to the top level on
            while (!stackOfRuns.isEmpty() && stackOfLevels.peek() < newLevel) {
                int[] topRun = stackOfRuns.pop();
                int toplevel = stackOfLevels.pop();

                System.out.println("Popped run: " + Arrays.toString(topRun) + " with toplevel = " + toplevel);
                System.out.println("Merged with leftRun: " + Arrays.toString(leftRun));
                System.out.println("Recalculated newLevel after merging: " + newLevel);

                // Merge the runs
                leftRun = mergeRuns(topRun, leftRun, a, comps);
                System.out.println(
                        "Before merging: leftrun=mergeRuns " + Arrays.toString(Arrays.copyOfRange(a, leftRun[0], topRun[1])));
                merge(a, comps, topRun[0], topRun[1] -1, leftRun[1]-1);
                System.out.println(
                        "After merging leftrun = mergeRuns: " + Arrays.toString(Arrays.copyOfRange(a, leftRun[0], topRun[1])));

                // Recalculate the boundary level after the merge
                //newLevel = calculateBoundaryLeveL(leftRun[0], leftRun[1], newRun[1]);
                //System.out.println("After merging with toplevel = " + toplevel + ", newLevel = " + newLevel);
            }

            System.out.println(
                    "Finished merging. Pushing back run: " + Arrays.toString(leftRun) + " with newLevel = " + newLevel);

            // Push the merged run and its boundary level back onto the stacks
            stackOfRuns.push(new int[] { leftRun[0], leftRun[1] });
            stackOfLevels.push(newLevel); // Push the updated boundary level
            lastKnownBoundaryLevel = newLevel; // Track the last known boundary level
        }

        // Handle the final merge (only one run remains on the stack)
        if (stackOfRuns.size() == 1) {
            int[] finalRun = stackOfRuns.pop();
            finalRun[1] = Math.max(finalRun[1], a.length); // Ensure the final run ends at the last index

            System.out
                    .println("Before merging finalmerge: " + Arrays.toString(Arrays.copyOfRange(a, finalRun[0], finalRun[1])));
            // Perform the final merge
            merge(a, comps, finalRun[0], finalRun[1], a.length - 1); // finalRun[1]-1 ends up with the wrong indexing, where it doesn't include the final element, but sorts the rest.
                                                                    // not using -1 on finalRun[1] results middle == rightEnd, which shouldnt be case and the last 2 elements being sorted on their own before added.
            System.out.println("After merging finalmerge: " + Arrays.toString(Arrays.copyOfRange(a, finalRun[0], finalRun[1])));
        }
    }   

    // merge runs, does what it implies. Combines two runs returns their resulting new range.
    // Includes check whether the runs are contiguous or not.
    private int[] mergeRuns(int[] leftRun, int[] rightRun, T[] a, T[] aux) {
        // Check if the runs are contiguous
        if (leftRun[1] != rightRun[0]) {
            System.out.println("Error: runs are not contiguous!");
            throw new IllegalArgumentException("Runs are not contiguous");
        }

            // Debug logs
        System.out.println("Before merging: Left Run = [" + leftRun[0] + ", " + (leftRun[1]) + "], Right Run = [" 
                        + rightRun[0] + ", " + rightRun[1] + "]");
        System.out.println("Subarray before merge: " + Arrays.toString(Arrays.copyOfRange(a, leftRun[0], rightRun[1] + 1)));

        System.out.println("Before merging: " + Arrays.toString(Arrays.copyOfRange(a, leftRun[0], rightRun[1] + 1)));
        // Perform the merge
        merge(a, aux, leftRun[0], leftRun[1], rightRun[1]); // from no -1 on the indexes
        System.out.println("After merging: " + Arrays.toString(Arrays.copyOfRange(a, leftRun[0], rightRun[1] + 1)));
        
        // Return the updated run range
        return new int[] {leftRun[0], rightRun[1]};
    }

    // working with 2 contigous runs
    public void merge2(T[] a, T[] comps, int leftStart, int middle, int rightEnd) {
        System.out.println("Merging: leftStart = " + leftStart + ", middle = " + middle + ", rightEnd = " + rightEnd);

        // Log the subarray being merged
        System.out.println("Subarray before merge: " + Arrays.toString(Arrays.copyOfRange(a, leftStart, rightEnd + 1)));

        int left = leftStart;
        int right = middle + 1;
        int tempIndex = leftStart;

        while (left <= middle && right <= rightEnd) {
            if (a[left].compareTo(a[right]) <= 0) {
                // Log the action taken
                System.out.println("Taking from left: a[" + left + "] = " + a[left]);
                comps[tempIndex++] = a[left++];
            } else {
                // Log the action taken
                System.out.println("Taking from right: a[" + right + "] = " + a[right]);
                comps[tempIndex++] = a[right++];
            }
        }

        // Copy remaining elements from left run
        while (left <= middle) {
            System.out.println("Copying remaining from left: a[" + left + "] = " + a[left]);
            comps[tempIndex++] = a[left++];
        }

        // Copy remaining elements from right run
        while (right <= rightEnd) {
            System.out.println("Copying remaining from right: a[" + right + "] = " + a[right]);
            comps[tempIndex++] = a[right++];
        }

        // Copy the merged subarray back to the original array
        System.arraycopy(comps, leftStart, a, leftStart, rightEnd - leftStart + 1);

        // Log the final merged subarray
        System.out.println("After merge: " + Arrays.toString(Arrays.copyOfRange(a, leftStart, rightEnd + 1)));
    }


    private void merge(T[] a, T[] aux, int leftStart, int middle, int rightEnd) {
        // Copy the relevant range into the auxiliary array
        for (int k = leftStart; k <= rightEnd; k++) {
            aux[k] = a[k];
        }

        int left = leftStart; // Start index of the left run
        int right = middle + 1; // Start index of the right run

        // Merge the runs back into the original array
        for (int k = leftStart; k <= rightEnd; k++) {
            if (left > middle) {
                // Left run is exhausted, take from the right
                a[k] = aux[right++];
            } else if (right > rightEnd) {
                // Right run is exhausted, take from the left
                a[k] = aux[left++];
            } else if (aux[right].compareTo(aux[left]) < 0) {
                // Take from the right if it's smaller
                a[k] = aux[right++];
            } else {
                // Take from the left otherwise
                a[k] = aux[left++];
            }
        }
    }



    public int calculateBoundaryLeveL(long aRun, long bRun, long cRun) { // convert from ints[] or T[] instead?
        //logic for finding midpoints
        // long ml = (aRun + bRun) / 2;
        // long mr = (bRun + cRun) / 2;
        long ml = aRun + ((bRun - aRun) / 2);
        long mr = bRun + ((cRun - bRun) / 2);

        // we calculate the XOR to find the parts that are differing
        long xor = ml ^ mr;

        //boundary determination
        int level = 64 - Long.numberOfLeadingZeros(xor);

        return level;
    }
    
    public int[] createRun(T[] a, int runStart, int c) { // why can't I acess the instanciation?

            int actualLength = findSequence(runStart, a);
            int runEnd = Math.min(runStart + Math.max(actualLength, c), a.length); 
            // if (this.sortMode == sortMode.adaptive) {
            //     actualLength = findSequence(runStart, a);
            // }

            System.out.println("Created run: Start = " + runStart + ", End = " + runEnd);
            if (actualLength < c){
                T[] subArray = createSubarray(a, runStart, runEnd);
                insertionSort.sort(subArray);
                System.arraycopy(subArray, 0, a, runStart, subArray.length);
            }
            return new int[] {runStart, runEnd};
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

    private T[] createSubarray(T[] a, int runStart, int runEnd) { 
        int length = runEnd - runStart;
        T[] subarray = (T[]) Array.newInstance(a.getClass().getComponentType(), length);
        System.arraycopy(a, runStart, subarray, 0, length);
        return subarray;
    }

}
