package main.Exceptions;

import main.Entities.Units.Units.IUnit;

public class NotYourTown extends Exception {
    public NotYourTown(IUnit unit) {
        super("Your unit " + unit.getName() + "at the cell (row: " + (unit.getRow() + 1) + "; col: " + (unit.getCol() + 1) + ") tried to move at enemy's Town.");
    }
}
