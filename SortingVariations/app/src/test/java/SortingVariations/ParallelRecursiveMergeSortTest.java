package SortingVariations;

import SortingVariations.Util.StableTestClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class ParallelRecursiveMergeSortTest {

    private Integer[] testingArray1;
    private String[] emptyArray;
    private String[] unevenNumber;
    private StableTestClass[] stableTest1;
    private StableTestClass[] stableTest2;

    @Before
    public void setup(){
        testingArray1 = new Integer[]{76,3,16,3,1,94,1,6,34,4,4,4,4,4,5};
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
        ParallelRecursiveMergeSort<Integer> parallelSort = new ParallelRecursiveMergeSort<>(10);


        parallelSort.sort(testingArray1);

        assertArrayEquals(sortedArray, testingArray1);

    }
}
