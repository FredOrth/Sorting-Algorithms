package SortingVariations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import SortingVariations.Benchmarking.*;

import static SortingVariations.Benchmarking.SearchAndSort.fillIntArray;
import static SortingVariations.Benchmarking.SearchAndSort.shuffle;

public class ParallelRecursiveMergeSort<T extends Comparable<T>> implements Sorter<T> {

    private final int threshold;

    public ParallelRecursiveMergeSort(int threshold){
        this.threshold=threshold;
    }


    @Override
    public void sort(T[] a) {
        ForkJoinPool pool = new ForkJoinPool();
        AtomicInteger comparisonCounter = new AtomicInteger(0);
        // invotke sort task
        T[] sorted = pool.invoke(new MergeSortTask<>(a, 0, a.length - 1, threshold,comparisonCounter));

        // copy it back to the original array.
        System.arraycopy(sorted, 0, a, 0, a.length);

        // Here we need to get the number of comparisons out to python somehow. Integer return instead of void?
        // or just print?

    }



    private static class MergeSortTask<T extends Comparable<T>> extends RecursiveTask<T[]> {
        private final T[] array;
        private final int low, high, threshold;
        private final AtomicInteger comparisonCounter;

        public MergeSortTask(T[] array, int low, int high, int threshold,AtomicInteger comparisonCounter) {
            this.array = array;
            this.low = low;
            this.high = high;
            this.threshold = threshold;
            this.comparisonCounter = comparisonCounter;
        }

        @Override
        protected T[] compute() {
            if (high - low + 1 <= threshold) {
                // Sequential sort for small subarrays
                // we can play which sequential algorithm we use here
                T[] sorted = java.util.Arrays.copyOfRange(array, low, high + 1);
                RecursiveMergeSort<T> sequentialSorter = new RecursiveMergeSort<>();
                sequentialSorter.sort(sorted);
                return sorted;
            }

            // Split
            int mid = low + (high - low) / 2;

            // make subtasks
            MergeSortTask<T> leftTask = new MergeSortTask<>(array, low, mid, threshold,comparisonCounter);
            MergeSortTask<T> rightTask = new MergeSortTask<>(array, mid + 1, high, threshold,comparisonCounter);

            // fork and compute subtasks
            leftTask.fork();
            T[] rightResult = rightTask.compute(); // Compute the right half directly
            T[] leftResult = leftTask.join();      // Wait for the left half to finish

            // merge
            return merge(leftResult, rightResult);
        }

        // MErge method, without lo mid and high, could mayve use the same one as the recursive MergeSort but this was easier
        private T[] merge(T[] left, T[] right) {
            T[] merged = java.util.Arrays.copyOf(left, left.length + right.length);
            int i = 0, j = 0, k = 0;

            while (i < left.length && j < right.length) {
                comparisonCounter.incrementAndGet();
                if (left[i].compareTo(right[j]) <= 0) {
                    merged[k++] = left[i++];
                } else {
                    merged[k++] = right[j++];
                }
            }

            while (i < left.length) merged[k++] = left[i++];
            while (j < right.length) merged[k++] = right[j++];

            return merged;
        }
    }



    // NOT WORKING YET, BUGS
    public int[] twoSequenceSelect(T[] a,T[] b, int k){
        int[] aiBi = new int[2];


        int low = Math.max(0,k-b.length);
        int high = Math.min(k,a.length);

        while (low < high) {
            int ja = (low + high) / 2; // Midpoint for binary search
            int jb = k - ja;           // Complement index for b

            // Bounds checks and default behavior
            T leftA = (ja > 0) ? a[ja - 1] : null; // Treat null as -∞
            T rightA = (ja < a.length) ? a[ja] : null; // Treat null as +∞
            T leftB = (jb > 0) ? b[jb - 1] : null; // Treat null as -∞
            T rightB = (jb < b.length) ? b[jb] : null; // Treat null as +∞

            // Condition checks
            if ((leftA == null || rightB == null || leftA.compareTo(rightB) <= 0) &&
                    (leftB == null || rightA == null || leftB.compareTo(rightA) < 0)) {
                // Both conditions are satisfied
                aiBi[0] = ja;
                aiBi[1] = jb;
                return aiBi;
            }

            // Adjust binary search bounds
            if (leftA != null && rightB != null && leftA.compareTo(rightB) > 0) {
                high = ja; // ja is too large
            } else {
                low = ja + 1; // ja is too small
            }
        }


        aiBi[0] = low;
        aiBi[1] = k - low;
        return aiBi;
    }


    // Benchmarking below:

    public static void runSize(ForkJoinPool pool, int pSize, int threshold, int n) {

        final Integer[] intArray = new Integer[pSize];
        for (int i = 0; i < pSize; i++) {
            intArray[i] = i;
        }

        Benchmark.Mark8Setup("parallel mergesort Executor", String.format("%2d", n),
                new Benchmarkable() {
                    private AtomicInteger comparisonCounter;

                    public void setup() {
                        java.util.Collections.shuffle(java.util.Arrays.asList(intArray));

                        comparisonCounter = new AtomicInteger(0); // initialize fresh counter
                    }

                    public double applyAsDouble(int i) {
                        MergeSortTask<Integer> task = new MergeSortTask<>(intArray, 0, pSize - 1, threshold,comparisonCounter);
                        pool.invoke(task);
                        //testSorted(intArray);
                        // only needed while testing
                        //return dummy value
                        return 0.0;
                    }
                }
        );
    }

}
