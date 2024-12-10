package SortingVariations;

public class BinomialSort<T extends Comparable<T>> implements Sorter<T> {
    private int counter = 0;

    @Override
    public void sort(T[] a) {
        
    }

    public int findSequence(int i, T[]a){
        int j = i;
        if(a[j].compareTo(a[j+1])<0){
            counter++;
            i++;
            while(j<a.length-1){
                if(a[j].compareTo(a[j+1])<0){
                counter++;
                j++;
            }else{
                break;
            }
            }
        }else{
            counter++;
            while(j<a.length-1){
            if(a[j].compareTo(a[j+1])>=0){
                counter++;
                j++;
            }else{
                break;
            }
            }
            for(int k = i; k<j/2+1; k++){
                T bigElement = a[j-(k-i)];
                System.out.println(bigElement);
                System.out.println(a[k]);
                a[j-(k-i)] = a[k];
                a[k]= bigElement;
            }
        }
        return j;
    }
    
}
