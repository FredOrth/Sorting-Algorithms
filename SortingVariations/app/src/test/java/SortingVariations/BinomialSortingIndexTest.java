package SortingVariations;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import SortingVariations.Util.StableTestClass;


public class BinomialSortingIndexTest {
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
    public void BinomialIndexSortTest() {

        int k = testingArray1.length;
        Integer[] sortedArray = new Integer[k];

        for (int i = 0; i < k; i++) {
            sortedArray[i] = testingArray1[i];
            
        }
        
        Arrays.sort(sortedArray);
        BinomialSortIndex<Integer> bioAdaptive = new BinomialSortIndex<>(2, true);

        bioAdaptive.sort(testingArray1);

        for(int i = 0; i<testingArray1.length; i++){
            System.out.println(testingArray1[i]);
        }
        
        assertArrayEquals(sortedArray, testingArray1);

        //Reset
        setup();

        BinomialSortIndex bioNonAdaptive = new BinomialSortIndex<>(2, false);
        bioNonAdaptive.sort(testingArray1);
        assertArrayEquals(sortedArray, testingArray1);
    }

    @Test
    public void emptyTest(){
        BinomialSortIndex bioAdaptive = new BinomialSortIndex<>(5, true);
        bioAdaptive.sort(emptyArray);
        String[] emptyTestArray = {};
        assertEquals(emptyTestArray, emptyArray);

        BinomialSortIndex bioAdaptiveNonAdaptive = new BinomialSortIndex<>(5, false);
        bioAdaptiveNonAdaptive.sort(emptyArray);
        assertEquals(emptyTestArray, emptyArray);
    }

    @Test
    public void testUneven(){
        BinomialSortIndex<String> bioAdaptive = new BinomialSortIndex<>(3, true);
        String[] testArray = new String[unevenNumber.length];
        for(int i = 0; i<unevenNumber.length; i++){
            testArray[i] = unevenNumber[i];
        }
        Arrays.sort(testArray);
        bioAdaptive.sort(unevenNumber);

        assertEquals(testArray, unevenNumber);

        //Reset
        setup();

        BinomialSortIndex<String> bioNonAdaptive = new BinomialSortIndex<>(3, false);
        String[] testArray2 = new String[unevenNumber.length];
        for(int i = 0; i<unevenNumber.length; i++){
            testArray[i] = unevenNumber[i];
        }
        Arrays.sort(testArray);
        bioNonAdaptive.sort(unevenNumber);
    }

    @Test
    public void stableTest1(){
        BinomialSortIndex<StableTestClass> bioAdaptive = new BinomialSortIndex<>(3, true);
        
        bioAdaptive.sort(stableTest1);
        for(int i=0; i<stableTest1.length; i++){
            assertEquals(i, stableTest1[i].getTester());
        }

        //Reset
        setup();

        BinomialSortIndex<StableTestClass> bioNonAdaptive = new BinomialSortIndex<>(3, false);
        
        bioNonAdaptive.sort(stableTest1);
        for(int i=0; i<stableTest1.length; i++){
            assertEquals(i, stableTest1[i].getTester());
        }

    }

    @Test
    public void stableTest2(){
        BinomialSortIndex<StableTestClass> bioAdaptive = new BinomialSortIndex<>(4, true);
        
        bioAdaptive.sort(stableTest1);
        assertEquals(1,stableTest1[0].getComparable());
        assertEquals(1,stableTest1[1].getComparable());
        assertEquals(1,stableTest1[2].getComparable());

        assertEquals(0,stableTest1[0].getTester());
        assertEquals(1,stableTest1[1].getTester());
        assertEquals(2,stableTest1[2].getTester());

        //Reset
        setup();

        BinomialSortIndex<StableTestClass> bioNonAdaptive = new BinomialSortIndex<>(4, false);
        
        bioNonAdaptive.sort(stableTest1);
        assertEquals(1,stableTest1[0].getComparable());
        assertEquals(1,stableTest1[1].getComparable());
        assertEquals(1,stableTest1[2].getComparable());

        assertEquals(0,stableTest1[0].getTester());
        assertEquals(1,stableTest1[1].getTester());
        assertEquals(2,stableTest1[2].getTester());
    }

    @Test 
    public void stressTest(){
        BinomialSortIndex<Integer> bioAdaptive = new BinomialSortIndex<>(3, true);

        Integer[] stress1Test = new Integer[arr10000.length];
        for(int i = 0; i<arr10000.length; i++){
            stress1Test[i] = arr10000[i];
        }
        bioAdaptive.sort(arr10000);
        Arrays.sort(stress1Test);

        assertEquals(stress1Test, arr10000);

        //reset
        setupStressTest();

        BinomialSortIndex<Integer> bioNonAdaptive = new BinomialSortIndex<>(3, true);

        Integer[] stress1Test2 = new Integer[arr10000.length];
        for(int i = 0; i<arr10000.length; i++){
            stress1Test2[i] = arr10000[i];
        }
        bioNonAdaptive.sort(arr10000);
        Arrays.sort(stress1Test2);

        assertEquals(stress1Test2, arr10000);
        
    }   

    @Test
    public void stressTest2(){
        BinomialSortIndex<Integer> bioAdaptive = new BinomialSortIndex<>(3, true);

        Integer[] stress1Test = new Integer[arr100000.length];
        for(int i = 0; i<arr100000.length; i++){
            stress1Test[i] = arr100000[i];
        }
        bioAdaptive.sort(arr100000);
        Arrays.sort(stress1Test);

        assertEquals(stress1Test, arr100000);

        //reset
        setupStressTest();

        BinomialSortIndex<Integer> bioNonAdaptive = new BinomialSortIndex<>(3, true);

        Integer[] stress1Test2 = new Integer[arr100000.length];
        for(int i = 0; i<arr100000.length; i++){
            stress1Test2[i] = arr100000[i];
        }
        bioNonAdaptive.sort(arr100000);
        Arrays.sort(stress1Test2);

        assertEquals(stress1Test2, arr100000);
    }

    @Test
    public void stressTest3(){
        BinomialSortIndex<Integer> bioAdaptive = new BinomialSortIndex<>(3, true);

        Integer[] stress1Test = new Integer[arr1000000.length];
        for(int i = 0; i<arr1000000.length; i++){
            stress1Test[i] = arr1000000[i];
        }
        bioAdaptive.sort(arr1000000);
        Arrays.sort(stress1Test);

        assertEquals(stress1Test, arr1000000);

        //reset
        setupStressTest();

        BinomialSortIndex<Integer> bioNonAdaptive = new BinomialSortIndex<>(3, true);

        Integer[] stress1Test2 = new Integer[arr1000000.length];
        for(int i = 0; i<arr1000000.length; i++){
            stress1Test2[i] = arr1000000[i];
        }
        bioNonAdaptive.sort(arr1000000);
        Arrays.sort(stress1Test2);

        assertEquals(stress1Test2, arr1000000);
    }

}
