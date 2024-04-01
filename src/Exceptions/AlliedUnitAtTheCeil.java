package Exceptions;

public class AlliedUnitAtTheCeil extends Exception {
    public AlliedUnitAtTheCeil(int currentRow, int currentCol, int endRow, int endCol) {
        super("You tried to move your unit from cell (row: " + (currentRow + 1) + "; col: " + (currentCol + 1) + ") " +
                "to cell (row: " + (endRow + 1) + "; col: " + (endCol + 1) + ") where your allied unit is.");
    }
}
