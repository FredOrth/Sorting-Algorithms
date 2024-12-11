package SortingVariations;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import SortingVariations.Util.StableTestClass;

public class BinomialSortingTest {
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
    public void BinomialSortTest() {

        int k = testingArray1.length;
        Integer[] sortedArray = new Integer[k];

        for (int i = 0; i < k; i++) {
            sortedArray[i] = testingArray1[i];
            
        }
        
        Arrays.sort(sortedArray);
        BinomialSort<Integer> bioAdaptive = new BinomialSort<>(2, true);

        bioAdaptive.sort(testingArray1);
        
        assertArrayEquals(sortedArray, testingArray1);

        //Reset
        setup();

        BinomialSort bioNonAdaptive = new BinomialSort<>(2, false);
        bioNonAdaptive.sort(testingArray1);
        assertArrayEquals(sortedArray, testingArray1);
    }

    @Test
    public void emptyTest(){
        BinomialSort bioAdaptive = new BinomialSort<>(5, true);
        bioAdaptive.sort(emptyArray);
        String[] emptyTestArray = {};
        assertEquals(emptyTestArray, emptyArray);

        BinomialSort bioAdaptiveNonAdaptive = new BinomialSort<>(5, false);
        bioAdaptiveNonAdaptive.sort(emptyArray);
        assertEquals(emptyTestArray, emptyArray);
    }

    @Test
    public void testUneven(){
        BinomialSort<String> bioAdaptive = new BinomialSort<>(3, true);
        String[] testArray = new String[unevenNumber.length];
        for(int i = 0; i<unevenNumber.length; i++){
            testArray[i] = unevenNumber[i];
        }
        Arrays.sort(testArray);
        bioAdaptive.sort(unevenNumber);

        assertEquals(testArray, unevenNumber);

        //Reset
        setup();

        BinomialSort<String> bioNonAdaptive = new BinomialSort<>(3, false);
        String[] testArray2 = new String[unevenNumber.length];
        for(int i = 0; i<unevenNumber.length; i++){
            testArray[i] = unevenNumber[i];
        }
        Arrays.sort(testArray);
        bioNonAdaptive.sort(unevenNumber);
    }

    @Test
    public void stableTest1(){
        BinomialSort<StableTestClass> bioAdaptive = new BinomialSort<>(3, true);
        
        bioAdaptive.sort(stableTest1);
        for(int i=0; i<stableTest1.length; i++){
            assertEquals(i, stableTest1[i].getTester());
        }

        //Reset
        setup();

        BinomialSort<StableTestClass> bioNonAdaptive = new BinomialSort<>(3, false);
        
        bioNonAdaptive.sort(stableTest1);
        for(int i=0; i<stableTest1.length; i++){
            assertEquals(i, stableTest1[i].getTester());
        }

    }

    @Test
    public void stableTest2(){
        BinomialSort<StableTestClass> bioAdaptive = new BinomialSort<>(4, true);
        
        bioAdaptive.sort(stableTest1);
        assertEquals(1,stableTest1[0].getComparable());
        assertEquals(1,stableTest1[1].getComparable());
        assertEquals(1,stableTest1[2].getComparable());

        assertEquals(0,stableTest1[0].getTester());
        assertEquals(1,stableTest1[1].getTester());
        assertEquals(2,stableTest1[2].getTester());

        //Reset
        setup();

        BinomialSort<StableTestClass> bioNonAdaptive = new BinomialSort<>(4, false);
        
        bioNonAdaptive.sort(stableTest1);
        assertEquals(1,stableTest1[0].getComparable());
        assertEquals(1,stableTest1[1].getComparable());
        assertEquals(1,stableTest1[2].getComparable());

        assertEquals(0,stableTest1[0].getTester());
        assertEquals(1,stableTest1[1].getTester());
        assertEquals(2,stableTest1[2].getTester());
    }


}
