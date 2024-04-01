package Exceptions;

import Entities.Units.Units.IUnit;

public class UnitHasAlreadyAttacked extends Exception {
    public UnitHasAlreadyAttacked(IUnit unit) {
        super("Your unit " + unit.getName() + " at the cell (row: " + (unit.getRow() + 1) + "; col: " + (unit.getCol() + 1) + ") has already attacked another unit.");
    }
}
