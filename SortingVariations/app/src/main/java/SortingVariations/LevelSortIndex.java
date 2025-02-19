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
        Stack<int[]> stackLvl = new Stack<>(); // This is where I think implementing a run class, that can have an attribute level.
        int i = 0;

        Integer[] run = findSequence(i, a);
        while (i < a.length) {
            Integer[] nextRun = new Integer[2];
            nextRun[0] = i;
            run[0] = i;

            int k = validateSequence(nextRun, i, cutoff, adaptive);
            int i += k;

            int lvl = computeLevel(int run[0], int run[1], nextRun[1]);

            // Continue merging based on level size [run 1 on stack, run, nextRun]
            while (!stack.isEmpty()) {
                int topRunLvl = stackLvl.peek();
                if (topRunLvl <= lvl) { // i.e run1 (4) <= run(6)
                    topRun = stack.pop(); //run 1
                    topLvl = stackLvl.pop(); // 4
                    merge(a, aux, run[0], run[0]-1, nextRun[1]); //run 1 and run
                    run[0] = topRun[0]; // start of run = run1
                    //topLvl = lvl // toplvl is now lvl otherwise recompute lvl of run1 + run to nextRun
                    // just add lvl to lvlstack and throw toplvl out?
                } else {
                    break;
                }
            }
            //assert stack.isEmpty() || stack.peek()[1] - stack.peek()[0] + 1 >= (run[1] - run[0] + 1) * 2;
            stack.add(run);
            //smarter way than this?
            stackLvl.add(lvl);
            run = nextRun;
        }
        // Final merging phase
        while (stack.size() > 1) {
            Integer[] newRun = stack.pop();
            merge(a, aux, stack.peek()[0], newRun[0]-1, newRun[1]);
            stack.peek()[1] = newRun[1];
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

    private int validateSequence(T[] run, int i, int cutoff, boolean adaptive) {
        // Determine run length
            if (adaptive) {
                int sequence = findSequence(i, run);
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
            return i;
    }

    private int computeLevel(int ia, int ib, int ic) {
        /* 
         * Takes 3 int arguments ia, ib and ic.
         * returns an int level
        */
        long ml = (ia + (ib-1))/2; // -1 or no?
        long mr = (ib + ic-1)/2;

        long xor = ml ^ mr; // find the differing parts

        int level = 64 - Long.numberOfLeadingZeros(xor);

        return level;
    }

    //revert back to the low, mid, high policy from Binomial?
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
