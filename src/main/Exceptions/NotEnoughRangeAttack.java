package main.Exceptions;

public class NotEnoughRangeAttack extends Exception{
    public NotEnoughRangeAttack(int currentRow, int currentCol) {
        super("Your unit at the cell (row: " + (currentRow + 1) + "; col: " + (currentCol + 1) + ") does not have enough attack range");
    }
}
