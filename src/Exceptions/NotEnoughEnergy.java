package Exceptions;

public class NotEnoughEnergy extends Exception {
    public NotEnoughEnergy(int currentRow, int currentCol, int endRow, int endCol, float totalSpendEnergy) {
        super("Your unit at the ceil (row: " + (currentRow + 1) + "; col: " + (currentCol + 1) + ") does not have enough energy " +
                "to move into the ceil (row: " + (endRow + 1) + "; col: " + (endCol + 1) + "). You need " + totalSpendEnergy + " energy");
    }
}
