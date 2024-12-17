package SortingVariations;

import SortingVariations.Util.StableTestClass;
import SortingVariations.Util.TwoSequenceSelect;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ParallelRecursiveMergeSortTest {

    private Integer[] testingArray1;
    private Integer[] testingArray2;
    private Integer[] testingArray3;
    private String[] emptyArray;
    private String[] unevenNumber;
    private StableTestClass[] stableTest1;
    private StableTestClass[] stableTest2;

    @Before
    public void setup(){
        testingArray1 = new Integer[]{76,3,16,3,1,94,1,6,34,4,4,4,4,4,5};
        testingArray2 = new Integer[]{10, 2, 2, 9, 1, 15, 15, 3, 8, 8, 8, 22, 0, -1, 100};
        testingArray3 = new Integer[]{100, 95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 30,100, 95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 30,100, 95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 30,100, 95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 30};
        emptyArray = new String[]{};
        unevenNumber = new String[] {"Hello", "Heyo", "And", "Anders", "Polution", "And", "Pull"};
        stableTest1 = new StableTestClass[10];
        for(int i = 0; i<10; i++){
            StableTestClass s = new StableTestClass(1, i);
            stableTest1[i] = s;
        }
        stableTest2 = new StableTestClass[15];
        for(int i = 0; i<stableTest2.length; i++){
            if(i%5 == 0){
                StableTestClass s = new StableTestClass(1, i/5);
                stableTest2[i] = s;
            }else{
                StableTestClass s = new StableTestClass(9, i/5);
                stableTest2[i] = s;
            }
        }
    }

    @Test
    public void parallelTest() {

        int k = testingArray1.length;
        Integer[] sortedArray = new Integer[k];

        System.arraycopy(testingArray1, 0, sortedArray, 0, k);

        Arrays.sort(sortedArray);
        ParallelRecursiveMergeSort<Integer> parallelSort = new ParallelRecursiveMergeSort<>(10,false,0);


        parallelSort.sort(testingArray1);

        assertArrayEquals(sortedArray, testingArray1);

    }

    @Test
    public void twoSequenceSelectSmallArrayTest(){
        int[] aibi = TwoSequenceSelect.twoSequenceSelect(new Integer[]{1, 3, 5}, new Integer[]{2, 4, 6}, 4);

        // Extract ja and jb from the result
        int ja = aibi[0];
        int jb = aibi[1];

        // Arrays a and b
        Integer[] a = new Integer[]{1, 3, 5};
        Integer[] b = new Integer[]{2, 4, 6};

        // Find the k-th element
        Integer kthElement;
        if (ja > 0 && (jb == 0 || a[ja - 1] <= b[jb - 1])) {
            kthElement = b[jb - 1]; // Take from b
        } else {
            kthElement = a[ja - 1]; // Take from a
        }

        Assert.assertEquals((Integer)4,kthElement);

    }

    @Test
    public void twoSequenceSelectLargerArrayTest(){

        int[] aibi = TwoSequenceSelect.twoSequenceSelect(new Integer[]{1, 2,3,4, 5,6,7,8,9,10,11,12,13,14,19}, new Integer[]{2, 4, 6,13}, 16);

        // Extract ja and jb from the result
        int ja = aibi[0];
        int jb = aibi[1];

        // Arrays a and b
        Integer[] a = new Integer[]{1, 2,3,4, 5,6,7,8,9,10,11,12,13,14,19};
        Integer[] b = new Integer[]{2, 4, 6,13};

        // Find the k-th element
        Integer kthElement;
        if (ja > 0 && (jb == 0 || a[ja - 1] <= b[jb - 1])) {
            kthElement = b[jb - 1]; // Take from b
        } else {
            kthElement = a[ja - 1]; // Take from a
        }

        Assert.assertEquals((Integer)13,kthElement);

    }

    @Test
    public void parallelTestWithParallelMergeSort() {

        int k = testingArray3.length;
        Integer[] sortedArray = new Integer[k];

        System.arraycopy(testingArray3, 0, sortedArray, 0, k);

        Arrays.sort(sortedArray);
        ParallelRecursiveMergeSort<Integer> parallelSort = new ParallelRecursiveMergeSort<>(1,true,0);


        System.out.println(parallelSort.sort(testingArray3));

        assertArrayEquals(sortedArray, testingArray3);

    }

    @Test
    public void testWithPythonArray() {
        // simulate random array from Python
        Integer[] inputArray = new Integer[]{76, 3, 16, 3, 1, 94, 1, 6, 34, 4, 4, 4, 4, 4, 5};

        // threshold = 10, Number of threads = 4
        ParallelRecursiveMergeSort.benchmarkSortingWithSetup(inputArray,100,0);
    }



}
