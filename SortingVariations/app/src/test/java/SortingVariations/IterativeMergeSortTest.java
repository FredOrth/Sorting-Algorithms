package SortingVariations;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import SortingVariations.Util.StableTestClass;

public class IterativeMergeSortTest {

    // private StableTestClass[] testingArray1;

    // IterativeMergeSort<StableTestClass> sorter = new IterativeMergeSort<>();
    IterativeMergeSort<Integer> sorter = new IterativeMergeSort<>();

    // probably irrelevant, as we should assume for all intense and purposes, that
    // the satck has at least 1 element.
    // @Test
    // public void testSortEmptyArray() {
    // StableTestClass[] array = new StableTestClass[]{};
    // sorter.sort(array);
    // assertArrayEquals(new StableTestClass[]{}, array);
    // }

    // @Test
    // public void testSortEmptyArray() {
    //     Integer[] array = new Integer[] {};
    //     sorter.sort(array);
    //     assertArrayEquals(new Integer[] {}, array);
    // }

    @Test
    public void testSortSingleElement() {
        Integer[] array = new Integer[] { 5 };
        sorter.sort(array);
        assertArrayEquals(new Integer[] { 5 }, array);
    }

    @Test
    public void testSortAlreadySorted() {
        Integer[] array = new Integer[] { 1, 2, 3, 4, 5 };
        sorter.sort(array);
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5 }, array);
    }

    @Test
    public void testSortReversed() {
        Integer[] array = new Integer[] { 5, 4, 3, 2, 1 };
        sorter.sort(array);
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5 }, array);
    }

    @Test
    public void testSortUnsortedArray() {
        Integer[] array = new Integer[] { 4, 1, 3, 5, 2 };
        sorter.sort(array);
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5 }, array);
    }

    @Test
    public void testSortArrayWithDuplicates() {
        Integer[] array = new Integer[] { 4, 2, 2, 4, 1 };
        sorter.sort(array);
        assertArrayEquals(new Integer[] { 1, 2, 2, 4, 4 }, array);
    }

    @Test
    public void testSortWithNegativeNumbers() {
        Integer[] array = new Integer[] { 3, -2, -1, 5, 0 };
        sorter.sort(array);
        assertArrayEquals(new Integer[] { -2, -1, 0, 3, 5 }, array);
    }

    @Test
    public void testSortWithLargeNumbers() {
        Integer[] array = new Integer[] { 1000, 200, 50, 5, 3000 };
        sorter.sort(array);
        assertArrayEquals(new Integer[] { 5, 50, 200, 1000, 3000 }, array);
    }


    //Using StableTestClass
    // @Before
    // public void setup() {
    //     testingArray1 = new StableTestClass[]{
    //         new StableTestClass(76, 1),
    //         new StableTestClass(3, 2),
    //         new StableTestClass(16, 3),
    //         new StableTestClass(3, 4),
    //         new StableTestClass(1, 5),
    //         new StableTestClass(94, 6),
    //         new StableTestClass(1, 7),
    //         new StableTestClass(6, 8),
    //         new StableTestClass(34, 9),
    //         new StableTestClass(4, 10),
    //         new StableTestClass(4, 11),
    //         new StableTestClass(4, 12),
    //         new StableTestClass(4, 13),
    //         new StableTestClass(4, 14),
    //         new StableTestClass(5, 15)
    //     };
    // }

    // @Test
    // public void iterativeMergeSortTest() {
    //     // Copy the original array to create an expected sorted version
    //     int k = testingArray1.length;
    //     StableTestClass[] sortedArray = new StableTestClass[k];
    //     for (int i = 0; i < k; i++) {
    //         sortedArray[i] = testingArray1[i];
    //     }

    //     // Sort the copied array using a stable sorting algorithm
    //     Arrays.sort(sortedArray, (a, b) -> b.compareTo(a)); // Matches the descending order in compareTo

    //     // Create the sorter and sort the testing array
    //     IterativeMergeSort<StableTestClass> sorter = new IterativeMergeSort<>();
    //     sorter.sort(testingArray1);

    //     // Assert that the sorted array matches the expected sorted version
    //     assertArrayEquals(sortedArray, testingArray1);
    // }

}
