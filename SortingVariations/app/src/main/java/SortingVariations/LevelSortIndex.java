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

            //Is this check supposed to be there? Im not sure where
            //in the description it says that runs neccesarily should increas in size
            // Merge runs from the stack
            // while (!stack.isEmpty()) {
            //     Integer[] topRun = stack.peek();
            //     int check = topRun[1] - topRun[0] + 1;
            //     int check2 =  run[1] - run[0] + 1;
            //     if (topRun[1] - topRun[0] + 1 < run[1] - run[0] + 1) {
            //         merge(a, aux, topRun[0], run[0] - 1, run[1]);
            //         stack.pop();
            //         run[0] = topRun[0];
            //     } else {
            //         break;
            //     }
            // }

            // Continue merging based on level size
            //Maybe i misunderstand the code but do we really find the difference in most significant bit here?
            //Example arr1[9] and arr[5] =  9/2 = 4 = (3 bits) and 5/2 = 2 (2 bits), meaning no merge should happen 
            //In the code we say 0-8+1 = 9 and (8-12+1)*2 = 10 so a merge does happen. 
            //Maybe I dont understand the code, but it seems the calculation is not correct for MST here 
            while (!stack.isEmpty()) {
                Integer[] topRun = stack.peek();
                if (topRun[1] - topRun[0] + 1 < (run[1] - run[0] + 1) * 2) {
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

    private void merge(T[] a, T[] aux, int low, int mid, int high) {
        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }

        int i = low;
        int j = mid + 1;

        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > high) {
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

    private void insertionSort(T[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            T key = a[i];
            int j = i - 1;

            while (j >= low && a[j].compareTo(key) > 0) {
                counter++; // Count comparison
                a[j + 1] = a[j];
                j--;
            }

            if (j >= low) {
                counter++; // Count final comparison
            }

            a[j + 1] = key;
        }
    }
}
