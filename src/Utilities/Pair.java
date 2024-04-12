package Utilities;

public class Pair {
    private final int row, col;
    private Double cost;
    private Pair previous;

    public Pair(int row, int col, Double cost) {
        this.row = row;
        this.col = col;
        this.cost = cost;
        previous = null;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Double getCost() {
        return cost;
    }

    public Pair getPrevious() {
        return previous;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setPrevious(Pair previous) {
        this.previous = previous;
    }
}