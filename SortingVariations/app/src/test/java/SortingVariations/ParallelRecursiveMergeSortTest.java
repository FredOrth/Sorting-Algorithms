package SortingVariations;

import SortingVariations.Util.StableTestClass;
import SortingVariations.Util.TwoSequenceSelect;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

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
    public void parallelTestIntegerArray1() {

        int k = testingArray1.length;
        Integer[] sortedArray = new Integer[k];

        System.arraycopy(testingArray1, 0, sortedArray, 0, k);

        Arrays.sort(sortedArray);
        ParallelRecursiveMergeSort<Integer> parallelSort = new ParallelRecursiveMergeSort<>(10,false,0);


        parallelSort.sort(testingArray1);

        assertArrayEquals(sortedArray, testingArray1);

    }

    @Test
    public void parallelTestIntegerArray2() {

        int k = testingArray2.length;
        Integer[] sortedArray = new Integer[k];

        System.arraycopy(testingArray2, 0, sortedArray, 0, k);

        Arrays.sort(sortedArray);
        ParallelRecursiveMergeSort<Integer> parallelSort = new ParallelRecursiveMergeSort<>(10,false,0);


        parallelSort.sort(testingArray2);

        assertArrayEquals(sortedArray, testingArray2);

    }

    @Test
    public void parallelTestIntegerArray3() {

        int k = testingArray3.length;
        Integer[] sortedArray = new Integer[k];

        System.arraycopy(testingArray3, 0, sortedArray, 0, k);

        Arrays.sort(sortedArray);
        ParallelRecursiveMergeSort<Integer> parallelSort = new ParallelRecursiveMergeSort<>(10,false,0);


        parallelSort.sort(testingArray3);

        assertArrayEquals(sortedArray, testingArray3);

    }

    @Test
    public void parallelTestStringArray() {

        int k = unevenNumber.length;
        String[] sortedArray = new String[k];

        System.arraycopy(unevenNumber, 0, sortedArray, 0, k);

        Arrays.sort(sortedArray);
        ParallelRecursiveMergeSort<String> parallelSort = new ParallelRecursiveMergeSort<>(10,false,0);


        parallelSort.sort(unevenNumber);

        assertArrayEquals(sortedArray, unevenNumber);

    }

    @Test
    public void parallelTestComplexTypeArray() {

        int k = stableTest1.length;
        StableTestClass[] sortedArray = new StableTestClass[k];

        System.arraycopy(stableTest1, 0, sortedArray, 0, k);

        Arrays.sort(sortedArray);
        ParallelRecursiveMergeSort<StableTestClass> parallelSort = new ParallelRecursiveMergeSort<>(10,false,0);


        parallelSort.sort(stableTest1);

        assertArrayEquals(sortedArray, stableTest1);

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

    /*
    * This test Is to generate a benchmark for task 12. The aim is to test ParallelRecursiveMergesort without parallelMerging
    * to judge which cutoff parameter would be good. Alternatively it would be interesting to test a different base case algorithm
    * */
    @Ignore
    @Test
    public void benchmarkParallelCutoffThresholds() {
        int pSize = 100000; // Array size (large enough to observe parallel speedup)
        int numberOfThreads = 4; // Number of threads in ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool(numberOfThreads);

        int[] thresholds = {2, 5, 10, 20, 50, 100, 200,500,700,1000,2000,10000,20000,50000,70000,100000}; // Threshold values to test

        // System.out.printf("%-20s %-10s %-10s\n", "Threshold", "Time (ns)", "Array Size");

            int n = 50;
        System.out.println("testing with" + n + " number of repetitions");
            ParallelRecursiveMergeSort.runCutoffBenchmark(pool, pSize, thresholds, n,false,0); // Run with the current threshold


        pool.shutdown(); // Cleanup ForkJoinPool
        /*
        testing with50 number of repetitions, base case Mergesort
        Cutoff          Time (ns)       Variation (ns)
        2               165846758,33    46463812,25
        5               94013225,00     19582803,36
        10              53776889,82     16261920,42
        20              39749628,64     11918459,79
        50              26593301,86     6111958,62
        100             22413039,99     4439007,83
        200             19187509,64     1148352,54
        500             16507541,37     678482,12
        700             16819923,61     954892,82
        1000            14065835,22     266558,77
        2000            11850425,05     203023,43
        10000           9579432,76      124358,28
        20000           8221828,59      84994,99
        50000           8387315,10      49349,21
        70000           8380854,41      19310,42
        100000          15456946,50     59391,38
        */

        /*
        testing with50 number of repetitions, base case InsertionSort
        Cutoff          Time (ns)       Variation (ns)
        2               161875801,25    62124746,44
        5               88643616,45     22107284,98
        10              62396111,60     39087783,22
        20              46256903,06     16208439,46
        50              26302902,29     5897210,86
        100             21922846,39     2279246,71
        200             19605780,59     943292,31
        500             21396762,09     541098,88
        700             21327360,99     555439,25
        1000            30672675,41     628299,47
        2000            53715812,39     690611,02
        10000           220245847,51    3360260,21
        20000           443355541,27    4963487,81
        50000           3410199021,32   15467121,43
        70000           3410269730,79   8326746,63
        100000          13238398751,24  24018638,39
        * */

    }

    @Ignore
    @Test
    public void benchmarkTestParallelCutoffThresholdsWithParallelMerging() {
        int pSize = 100000; // Array size (large enough to observe parallel speedup)
        int numberOfThreads = 4; // Number of threads in ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool(numberOfThreads);

        int[] thresholds = {2, 5, 10, 20, 50, 100, 200,500,700,1000,2000,10000,20000,50000,70000,100000}; // Threshold values to test

        // System.out.printf("%-20s %-10s %-10s\n", "Threshold", "Time (ns)", "Array Size");

        int n = 20;
        System.out.println("testing with" + n + " number of repetitions");
        ParallelRecursiveMergeSort.runCutoffBenchmark(pool, pSize, thresholds, n,true,0); // Run with the current threshold


        pool.shutdown(); // Cleanup ForkJoinPool
        /*
        testing with20 number of repetitions, base case mergeSort
            Cutoff          Time (ns)       Variation (ns)
            2               719840555,18    131440738,15
            5               527610620,83    138992092,98
            10              276743437,50    99287327,25
            20              131649146,36    29916259,01
            50              48172457,71     25151211,14
            100             33997363,27     11003831,79
            200             27879963,13     6005205,44
            500             22846816,25     4240753,42
            700             22907191,07     4166449,68
            1000            19241090,26     2306898,34
            2000            16301323,18     510365,10
            10000           13256193,27     637419,82
            20000           11453881,12     278312,69
            50000           9633597,76      54711,62
            70000           9727750,62      105600,35
            100000          15735625,45     239428,52
        */

        /*
        testing with20 number of repetitions, base case InsertionSort

            Cutoff          Time (ns)       Variation (ns)
            2               666279362,58    92997618,90
            5               428997277,08    38056035,48
            10              230490764,68    65924152,58
            20              123533452,04    31957166,12
            50              44831095,86     14646797,21
            100             35434549,49     12467720,25
            200             29482474,75     6471829,20
            500             29040193,76     5453027,97
            700             28762235,57     4232504,22
            1000            43110173,95     12144206,93
            2000            56503543,52     2061793,97
            10000           217718376,13    4592430,84
            20000           441804799,00    4177843,64
            50000           3355068428,03   167842742,22
        * */

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

    @Ignore
    @Test
    public void testWithPythonArray() {
        // simulate random array from Python
        Integer[] inputArray = new Integer[]{76, 3, 16, 3, 1, 94, 1, 6, 34, 4, 4, 4, 4, 4, 5};

        // threshold = 10, Number of threads = 4
        ParallelRecursiveMergeSort.benchmarkSortingWithSetup(inputArray,1000,0);
    }

    @Ignore
    @Test
    public void testThreadScaling() {
        int[] threadCounts = {1, 2, 4,6, 8, 16};
        int[] arraySizes = {1000, 10000, 100000, 1000000};
        int threshold = 200;

        for (int size : arraySizes) {
            Integer[] inputArray = ParallelRecursiveMergeSort.generateRandomArray(size, 100000);
            ParallelRecursiveMergeSort.benchmarkSortingWithThreads(inputArray, threshold, threadCounts);
        }

        /*
            ParallelRecursiveMergeSort n=1000, threshold=200, threads=1       249537,2 ns    2490,52       1024
            ParallelRecursiveMergeSort n=1000, threshold=200, threads=2       231148,3 ns    1483,70       2048
            ParallelRecursiveMergeSort n=1000, threshold=200, threads=4       237863,4 ns     900,71       2048
            ParallelRecursiveMergeSort n=1000, threshold=200, threads=6       308353,7 ns    1450,70       1024
            ParallelRecursiveMergeSort n=1000, threshold=200, threads=8       376468,0 ns    1448,52       1024
            ParallelRecursiveMergeSort n=1000, threshold=200, threads=16       397730,1 ns    1877,62       1024
            Arrays.parallelSort       n=1000, threshold=200        34893,2 ns      86,80       8192
            ParallelRecursiveMergeSort n=10000, threshold=200, threads=1      2783251,8 ns   36880,39        128
            ParallelRecursiveMergeSort n=10000, threshold=200, threads=2      1787646,7 ns    8983,23        256
            ParallelRecursiveMergeSort n=10000, threshold=200, threads=4      1381086,3 ns    8209,78        256
            ParallelRecursiveMergeSort n=10000, threshold=200, threads=6      1537392,5 ns    8976,83        256
            ParallelRecursiveMergeSort n=10000, threshold=200, threads=8      1631607,7 ns    7059,67        256
            ParallelRecursiveMergeSort n=10000, threshold=200, threads=16      1753305,7 ns   13498,97        256
            Arrays.parallelSort       n=10000, threshold=200       366868,2 ns    3376,61       1024
            ParallelRecursiveMergeSort n=100000, threshold=200, threads=1     35704965,1 ns  516516,07          8
            ParallelRecursiveMergeSort n=100000, threshold=200, threads=2     23201645,6 ns  325130,58         16
            ParallelRecursiveMergeSort n=100000, threshold=200, threads=4     17910894,6 ns  379026,48         16
            ParallelRecursiveMergeSort n=100000, threshold=200, threads=6     24152767,9 ns  343690,05         16
            ParallelRecursiveMergeSort n=100000, threshold=200, threads=8     26731677,3 ns  348820,29         16
            ParallelRecursiveMergeSort n=100000, threshold=200, threads=16     27416627,3 ns  277086,40         16
            Arrays.parallelSort       n=100000, threshold=200      3369529,8 ns   18126,43        128
            ParallelRecursiveMergeSort n=1000000, threshold=200, threads=1    324585595,7 ns 19433007,27          2
            ParallelRecursiveMergeSort n=1000000, threshold=200, threads=2    246152958,2 ns 12364950,05          2
            ParallelRecursiveMergeSort n=1000000, threshold=200, threads=4    260921135,6 ns 10321983,93          2
            ParallelRecursiveMergeSort n=1000000, threshold=200, threads=6    438600449,8 ns 14144341,56          2
            ParallelRecursiveMergeSort n=1000000, threshold=200, threads=8    467512279,4 ns 8692599,79          2
            ParallelRecursiveMergeSort n=1000000, threshold=200, threads=16    471781366,7 ns 11365456,89          2
            Arrays.parallelSort       n=1000000, threshold=200     40368698,4 ns  869929,86          8
            */

        /*
            with parallelMerge and 1600 cutoff
        *   ParallelRecursiveMergeSort n=1000, threshold=1600, threads=1        89394,9 ns     589,54       4096
            ParallelRecursiveMergeSort n=1000, threshold=1600, threads=2        90677,6 ns    1218,05       4096
            ParallelRecursiveMergeSort n=1000, threshold=1600, threads=4        92791,5 ns    2036,13       4096
            ParallelRecursiveMergeSort n=1000, threshold=1600, threads=6        90736,0 ns     522,21       4096
            ParallelRecursiveMergeSort n=1000, threshold=1600, threads=8        91632,2 ns    1546,95       4096
            ParallelRecursiveMergeSort n=1000, threshold=1600, threads=16        90505,6 ns     435,27       4096
            Arrays.parallelSort       n=1000, threshold=1600        36331,8 ns      81,97       8192
            ParallelRecursiveMergeSort n=10000, threshold=1600, threads=1      1156392,2 ns    3288,01        256
            ParallelRecursiveMergeSort n=10000, threshold=1600, threads=2       962614,9 ns    3704,55        512
            ParallelRecursiveMergeSort n=10000, threshold=1600, threads=4       943042,4 ns    3884,60        512
            ParallelRecursiveMergeSort n=10000, threshold=1600, threads=6      1164158,8 ns    7772,34        256
            ParallelRecursiveMergeSort n=10000, threshold=1600, threads=8      1177780,5 ns   12947,54        256
            ParallelRecursiveMergeSort n=10000, threshold=1600, threads=16      1273178,2 ns    5608,81        256
            Arrays.parallelSort       n=10000, threshold=1600       362530,1 ns    5278,05       1024
            ParallelRecursiveMergeSort n=100000, threshold=1600, threads=1     14951665,5 ns   82202,92         32
            ParallelRecursiveMergeSort n=100000, threshold=1600, threads=2     13304417,9 ns  285490,42         32
            ParallelRecursiveMergeSort n=100000, threshold=1600, threads=4     13310496,3 ns   99329,30         32
            ParallelRecursiveMergeSort n=100000, threshold=1600, threads=6     22250028,9 ns  174808,50         16
            ParallelRecursiveMergeSort n=100000, threshold=1600, threads=8     23039429,4 ns  223206,78         16
            ParallelRecursiveMergeSort n=100000, threshold=1600, threads=16     24165871,0 ns  173525,43         16
            Arrays.parallelSort       n=100000, threshold=1600      3288158,3 ns   12109,38        128
            ParallelRecursiveMergeSort n=1000000, threshold=1600, threads=1    198618241,6 ns 10055736,34          2
            ParallelRecursiveMergeSort n=1000000, threshold=1600, threads=2    188841758,2 ns 6212110,58          2
            ParallelRecursiveMergeSort n=1000000, threshold=1600, threads=4    224629302,3 ns 9369716,84          2
            ParallelRecursiveMergeSort n=1000000, threshold=1600, threads=6    410343687,5 ns 8692198,23          2
            ParallelRecursiveMergeSort n=1000000, threshold=1600, threads=8    412778318,8 ns 5861142,08          2
            ParallelRecursiveMergeSort n=1000000, threshold=1600, threads=16    422560262,3 ns 4133522,02          2
            Arrays.parallelSort       n=1000000, threshold=1600     47781463,0 ns 12261064,85          8
            */

        /*  with parallelMerge and 3200 cutoff
        ParallelRecursiveMergeSort n=1000, threshold=3200, threads=1        90571,6 ns     689,29       4096
            ParallelRecursiveMergeSort n=1000, threshold=3200, threads=2        91251,5 ns     864,32       4096
            ParallelRecursiveMergeSort n=1000, threshold=3200, threads=4        91076,3 ns    1021,12       4096
            ParallelRecursiveMergeSort n=1000, threshold=3200, threads=6        91728,2 ns     654,49       4096
            ParallelRecursiveMergeSort n=1000, threshold=3200, threads=8        91313,7 ns     479,25       4096
            ParallelRecursiveMergeSort n=1000, threshold=3200, threads=16        91814,1 ns     512,64       4096
            Arrays.parallelSort       n=1000, threshold=3200        34921,4 ns     233,84       8192
            ParallelRecursiveMergeSort n=10000, threshold=3200, threads=1      1161225,9 ns    3165,02        256
            ParallelRecursiveMergeSort n=10000, threshold=3200, threads=2       871757,1 ns    3403,06        512
            ParallelRecursiveMergeSort n=10000, threshold=3200, threads=4       835845,5 ns    2195,15        512
            ParallelRecursiveMergeSort n=10000, threshold=3200, threads=6       918748,8 ns    3181,06        512
            ParallelRecursiveMergeSort n=10000, threshold=3200, threads=8       902576,5 ns    4236,16        512
            ParallelRecursiveMergeSort n=10000, threshold=3200, threads=16       936687,0 ns    4381,20        512
            Arrays.parallelSort       n=10000, threshold=3200       360915,6 ns    2451,86       1024
            ParallelRecursiveMergeSort n=100000, threshold=3200, threads=1     15240676,1 ns   80863,63         32
            ParallelRecursiveMergeSort n=100000, threshold=3200, threads=2     12993714,7 ns  166282,57         32
            ParallelRecursiveMergeSort n=100000, threshold=3200, threads=4     11751181,8 ns  137166,99         32
            ParallelRecursiveMergeSort n=100000, threshold=3200, threads=6     18992724,8 ns  140851,61         16
            ParallelRecursiveMergeSort n=100000, threshold=3200, threads=8     19525898,2 ns  278917,68         16
            ParallelRecursiveMergeSort n=100000, threshold=3200, threads=16     20693337,3 ns  189501,27         16
            Arrays.parallelSort       n=100000, threshold=3200      3345348,0 ns   15875,19        128
            ParallelRecursiveMergeSort n=1000000, threshold=3200, threads=1    201720641,6 ns 5549501,01          2
            ParallelRecursiveMergeSort n=1000000, threshold=3200, threads=2    185082268,9 ns 6068592,63          2
            ParallelRecursiveMergeSort n=1000000, threshold=3200, threads=4    199877346,0 ns 6745354,38          2
            ParallelRecursiveMergeSort n=1000000, threshold=3200, threads=6    367827050,0 ns 5946258,98          2
            ParallelRecursiveMergeSort n=1000000, threshold=3200, threads=8    369173327,2 ns 4539732,85          2
            ParallelRecursiveMergeSort n=1000000, threshold=3200, threads=16    379086189,7 ns 3119176,50          2
            Arrays.parallelSort       n=1000000, threshold=3200     42177095,9 ns  493832,46          8
            */

            /*
                with parallelMerge and 20.000 cutoff
            * ParallelRecursiveMergeSort n=1000, threshold=20000, threads=1        94890,9 ns     441,33       4096
                ParallelRecursiveMergeSort n=1000, threshold=20000, threads=2        95557,1 ns    1384,59       4096
                ParallelRecursiveMergeSort n=1000, threshold=20000, threads=4        96232,2 ns    1747,56       4096
                ParallelRecursiveMergeSort n=1000, threshold=20000, threads=6        95078,4 ns     574,08       4096
                ParallelRecursiveMergeSort n=1000, threshold=20000, threads=8        95137,3 ns     404,40       4096
                ParallelRecursiveMergeSort n=1000, threshold=20000, threads=16        94869,1 ns     469,53       4096
                Arrays.parallelSort       n=1000, threshold=20000        34790,3 ns     138,45       8192
                ParallelRecursiveMergeSort n=10000, threshold=20000, threads=1      1263783,7 ns    6181,98        256
                ParallelRecursiveMergeSort n=10000, threshold=20000, threads=2      1283641,9 ns   20475,58        256
                ParallelRecursiveMergeSort n=10000, threshold=20000, threads=4      1264629,1 ns    5446,20        256
                ParallelRecursiveMergeSort n=10000, threshold=20000, threads=6      1263238,1 ns    3295,40        256
                ParallelRecursiveMergeSort n=10000, threshold=20000, threads=8      1269563,2 ns    7091,10        256
                ParallelRecursiveMergeSort n=10000, threshold=20000, threads=16      1263488,6 ns    4015,37        256
                Arrays.parallelSort       n=10000, threshold=20000       358639,2 ns    6493,51       1024
                ParallelRecursiveMergeSort n=100000, threshold=20000, threads=1     16317841,2 ns   75155,41         16
                ParallelRecursiveMergeSort n=100000, threshold=20000, threads=2     12359532,7 ns  117473,06         32
                ParallelRecursiveMergeSort n=100000, threshold=20000, threads=4      9907046,4 ns   85661,45         32
                ParallelRecursiveMergeSort n=100000, threshold=20000, threads=6     14617583,1 ns  160183,01         32
                ParallelRecursiveMergeSort n=100000, threshold=20000, threads=8     13729003,4 ns  135327,35         32
                ParallelRecursiveMergeSort n=100000, threshold=20000, threads=16     14114687,5 ns   97261,03         32
                Arrays.parallelSort       n=100000, threshold=20000      3348794,1 ns   13265,42        128
                ParallelRecursiveMergeSort n=1000000, threshold=20000, threads=1    217540947,9 ns 15617820,63          2
                ParallelRecursiveMergeSort n=1000000, threshold=20000, threads=2    169080135,4 ns 2713534,76          2
                ParallelRecursiveMergeSort n=1000000, threshold=20000, threads=4    154544323,1 ns 7199597,43          2
                ParallelRecursiveMergeSort n=1000000, threshold=20000, threads=6    242219364,7 ns 7384246,08          2
                ParallelRecursiveMergeSort n=1000000, threshold=20000, threads=8    247574462,6 ns 4864703,54          2
                ParallelRecursiveMergeSort n=1000000, threshold=20000, threads=16    255477733,4 ns 5632508,27          2
                Arrays.parallelSort       n=1000000, threshold=20000     70264340,1 ns 3003806,42          8
*/

        /*
        * 1600 cutoff:
            ParallelRecursiveMergeSort n=10000, threshold=1600, threads=1      1156392,2 ns    3288,01        256
            ParallelRecursiveMergeSort n=10000, threshold=1600, threads=2       962614,9 ns    3704,55        512
            ParallelRecursiveMergeSort n=10000, threshold=1600, threads=4       943042,4 ns    3884,60        512
            ParallelRecursiveMergeSort n=10000, threshold=1600, threads=6      1164158,8 ns    7772,34        256
            ParallelRecursiveMergeSort n=10000, threshold=1600, threads=8      1177780,5 ns   12947,54        256
            ParallelRecursiveMergeSort n=10000, threshold=1600, threads=16      1273178,2 ns    5608,81        256
            Arrays.parallelSort       n=10000, threshold=1600       362530,1 ns    5278,05       1024
        * 3200 cutoff:

            ParallelRecursiveMergeSort n=10000, threshold=3200, threads=1      1161225,9 ns    3165,02        256
            ParallelRecursiveMergeSort n=10000, threshold=3200, threads=2       871757,1 ns    3403,06        512
            ParallelRecursiveMergeSort n=10000, threshold=3200, threads=4       835845,5 ns    2195,15        512
            ParallelRecursiveMergeSort n=10000, threshold=3200, threads=6       918748,8 ns    3181,06        512
            ParallelRecursiveMergeSort n=10000, threshold=3200, threads=8       902576,5 ns    4236,16        512
            ParallelRecursiveMergeSort n=10000, threshold=3200, threads=16       936687,0 ns    4381,20        512
            Arrays.parallelSort       n=10000, threshold=3200       360915,6 ns    2451,86       1024
        * 20.000 cutoff:
    *       ParallelRecursiveMergeSort n=100000, threshold=20000, threads=1     16317841,2 ns   75155,41         16
            ParallelRecursiveMergeSort n=100000, threshold=20000, threads=2     12359532,7 ns  117473,06         32
            ParallelRecursiveMergeSort n=100000, threshold=20000, threads=4      9907046,4 ns   85661,45         32
            ParallelRecursiveMergeSort n=100000, threshold=20000, threads=6     14617583,1 ns  160183,01         32
            ParallelRecursiveMergeSort n=100000, threshold=20000, threads=8     13729003,4 ns  135327,35         32
            ParallelRecursiveMergeSort n=100000, threshold=20000, threads=16     14114687,5 ns   97261,03         32
            Arrays.parallelSort       n=100000, threshold=20000      3348794,1 ns   13265,42        128
            ParallelRecursiveMergeSort n=1000000, threshold=20000, threads=1    217540947,9 ns 15617820,63          2
            ParallelRecursiveMergeSort n=1000000, threshold=20000, threads=2    169080135,4 ns 2713534,76          2
            ParallelRecursiveMergeSort n=1000000, threshold=20000, threads=4    154544323,1 ns 7199597,43          2
            ParallelRecursiveMergeSort n=1000000, threshold=20000, threads=6    242219364,7 ns 7384246,08          2
            ParallelRecursiveMergeSort n=1000000, threshold=20000, threads=8    247574462,6 ns 4864703,54          2
            ParallelRecursiveMergeSort n=1000000, threshold=20000, threads=16    255477733,4 ns 5632508,27          2
            Arrays.parallelSort       n=1000000, threshold=20000     70264340,1 ns 3003806,42          8
        * */


    }

    @Ignore
    @Test
    public void testThreadScalingLargeArray() {
        int[] threadCounts = {1, 2, 4,6, 8, 16};
        int[] arraySizes = {10000000};
        int threshold = 100000;

        for (int size : arraySizes) {
            Integer[] inputArray = ParallelRecursiveMergeSort.generateRandomArray(size, 100000);
            ParallelRecursiveMergeSort.benchmarkSortingWithThreads(inputArray, threshold, threadCounts);
        }

        /*
        ParallelRecursiveMergeSort n=10000000, threshold=100000, threads=1   3463314860,4 ns 301665193,15          2
        ParallelRecursiveMergeSort n=10000000, threshold=100000, threads=2   2488103870,9 ns 31758648,71          2
        ParallelRecursiveMergeSort n=10000000, threshold=100000, threads=4   2689048566,8 ns 85584573,73          2
        ParallelRecursiveMergeSort n=10000000, threshold=100000, threads=6   3084805570,9 ns 33359592,78          2
        ParallelRecursiveMergeSort n=10000000, threshold=100000, threads=8   3081415025,1 ns 33772397,42          2
        ParallelRecursiveMergeSort n=10000000, threshold=100000, threads=16   3070396741,8 ns 50026806,85          2
        Arrays.parallelSort       n=10000000, threshold=100000    673172477,2 ns 118299503,69          2

            */
    }



}
