package Exceptions;

public class NotEnoughEnergy extends Exception {
    public NotEnoughEnergy(int currentRow, int currentCol, int endRow, int endCol, Double totalSpendEnergy) {
        super("Your unit at the cell (row: " + (currentRow + 1) + "; col: " + (currentCol + 1) + ") does not have enough energy " +
                "to move into the cell (row: " + (endRow + 1) + "; col: " + (endCol + 1) + "). You need " + totalSpendEnergy + " energy");
    }
}
