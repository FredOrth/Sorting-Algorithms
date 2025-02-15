package SortingVariations.Util;

class Run {
    public int start
    public int end
    public int level

    public Run(int start, int end, int level){
        this.start = start
        this.end = end
        this.level = level
    }

    public int size(){ // perhaps not a useful method, given that it returns different run lengths compared to the findSequence
        return end-start + 1
    }
}