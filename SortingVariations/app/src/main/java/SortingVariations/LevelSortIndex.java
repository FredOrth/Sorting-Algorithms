package SortingVariations;

import java.util.Stack;

public class LevelSortIndex<T extends Comparable<T>> implements Sorter<T> {
    private int counter = 0;
    private int cutoff;
    private boolean adaptive;

    public LevelSortIndex(int cutoff, boolean adaptive) {
        this.cutoff = cutoff;
        this.adaptive = adaptive;
    }

    @Override
    public Integer sort(T[] a) {
        T[] aux = a.clone();
        counter = 0;

        Stack<Integer[]> stack = new Stack<>();
        Stack<integer[]> stackLvl = new Stack<>(); // This is where I think implementing a run class, that can have an attribute level.
        int i = 0;

        while (i < a.length) {
            Integer[] run = new Integer[2];
            run[0] = i;

            // Determine run length
            if (adaptive) {
                int sequence = findSequence(i, a);
                if (sequence >= cutoff) {
                    run[1] = i + sequence - 1;
                    i += sequence - 1;
                } else {
                    if (i + cutoff >= a.length) {
                        run[1] = a.length - 1;
                        insertionSort(a, i, run[1]);
                        i = a.length;
                    } else {
                        run[1] = i + cutoff - 1;
                        insertionSort(a, i, i + cutoff - 1);
                        i += cutoff - 1;
                    }
                }
            } else {
                if (i + cutoff >= a.length) {
                    run[1] = a.length - 1;
                    insertionSort(a, i, run[1]);
                    i = a.length;
                } else {
                    run[1] = i + cutoff - 1;
                    insertionSort(a, i, i + cutoff - 1);
                    i += cutoff - 1;
                }
            }
            i++;

            // Merge runs from the stack
            while (!stack.isEmpty()) {
                Integer[] topRun = stack.peek();
                if (topRun[1] - topRun[0] + 1 < run[1] - run[0] + 1) { //same as below.
                    merge(a, aux, topRun[0], run[0] - 1, run[1]);
                    stack.pop();
                    run[0] = topRun[0];
                } else {
                    break;
                }
            }

            // Continue merging based on level size
            while (!stack.isEmpty()) {
                Integer[] topRun = stack.peek();
                Integer[] topRunLvl = stack.peek();
                if (topRun[1] - topRun[0] + 1 < (run[1] - run[0] + 1) * 2 && topRunLvl[0] <= topRunLvl[1]) { // this is the condition where we wanna incorporate the levelboundary calculation
                    merge(a, aux, topRun[0], run[0] - 1, run[1]);
                    run[0] = topRun[0];
                    stack.pop();
                } else {
                    break;
                }
            }

            assert stack.isEmpty() || stack.peek()[1] - stack.peek()[0] + 1 >= (run[1] - run[0] + 1) * 2;
            stack.add(run);
        }

        // Final merging phase
        while (stack.size() > 1) {
            Integer[] run = stack.pop();
            merge(a, aux, stack.peek()[0], run[0] - 1, run[1]);
            stack.peek()[1] = run[1];
        }
        return counter;
    }

    private int findSequence(int i, T[] a) {
        int j = i;
        if (j == a.length - 1) {
            return 1;
        }
        if (a[j].compareTo(a[j + 1]) <= 0) {
            j++;
            counter++;
            while (j < a.length - 1) {
                counter++;
                if (a[j].compareTo(a[j + 1]) <= 0) {
                    j++;
                } else {
                    j++;
                    break;
                }
            }
            if (j == a.length - 1 && a[j - 1].compareTo(a[j]) <= 0) {
                j++;
            }
        } else {
            j++;
            counter++;
            while (j < a.length - 1) {
                counter++;
                if (a[j].compareTo(a[j + 1]) > 0) {
                    j++;
                } else {
                    j++;
                    break;
                }
            }
            if (j == a.length - 1 && a[j - 1].compareTo(a[j]) > 0) {
                j++;
            }
            if (j >= cutoff) {
                for (int k = 0; k < (j - i) / 2; k++) {
                    T smallElm = a[i + k];
                    a[i + k] = a[j - k - 1];
                    a[j - k - 1] = smallElm;
                }
            }
        }

        return j - i;

    }


    private void computeLevel(int ia, int ib, int ic) {
        /* 
         * Takes 3 int arguments ia, ib and ic.
         * returns an int level
        */
        long ml = (ia + (ib-1))/2
        long mr = (ib + ic-1)/2

        long xor = ml ^ mr // find the differing parts

        int level = 64 - Long.numberOfLeadingZeros(xor)

        return level
    }

    private void merge(T[] a, T[] aux, int ia, int ib, int ic) { // if a[1, 2 ,3], then aux = [1, 2, 3]
        for (int k = ia; k <= ic; k++) {
            aux[k] = a[k]; // do the same for a and aux
        }

        int i = ia;
        //int j = ib + 1; // need calc level here?
        int j = computeLevel(ia, ib, ic)

        for (int k = ia; k <= ic; k++) {
            if (i > ib) {
                a[k] = aux[j++];
            } else if (j > ic) { // so l instead of j here?
                a[k] = aux[i++];
            } else if ((aux[j].compareTo(aux[i])) < 0) {
                a[k] = aux[j++];
                counter++;
            } else {
                a[k] = aux[i++];
                counter++;
            }
        }
    }

    private void insertionSort(T[] a, int ia, int ic) {
        for (int i = ia + 1; i <= ic; i++) {
            T key = a[i];
            int j = i - 1;

            while (j >= ia && a[j].compareTo(key) > 0) {
                counter++; // Count comparison
                a[j + 1] = a[j];
                j--;
            }

            if (j >= ia) {
                counter++; // Count final comparison
            }

            a[j + 1] = key;
        }
    }
}
