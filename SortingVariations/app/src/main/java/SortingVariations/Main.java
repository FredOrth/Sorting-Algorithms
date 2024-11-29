package SortingVariations;

public class Main {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new Main().getGreeting());
        // assert false : "Assertions are enabled!";
        // System.out.println("Assertions are disabled!");

        Integer[] testingArray = new Integer[]{5,3,2,6,4,9,10,2,4};

        MergeSort.sort(testingArray);
        for (Integer integer : testingArray) {
            System.out.println(integer);
        }
    }
}
