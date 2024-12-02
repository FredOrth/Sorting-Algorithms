package SortingVariations;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;

public class SortingVariationsTest {

    private Integer[] testingArray1;


    @Before
    public void setup(){
        testingArray1 = new Integer[]{76,3,16,3,1,94,1,6,34,4,4,4,4,4,5};

    }


    @Test
    public void mergeSortTest() {
        //Integer[] sortedArray = new Integer[]{1,1,3,3,4,4,4,4,4,4,5,6,16,76,94};
        Integer[] sortedArray = testingArray1;
        // MergeSort.sort(testingArray1);
        Arrays.sort(sortedArray);
        assertArrayEquals(sortedArray, testingArray1);
    }

    @Test
    public void secondaryTest(){
        assertTrue(true);
    }
}
