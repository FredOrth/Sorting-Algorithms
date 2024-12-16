package SortingVariations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import SortingVariations.Benchmarking.*;
import SortingVariations.Util.TwoSequenceSelect;

import static SortingVariations.Benchmarking.SearchAndSort.fillIntArray;
import static SortingVariations.Benchmarking.SearchAndSort.shuffle;

public class ParallelRecursiveMergeSort<T extends Comparable<T>> implements Sorter<T> {

    private final int threshold;
    private final boolean useParallelMerging;

    public ParallelRecursiveMergeSort(int threshold, boolean useParallelMerging){
        this.threshold=threshold;
        this.useParallelMerging=useParallelMerging;
    }


    @Override
    public void sort(T[] a) {
        ForkJoinPool pool = new ForkJoinPool();
        AtomicInteger comparisonCounter = new AtomicInteger(0);
        // invoke sort task
        T[] sorted = pool.invoke(new MergeSortTask<>(a, 0, a.length - 1, threshold,comparisonCounter,useParallelMerging));

        // copy it back to the original array.
        System.arraycopy(sorted, 0, a, 0, a.length);

        // Here we need to get the number of comparisons out to python somehow. Integer return instead of void?
        System.out.println(comparisonCounter.get());
        // or just print?

    }

    private static class MergeSortTask<T extends Comparable<T>> extends RecursiveTask<T[]> {
        private final T[] array;
        private final int low, high, threshold;
        private final AtomicInteger comparisonCounter;
        private final boolean useParallelMerging;

        public MergeSortTask(T[] array, int low, int high, int threshold,AtomicInteger comparisonCounter,boolean useParallelMerging) {
            this.array = array;
            this.low = low;
            this.high = high;
            this.threshold = threshold;
            this.comparisonCounter = comparisonCounter;
            this.useParallelMerging = useParallelMerging;
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
            MergeSortTask<T> leftTask = new MergeSortTask<>(array, low, mid, threshold,comparisonCounter,useParallelMerging);
            MergeSortTask<T> rightTask = new MergeSortTask<>(array, mid + 1, high, threshold,comparisonCounter,useParallelMerging);

            // fork and compute subtasks
            leftTask.fork();
            T[] rightResult = rightTask.compute(); // Compute the right half directly
            T[] leftResult = leftTask.join();      // Wait for the left half to finish

            // merge
            if (useParallelMerging) {
                return parallelMerge(leftResult, rightResult);
            } else {
                return sequentialMerge(leftResult, rightResult);
            }
        }

        // MErge method, without lo mid and high, could maybe use the same one as the recursive MergeSort but this was easier
        private T[] sequentialMerge(T[] left, T[] right) {
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

        private T[] parallelMerge(T[] left, T[] right) {
            T[] merged = java.util.Arrays.copyOf(left, left.length + right.length);
            int size = merged.length;

            int p = Math.min(ForkJoinPool.commonPool().getParallelism(), merged.length);
            // I believe this is a good way to find p, the getParallelism shoudl return the number of threads available
            int chunkSize = (merged.length + p - 1) / p; // i think we ahve to be rounding up when dividing the tasks (i think, check this)

            // ForkJoinPool pool = new ForkJoinPool();
            List<RecursiveAction> tasks = new ArrayList<>();


            for (int i = 0; i < p; i++) {
                int start = i * chunkSize;
                int end = Math.min(start + chunkSize - 1, size - 1);

                // Find indices for this chunk using twoSequenceSelect
                int[] startIndices = TwoSequenceSelect.twoSequenceSelect(left, right, start);
                int[] endIndices = TwoSequenceSelect.twoSequenceSelect(left, right, end + 1);

                int ia = startIndices[0];
                int ib = startIndices[1];
                int iaEnd = endIndices[0];
                int ibEnd = endIndices[1];

                // Create a task to merge this chunk
                tasks.add(new RecursiveAction() {

                    @Override
                    protected void compute() {
                        int i = start;

                        // Use an array to store mutable indices
                        int[] indices = {ia, ib};

                        while (indices[0] < iaEnd && indices[1] < ibEnd) {
                            comparisonCounter.incrementAndGet();
                            if (left[indices[0]].compareTo(right[indices[1]]) <= 0) {
                                merged[i++] = left[indices[0]++];
                            } else {
                                merged[i++] = right[indices[1]++];
                            }
                        }

                        while (indices[0] < iaEnd) merged[i++] = left[indices[0]++];
                        while (indices[1] < ibEnd) merged[i++] = right[indices[1]++];
                    }
                });

            }
            // Works statically i guess.
            ForkJoinTask.invokeAll(tasks);

            return merged;
        }


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
                        MergeSortTask<Integer> task = new MergeSortTask<>(intArray, 0, pSize - 1, threshold,comparisonCounter,false);
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
