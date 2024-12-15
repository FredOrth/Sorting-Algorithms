package SortingVariations;

import java.util.Stack;

public class BinomialSortIndex<T extends Comparable<T>> extends Sorter1<T> implements Sorter<T> {
    private int counter = 0;
    private int cutoff;
    private boolean adaptive;

    public BinomialSortIndex(int cutoff, boolean adaptive){
        this.cutoff = cutoff;
        this.adaptive = adaptive;
    }

    @Override
    public void sort(T[] a) {
        T[] aux = a.clone();
        counter = 0;

        Stack<Integer[]> stack = new Stack<>();
        int i = 0;
        while(i<a.length){
            Integer[] arr = new Integer[2];
            arr[0] = i; 

            if(adaptive){
                int sequence = findSequence(i, a);
                if(sequence >= cutoff){
                arr[1] = i + sequence-1;
                i += sequence-1;
                }else{
                    if(i + cutoff >= a.length){
                        arr[1] = a.length-1;
                        insertionSort(a, i, arr[1]);
                        i = a.length;
                    }else{
                    arr[1] = i + cutoff-1;
                    insertionSort(a, i, i+cutoff-1);
                    i+=cutoff-1;
                }
                }
            }else{
                if(i + cutoff >= a.length){
                        arr[1] = a.length-1;
                        insertionSort(a, i, arr[1]);
                        i = a.length;
                    }else{
                    arr[1] = i + cutoff-1;
                    insertionSort(a, i, i+cutoff-1);
                    i+=cutoff-1;
                }
                }

            i++;
            
            while(!stack.isEmpty()){
                Integer[] topStack = stack.peek();
                if(topStack[1]-topStack[0] + 1 < arr[1] - arr[0] + 1){
                    merge(a, aux, topStack[0], arr[0]-1, arr[1]);
                    stack.pop();
                    arr[0] = topStack[0]; 
                }else{
                    break;
                }
            }

            while(!stack.isEmpty()){
                Integer[] topStack = stack.peek();
                if(topStack[1]-topStack[0] +1 < (arr[1] - arr[0] + 1) * 2){
                    merge(a, aux, topStack[0], arr[0]-1, arr[1]);
                    arr[0] = topStack[0];
                    stack.pop();
            }else{
                break;
            }
        }

        assert stack.isEmpty() ||stack.peek()[1]-stack.peek()[0]+1 >= (arr[1]-arr[0]+1)*2;
        stack.add(arr);

    }

    while(stack.size()>1){
        Integer[] arr = stack.pop();
        merge(a,aux,stack.peek()[0], arr[0]-1, arr[1]);
        stack.peek()[1] = arr[1];
    }

}

    private int findSequence(int i, T[]a){
        int j = i;
        if(j==a.length-1){
            return 1;
        }
        if(a[j].compareTo(a[j+1])<=0){
            j++;
            counter++;
            while(j<a.length-1){
                counter++;
                if(a[j].compareTo(a[j+1])<0){
                    j++;
                }else{
                    j++;
                    break;
                }
            }
        }else{
            j++;
            counter++;
            while(j<a.length-1){
                counter++;
                if(a[j].compareTo(a[j+1])> 0){
                    j++;
                }else{
                    break;
                }
            }
            j++;
            for(int k= 0; k<(j-i)/2; k++){
                T smallElm = a[i+k];
                a[i+k] = a[j-k-1];
                a[j-k-1] = smallElm; 
            }
        }
        return j-i;
        
    }
    
    
}

