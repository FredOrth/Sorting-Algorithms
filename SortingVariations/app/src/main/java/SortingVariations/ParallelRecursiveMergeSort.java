package SortingVariations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import SortingVariations.Benchmarking.*;
import SortingVariations.Util.TwoSequenceSelect;

public class ParallelRecursiveMergeSort<T extends Comparable<T>> implements Sorter<T> {

    private final int threshold;
    private final boolean useParallelMerging;
    private final int numberOfThreads;

    public ParallelRecursiveMergeSort(int threshold, boolean useParallelMerging, int numberOfThreads) {
        this.threshold=threshold;
        this.useParallelMerging=useParallelMerging;
        this.numberOfThreads=numberOfThreads;
    }


    @Override
    public Integer sort(T[] a) {
        ForkJoinPool pool = (numberOfThreads > 0)
                ? new ForkJoinPool(numberOfThreads) // Custom number of threads
                : new ForkJoinPool(); // Use default parallelism (available cores)
        AtomicInteger comparisonCounter = new AtomicInteger(0);
        // invoke sort task
        T[] sorted = pool.invoke(new MergeSortTask<>(a, 0, a.length - 1, threshold,comparisonCounter,useParallelMerging,numberOfThreads));
        pool.shutdown();
        // copy it back to the original array.
        System.arraycopy(sorted, 0, a, 0, a.length);

        // Here we need to get the number of comparisons out to python somehow. Integer return instead of void?
        return comparisonCounter.get();
        // or just print?

    }

    private static class MergeSortTask<T extends Comparable<T>> extends RecursiveTask<T[]> {
        private final T[] array;
        private final int low, high, threshold;
        private final AtomicInteger comparisonCounter;
        private final boolean useParallelMerging;
        private int numberOfThreads;

        public MergeSortTask(T[] array, int low, int high, int threshold,AtomicInteger comparisonCounter,boolean useParallelMerging,int numberOfThreads) {
            this.array = array;
            this.low = low;
            this.high = high;
            this.threshold = threshold;
            this.comparisonCounter = comparisonCounter;
            this.useParallelMerging = useParallelMerging;
            this.numberOfThreads=numberOfThreads;
        }


        @Override
        protected T[] compute() {
            if (high - low + 1 <= threshold) {
                // Sequential sort for small subarrays
                // we can play which sequential algorithm we use here
                T[] sorted = java.util.Arrays.copyOfRange(array, low, high + 1);

                RecursiveMergeSort<T> sequentialSorter = new RecursiveMergeSort<>();
                /*InsertionSort sequentialSorter = new InsertionSort();*/

                comparisonCounter.addAndGet(sequentialSorter.sort(sorted));
                return sorted;
            }

            // Split
            int mid = low + (high - low) / 2;

            // make subtasks
            MergeSortTask<T> leftTask = new MergeSortTask<>(array, low, mid, threshold,comparisonCounter,useParallelMerging,numberOfThreads);
            MergeSortTask<T> rightTask = new MergeSortTask<>(array, mid + 1, high, threshold,comparisonCounter,useParallelMerging,numberOfThreads);

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

            int availableThreads = (numberOfThreads > 0) ? numberOfThreads : Runtime.getRuntime().availableProcessors();

            // A simple heuristic could be something like:
            // p grows slowly with size, but not beyond availableThreads.
            // for example, p = min(availableThreads, 1 + (int)Math.log10(size)). we go wit hthat

                        int p = Math.min(availableThreads, 1 + (int)Math.log10(size));
            // Ensure at least 1 partition and never exceed the number of threads
            p = Math.max(1, p);
            // I believe this is a good way to find p, the getParallelism shoudl return the number of threads available
            int chunkSize = (merged.length + p - 1) / p; // i think we ahve to be rounding up when dividing the tasks (i think, check this)

            // ForkJoinPool pool = new ForkJoinPool();
            List<RecursiveAction> tasks = new ArrayList<>();


            for (int i = 0; i < p; i++) {
                int start = i * chunkSize;
                int end = Math.min(start + chunkSize - 1, size - 1);

                // Find indices forthe chunk with twoSequenceSelect
                int[] startIndices = TwoSequenceSelect.twoSequenceSelect(left, right, start);
                int[] endIndices = TwoSequenceSelect.twoSequenceSelect(left, right, end + 1);

                int ia = startIndices[0];
                int ib = startIndices[1];
                int iaEnd = endIndices[0];
                int ibEnd = endIndices[1];

                // adding the task to merge this chunk
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

    public static void runCutoffBenchmark(ForkJoinPool pool, int pSize, int[] thresholds, int n,boolean useParallelMerging,int numberOfThreads) {


        System.out.printf("%-15s %-15s %-15s%n", "Cutoff", "Time (ns)", "Variation (ns)"); // Print table headers

        final Integer[] intArray = new Integer[pSize];
        for (int i = 0; i < pSize; i++) {
            intArray[i] = i;
        }

        for (int threshold : thresholds) {
            Benchmarkable benchmarkable = new Benchmarkable() {
                private AtomicInteger comparisonCounter;

                @Override
                public void setup() {
                    java.util.Collections.shuffle(java.util.Arrays.asList(intArray));
                    comparisonCounter = new AtomicInteger(numberOfThreads);
                }

                @Override
                public double applyAsDouble(int i) {
                    MergeSortTask<Integer> task = new MergeSortTask<>(
                            intArray, 0, pSize - 1, threshold, comparisonCounter, useParallelMerging,numberOfThreads);
                    pool.invoke(task);
                    return 0.0; // Dummy return value
                }
            };

            // Run the benchmark and capture the mean and variation
            double[] result = Benchmark.runMark8WithStats("ParallelMergeSort", benchmarkable, n, 0.25);

            double meanTime = result[0]; // Mean execution time
            double variation = result[1]; // Standard deviation (variation)

            System.out.printf("%-15d %-15.2f %-15.2f%n", threshold, meanTime, variation);
        }

    }

    public static void benchmarkSortingWithSetup(Integer[] inputArray, int threshold, int numberOfThreads) {
        int n = inputArray.length;

        // Benchmark ParallelRecursiveMergeSort
        Benchmark.Mark8Setup("ParallelRecursiveMergeSort", "n=" + n + ", threshold=" + threshold,
                new Benchmarkable() {
                    private Integer[] arrayCopy;

                    @Override
                    public void setup() {
                        // Reset the array before each iteration
                        arrayCopy = inputArray.clone();
                    }

                    @Override
                    public double applyAsDouble(int i) {
                        ForkJoinPool pool = (numberOfThreads > 0)
                                ? new ForkJoinPool(numberOfThreads) // Custom number of threads
                                : new ForkJoinPool(); // Use default parallelism (available cores)
                        try {
                            ParallelRecursiveMergeSort<Integer> parallelSort =
                                    new ParallelRecursiveMergeSort<>(threshold, false, numberOfThreads);
                            pool.invoke(new ParallelRecursiveMergeSort.MergeSortTask<>(
                                    arrayCopy, 0, arrayCopy.length - 1, threshold, new AtomicInteger(0), false,numberOfThreads));
                        } finally {
                            pool.shutdown(); // Cleanup ForkJoinPool threads
                        }
                        return 0.0; // Dummy return value
                    }
                });

        // Benchmark Arrays.parallelSort
        Benchmark.Mark8Setup("Arrays.parallelSort", "n=" + n + ", threshold=" + threshold,
                new Benchmarkable() {
                    private Integer[] arrayCopy;

                    @Override
                    public void setup() {
                        // Reset the array before each iteration
                        arrayCopy = inputArray.clone();
                    }

                    @Override
                    public double applyAsDouble(int i) {
                        Arrays.parallelSort(arrayCopy);
                        return 0.0; // Dummy return value
                    }
                });
    }


    public static void benchmarkSortingWithThreads(Integer[] inputArray, int threshold, int[] threadCounts) {
        int n = inputArray.length;

        for (int threadCount : threadCounts) {
            // Benchmark ParallelRecursiveMergeSort
            Benchmark.Mark8Setup("ParallelRecursiveMergeSort", "n=" + n + ", threshold=" + threshold + ", threads=" + threadCount,
                    new Benchmarkable() {
                        private Integer[] arrayCopy;

                        @Override
                        public void setup() {
                            // Reset the array before each iteration
                            arrayCopy = inputArray.clone();
                        }

                        @Override
                        public double applyAsDouble(int i) {
                            ForkJoinPool pool = (threadCount > 0)
                                    ? new ForkJoinPool(threadCount) // Custom number of threads
                                    : new ForkJoinPool(); // Use default parallelism (available cores)
                            try {
                                ParallelRecursiveMergeSort<Integer> parallelSort =
                                        new ParallelRecursiveMergeSort<>(threshold, true, threadCount);
                                pool.invoke(new ParallelRecursiveMergeSort.MergeSortTask<>(
                                        arrayCopy, 0, arrayCopy.length - 1, threshold, new AtomicInteger(0), true,threadCount));
                            } finally {
                                pool.shutdown(); // Cleanup ForkJoinPool threads
                            }
                            return 0.0; // Dummy return value
                        }
                    });
        }

        // Benchmark Arrays.parallelSort
        Benchmark.Mark8Setup("Arrays.parallelSort", "n=" + n + ", threshold=" + threshold,
                new Benchmarkable() {
                    private Integer[] arrayCopy;

                    @Override
                    public void setup() {
                        // Reset the array before each iteration
                        arrayCopy = inputArray.clone();
                    }

                    @Override
                    public double applyAsDouble(int i) {
                        Arrays.parallelSort(arrayCopy);
                        return 0.0; // Dummy return value
                    }
                });
    }

    public static Integer[] generateRandomArray(int size, int maxValue) {
        Random random = new Random();
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(maxValue);
        }
        return array;
    }

}
