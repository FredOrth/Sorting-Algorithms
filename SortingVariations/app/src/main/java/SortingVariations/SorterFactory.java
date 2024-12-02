package SortingVariations;

public class SorterFactory {
    public static <T extends Comparable<T>> Sorter<T> getSorter(String type) {
        // We could pass a cutoff or something to this ,ethod
        switch (type.toLowerCase()) {
            case "recursive":
                return new RecursiveMergeSort<>();
            // Lav flere cases nednefor, skal bare returne den classe vi vil nbruge

            //for eksempel:
            // case "iterative":
            //     return new IterativeMergeSort<>();
            // case "hybrid":
            //     return new HybridMergeSort<>(cutoff);
            //Whatever, virker måske ikke
            default:
                throw new IllegalArgumentException("Unknown sorting type: " + type);
        }
    }
}
