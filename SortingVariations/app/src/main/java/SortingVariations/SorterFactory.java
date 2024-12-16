package SortingVariations;

public class SorterFactory {
    public static <T extends Comparable<T>> Sorter<T> getSorter(String type, int cutoff,boolean useParallelMergesort) {
        // We could pass a cutoff or something to this ,ethod
        switch (type) {
            case "recursiveMergeSort":
                return new RecursiveMergeSort<>();
            // Lav flere cases nednefor, skal bare returne den classe vi vil nbruge

            case "insertionMergeSort":
                if (cutoff < 0) {
                    throw new IllegalArgumentException("Cutoff value required for insertion sort.");
                }
                return new InsertionMergeSort<>(cutoff);

            case "parallelRecursiveMergeSort":
                if (cutoff < 0) {
                    throw new IllegalArgumentException("Cutoff value required for parallelRecursiveMergeSort.");
                }
                return new ParallelRecursiveMergeSort<>(cutoff,useParallelMergesort);
            //for eksempel:
            // case "iterative":
            //     return new IterativeMergeSort<>();
            // case "hybrid":
            //     return new HybridMergeSort<>(cutoff);

            default:
                throw new IllegalArgumentException("Unknown sorting type: " + type);
        }
    }
}
