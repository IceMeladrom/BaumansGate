package Exceptions;

import Entities.Units.Units.Unit;

public class NotYourTown extends Exception {
    public NotYourTown(Unit unit) {
        super("Your unit " + unit.getName() + " at the ceil (row: " + unit.getRow() + "; col: " + unit.getCol() + " tried to move at enemy's Town.");
    }
}
