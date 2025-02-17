package SortingVariations;

import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;

public class BioLevelDiffTest {
    Integer[] testingArray1;
    
    @Before
    public void setup(){
        testingArray1 = new Integer[]{9,8,7,6,5,4,3,2,1,2,3,4,5,10,2,3,4,5,6,3,2,1,0,-1};
        //The idea of this array is to show the difference between bio and level sort
        //the four subarrays = [1,2,3,4], [10,9,8,7,6,5,4,3,2,1], [1,2,3,4,5,6], [3,2,1,0,-1]
        //levelsort = sub1 = level (4/2 xor 9/2) = 3, sub2 = level (9/2 xor 6/2) = 3, sub3 = level (6/2 xor 5/2) = 1
        //levelsort merge sub 3 and 4, then sub 3 (merged with 4) and 2, 2 (m) with 1
        //bio merge sub1 and sub2, then sub 3 and 4, at last the two merged subarrays 

    }

    @Test
    public void test1(){
        BinomialSortIndex bio1 = new BinomialSortIndex<>(1, true);
        LevelSortIndex l1 = new LevelSortIndex<>(1, true);

        int bioSortComp = bio1.sort(testingArray1);

        setup();

        int levelSortComp = l1.sort(testingArray1);

        assertNotEquals(bioSortComp, levelSortComp);
    }

}
