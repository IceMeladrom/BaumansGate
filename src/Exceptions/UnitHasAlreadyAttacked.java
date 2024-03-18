package Exceptions;

import Entities.Units.Units.Unit;

public class UnitHasAlreadyAttacked extends Exception {
    public UnitHasAlreadyAttacked(Unit unit) {
        super("Your unit " + unit.getName() + " at ceil (row: " + (unit.getRow() + 1) + "; col: " + (unit.getCol() + 1) + ") has already attacked another unit.");
    }
}
