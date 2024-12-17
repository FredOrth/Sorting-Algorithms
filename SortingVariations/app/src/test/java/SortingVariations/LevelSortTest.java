package SortingVariations;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import SortingVariations.Util.StableTestClass;

public class LevelSortTest {
    private Integer[] testingArray1;
    private String[] emptyArray;
    private String[] unevenNumber;
    private StableTestClass[] stableTest1;
    private StableTestClass[] stableTest2;
    private Integer[] arr10000;
    private Integer[] arr100000;
    private Integer[] arr1000000;

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

    //-2147483648 to 2147483647
    @Before
    public void setupStressTest(){
        Random random = new Random();
        this.arr10000 = new Integer[10000];
        for(int i = 0; i<10000; i++){
            arr10000[i] = random.nextInt(-2147483647, 2147483646);
        }

        this.arr100000 = new Integer[100000];
        for(int i = 0; i<100000; i++){
            arr100000[i] = random.nextInt(-2147483647, 2147483646);
        }

        this.arr1000000 = new Integer[1000000];
        for(int i = 0; i<1000000; i++){
            arr1000000[i] = random.nextInt(-2147483647, 2147483646);
        }
    }

        @Test
    public void LevelSortAdaptiveTest() {

        int k = testingArray1.length;
        Integer[] sortedArray = new Integer[k];

        for (int i = 0; i < k; i++) {
            sortedArray[i] = testingArray1[i];
            
        }
        
        Arrays.sort(sortedArray);

        // instance with adaptive sorting mode (c=1)
        LevelSortIndex<Integer> adaptiveLevelSortIndex = new LevelSortIndex<>(8, true); // c arugment set to 1, as that would be the same as not using it, IE. the base-case.

        adaptiveLevelSortIndex.sort(testingArray1);
        
        assertArrayEquals(sortedArray, testingArray1);
    }

    @Test
    public void emptyTest(){
        LevelSortIndex<String> levelAdaptive = new LevelSortIndex<>(8, true);
        levelAdaptive.sort(emptyArray);
        String[] emptyTestArray = {};
        assertEquals(emptyTestArray, emptyArray);
        
        LevelSortIndex<String> levelNonAdaptive = new LevelSortIndex<>(8, false);
        levelNonAdaptive.sort(emptyArray);
        assertEquals(emptyTestArray, emptyArray);
    }

    @Test
    public void testUneven(){
        LevelSortIndex<String> levelAdaptive = new LevelSortIndex<>(8, true);
        String[] testArray = new String[unevenNumber.length];
        for(int i = 0; i<unevenNumber.length; i++){
            testArray[i] = unevenNumber[i];
        }
        Arrays.sort(testArray);
        levelAdaptive.sort(unevenNumber);
        assertEquals(testArray, unevenNumber);

        //Reset
        setup();

        LevelSortIndex<String> levelNonAdaptive = new LevelSortIndex<>(8, false);
        String[] testArray2 = new String[unevenNumber.length];
        for(int i = 0; i<unevenNumber.length; i++){
            testArray[i] = unevenNumber[i];
        }
        Arrays.sort(testArray);
        levelNonAdaptive.sort(unevenNumber);
        assertArrayEquals(testArray, unevenNumber);
    }

    @Test
    public void stableTest1(){
        LevelSortIndex<StableTestClass> lvlAdaptive = new LevelSortIndex<>(8, true);
        lvlAdaptive.sort(stableTest1);
        for(int i=0; i<stableTest1.length; i++){
            assertEquals(i, stableTest1[i].getTester());
        }

        //Reset
        setup();

        LevelSortIndex<StableTestClass> lvlNonAdaptive = new LevelSortIndex<>(8, false);
        lvlNonAdaptive.sort(stableTest1);
        for(int i=0; i<stableTest1.length; i++){
            assertEquals(i, stableTest1[i].getTester());
        }
    }

    
    @Test
    public void stableTest2(){
        LevelSortIndex<StableTestClass> lvlAdaptive = new LevelSortIndex<>(8, true);
        
        lvlAdaptive.sort(stableTest1);
        assertEquals(1,stableTest1[0].getComparable());
        assertEquals(1,stableTest1[1].getComparable());
        assertEquals(1,stableTest1[2].getComparable());
        
        assertEquals(0,stableTest1[0].getTester());
        assertEquals(1,stableTest1[1].getTester());
        assertEquals(2,stableTest1[2].getTester());
        
        //Reset
        setup();
        
        LevelSortIndex<StableTestClass> lvlNonAdaptive = new LevelSortIndex<>(8, false);
        
        lvlNonAdaptive.sort(stableTest1);
        assertEquals(1,stableTest1[0].getComparable());
        assertEquals(1,stableTest1[1].getComparable());
        assertEquals(1,stableTest1[2].getComparable());
        
        assertEquals(0,stableTest1[0].getTester());
        assertEquals(1,stableTest1[1].getTester());
        assertEquals(2,stableTest1[2].getTester());
    }
    
    // @Test
    // public void stableTest3() {
    //     LevelSortIndex<StableTestClass> lvlAdaptive = new LevelSortIndex<>(8, true);
    //     lvlAdaptive.sort(stableTest2);
    //     for (int i = 0; i < stableTest2.length; i++) {
    //         assertEquals(i, stableTest2[i].getTester());
    //     }

    //     // Reset
    //     setup();

    //     LevelSortIndex<StableTestClass> lvlNonAdaptive = new LevelSortIndex<>(8, false);
    //     lvlNonAdaptive.sort(stableTest1);
    //     for (int i = 0; i < stableTest2.length; i++) {
    //         assertEquals(i, stableTest2[i].getTester());
    //     }
    // }


    @Test 
    public void stressTest(){
            LevelSortIndex<Integer> lvlAdaptive = new LevelSortIndex<>(8, true);
        
        Integer[] stress1Test = new Integer[arr10000.length];
        for(int i = 0; i<arr10000.length; i++){
            stress1Test[i] = arr10000[i];
        }
        lvlAdaptive.sort(arr10000);
        Arrays.sort(stress1Test);

        assertEquals(stress1Test, arr10000);

        //reset
        setupStressTest();

        LevelSortIndex<Integer> lvlNonAdaptive = new LevelSortIndex<>(8, false);

        Integer[] stress1Test2 = new Integer[arr10000.length];
        for(int i = 0; i<arr10000.length; i++){
            stress1Test2[i] = arr10000[i];
        }
        lvlNonAdaptive.sort(arr10000);
        Arrays.sort(stress1Test2);

        assertEquals(stress1Test2, arr10000);
        
    }   

    @Test
    public void stressTest2(){
        LevelSortIndex<Integer> lvlAdaptive = new LevelSortIndex<>(8, true);

        Integer[] stress1Test = new Integer[arr100000.length];
        for(int i = 0; i<arr100000.length; i++){
            stress1Test[i] = arr100000[i];
        }
        lvlAdaptive.sort(arr100000);
        Arrays.sort(stress1Test);

        assertEquals(stress1Test, arr100000);

        //reset
        setupStressTest();

        LevelSortIndex<Integer> lvlNonAdaptive = new LevelSortIndex<>(8, false);

        Integer[] stress1Test2 = new Integer[arr100000.length];
        for(int i = 0; i<arr100000.length; i++){
            stress1Test2[i] = arr100000[i];
        }
        lvlNonAdaptive.sort(arr100000);
        Arrays.sort(stress1Test2);

        assertEquals(stress1Test2, arr100000);
    }

    @Test
    public void stressTest3(){
        LevelSortIndex<Integer> lvlAdaptive = new LevelSortIndex<>(8, true);

        Integer[] stress1Test = new Integer[arr1000000.length];
        for(int i = 0; i<arr1000000.length; i++){
            stress1Test[i] = arr1000000[i];
        }
        lvlAdaptive.sort(arr1000000);
        Arrays.sort(stress1Test);

        assertEquals(stress1Test, arr1000000);

        //reset
        setupStressTest();

        LevelSortIndex<Integer> lvlNonAdaptive = new LevelSortIndex<>(8, false);

        Integer[] stress1Test2 = new Integer[arr1000000.length];
        for(int i = 0; i<arr1000000.length; i++){
            stress1Test2[i] = arr1000000[i];
        }
        lvlNonAdaptive.sort(arr1000000);
        Arrays.sort(stress1Test2);

        assertEquals(stress1Test2, arr1000000);
    }

    /* Niche tests for levelSort specifically.
     *  Adherence to lvls etc.
     */

    @Test
    public void testMergingAtLevels() {
        Integer[] input = {1, 2, 3, 7, 6, 5, 4}; // two runs [1, 2, 3], [7, 6, 5, 4]
        LevelSortIndex<Integer> lvlAdaptive = new LevelSortIndex<>(8, true);
        lvlAdaptive.sort(input);
        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6, 7}, input);

        Integer[] input2 = { 1, 2, 3, 7, 6, 5, 4 }; // two runs [1, 2, 3], [7, 6, 5, 4]
        LevelSortIndex<Integer> lvlNonAdaptive = new LevelSortIndex<>(8, false);
        lvlNonAdaptive.sort(input2);
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6, 7 }, input2);
    }

    @Test
    public void testNaturalRuns() {
        Integer[] input = { 1, 2, 3, 8, 7, 6, 5 }; // Ascending then descending
        LevelSortIndex<Integer> lvlAdaptive = new LevelSortIndex<>(4, true);
        lvlAdaptive.sort(input);
        assertArrayEquals(new Integer[] { 1, 2, 3, 5, 6, 7, 8 }, input);

        Integer[] input2 = { 1, 2, 3, 8, 7, 6, 5 }; // Ascending then descending
        LevelSortIndex<Integer> lvlNonAdaptive = new LevelSortIndex<>(4, false);
        lvlNonAdaptive.sort(input2);
        assertArrayEquals(new Integer[] { 1, 2, 3, 5, 6, 7, 8 }, input2);
    }

    @Test // small runs and cutoff behaviour. Input size below or exactly at the cutoff threshold.
    public void testSmallRunCutoff() {
        Integer[] input = { 5, 2, 4 }; // Array smaller than cutoff (assumed 4)
        LevelSortIndex<Integer> lvlAdaptive = new LevelSortIndex<>(4, true);
        lvlAdaptive.sort(input);
        assertArrayEquals(new Integer[] { 2, 4, 5 }, input);

        Integer[] input2 = { 5, 2, 4 }; // Array smaller than cutoff (assumed 4)
        LevelSortIndex<Integer> lvlNonAdaptive = new LevelSortIndex<>(4, false);
        lvlNonAdaptive.sort(input2);
        assertArrayEquals(new Integer[] { 2, 4, 5 }, input2);
    }


    @Test
    public void testSingleElementRuns() { // single element runs
        Integer[] input = { 5, 1, 8, 2, 9 }; // All elements could initially be single runs
        LevelSortIndex<Integer> lvlAdaptive = new LevelSortIndex<>(8, true);
        lvlAdaptive.sort(input);
        assertArrayEquals(new Integer[] { 1, 2, 5, 8, 9 }, input);

        Integer[] input2 = { 5, 1, 8, 2, 9 }; // All elements could initially be single runs
        LevelSortIndex<Integer> lvlNonAdaptive = new LevelSortIndex<>(8, false);
        lvlNonAdaptive.sort(input2);
        assertArrayEquals(new Integer[] { 1, 2, 5, 8, 9 }, input2);
    }

    @Test
    public void testComplexRunStack() {
    Integer[] input = {1, 2, 3, 7, 6, 5, 4, 8, 9}; // Alternating runs
    LevelSortIndex<Integer> lvlAdaptive = new LevelSortIndex<>(8, true);
    lvlAdaptive.sort(input);
    assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, input);

    Integer[] input2 = {1, 2, 3, 7, 6, 5, 4, 8, 9}; // Alternating runs
    LevelSortIndex<Integer> lvlNonAdaptive = new LevelSortIndex<>(8, false);
    lvlNonAdaptive.sort(input2);
    assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, input2);

}

}
