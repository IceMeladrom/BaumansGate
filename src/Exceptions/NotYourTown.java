package Exceptions;

import Entities.Units.Units.IUnit;

public class NotYourTown extends Exception {
    public NotYourTown(IUnit unit) {
        super("Your unit " + unit.getName() + " at the ceil (row: " + unit.getRow() + "; col: " + unit.getCol() + " tried to move at enemy's Town.");
    }
}
