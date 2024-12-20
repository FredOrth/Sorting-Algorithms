package SortingVariations;

import java.util.Stack;

public class LevelSortIndex<T extends Comparable<T>> implements Sorter<T> {
    private int counter = 0; // Counter for comparisons
    private int cutoff;
    private boolean adaptive;

    // Constructor
    public LevelSortIndex(int cutoff, boolean adaptive) {
        this.cutoff = cutoff;
        this.adaptive = adaptive;
    }

    @Override
    public Integer sort(T[] a) {
        if (a.length == 0) return 0; // Handle empty array

        T[] aux = a.clone(); // Auxiliary array for merging
        counter = 0;

        // Inner class to represent a run with start, end, and level
        class Run {
            int start;
            int end;
            int level;

            Run(int start, int end, int level) {
                this.start = start;
                this.end = end;
                this.level = level;
            }
        }

        Stack<Run> stack = new Stack<>();

        int i = 0;

        while (i < a.length) {
            int runStart = i;
            int runEnd = findRun(a, i);
            int runLength = runEnd - runStart;

            // If run is shorter than cutoff, extend it using insertion sort
            if (runLength < cutoff) {
                int actualEnd = Math.min(runStart + cutoff - 1, a.length - 1);
                insertionSort(a, runStart, actualEnd);
                runEnd = actualEnd + 1;
                runLength = runEnd - runStart;
            }

            // Create the current run
            Run currentRun = new Run(runStart, runEnd - 1, 0);

            // If there's a run on the stack, compute the level of the current run
            if (!stack.isEmpty()) {
                Run L = stack.peek();
                currentRun.level = computeLevel(L.start, L.end, currentRun.start, currentRun.end);
            }

            // Merge runs on the stack while the top run has a lower level than the current run
            while (!stack.isEmpty() && stack.peek().level < currentRun.level) {
                Run L = stack.pop();
                Run N = currentRun;
                // Merge L and N into [L.start..N.end]
                merge(a, aux, L.start, L.end, N.end);
                // Create a new merged run
                currentRun = new Run(L.start, N.end, 0);
                // If there's another run on the stack, compute the new level
                if (!stack.isEmpty()) {
                    Run newL = stack.peek();
                    currentRun.level = computeLevel(newL.start, newL.end, currentRun.start, currentRun.end);
                }
            }

            // Push the current run onto the stack
            stack.push(currentRun);
            i = runEnd;
        }

        // Final merging of remaining runs on the stack
        while (stack.size() > 1) {
            Run run1 = stack.pop();
            Run run2 = stack.pop();
            // Merge run2 and run1 into [run2.start..run1.end]
            merge(a, aux, run2.start, run2.end, run1.end);
            // Create the merged run
            Run mergedRun = new Run(run2.start, run1.end, 0);
            // If there's another run on the stack, compute the new level
            if (!stack.isEmpty()) {
                Run newL = stack.peek();
                mergedRun.level = computeLevel(newL.start, newL.end, mergedRun.start, mergedRun.end);
            }
            // Push the merged run back onto the stack
            stack.push(mergedRun);
        }

        return counter; // Return the number of comparisons
    }

    /**
     * Finds the end index (exclusive) of the run starting at 'start'.
     * If adaptive, finds the longest non-decreasing or strictly decreasing run.
     * If strictly decreasing, reverses it to make it ascending.
     */
    private int findRun(T[] a, int start) {
        int i = start;
        if (i >= a.length - 1) return a.length;

        if (adaptive) {
            if (a[i].compareTo(a[i + 1]) <= 0) {
                // Ascending run
                while (i < a.length - 1 && a[i].compareTo(a[i + 1]) <= 0) {
                    counter++; // Count comparison
                    i++;
                }
            } else {
                // Descending run
                while (i < a.length - 1 && a[i].compareTo(a[i + 1]) > 0) {
                    counter++; // Count comparison
                    i++;
                }
                // Reverse the descending run to make it ascending
                reverseRun(a, start, i);
            }
        }

        return i + 1; // Exclusive
    }

    /**
     * Reverses the run in-place from index 'low' to 'high'.
     */
    private void reverseRun(T[] a, int low, int high) {
        while (low < high) {
            T temp = a[low];
            a[low] = a[high];
            a[high] = temp;
            low++;
            high--;
        }
    }

    /**
     * Computes the level of the boundary between two runs.
     * ml = i_a + i_b
     * mr = i_b + i_c
     * level = 64 - Long.numberOfLeadingZeros(ml ^ mr)
     */
    private int computeLevel(int ia, int ib, int iboundaryStart, int iboundaryEnd) {
        // Run L: [ia..ib], Run N: [ib+1..ic]
        long ml = (long) ia + ib;
        long mr = (long) (ib + 1) + iboundaryEnd;
        long x = ml ^ mr;
        if (x == 0) return 0;
        return 64 - Long.numberOfLeadingZeros(x);
    }

    /**
     * Merges two runs [low..mid] and [mid+1..high] into a single sorted run.
     */
    private void merge(T[] a, T[] aux, int low, int mid, int high) {
        // Copy to auxiliary array
        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }

        int i = low;
        int j = mid + 1;

        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > high) {
                a[k] = aux[i++];
            } else if (aux[j].compareTo(aux[i]) < 0) {
                a[k] = aux[j++];
                counter++;
            } else {
                a[k] = aux[i++];
                counter++;
            }
        }
    }

    /**
     * Performs insertion sort on the subarray [low..high].
     */
    private void insertionSort(T[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            T key = a[i];
            int j = i - 1;

            // Move elements of a[low..i-1] that are greater than key
            while (j >= low && a[j].compareTo(key) > 0) {
                counter++; // Count comparison
                a[j + 1] = a[j];
                j--;
            }

            if (j >= low) {
                counter++; // Count the comparison that failed
            }

            a[j + 1] = key;
        }
    }
}