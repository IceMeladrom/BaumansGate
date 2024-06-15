package main.Exceptions;

import main.Entities.Units.Units.IUnit;

public class UnitHasNotPreparedAnAttack extends Exception {
    public UnitHasNotPreparedAnAttack(IUnit unit) {
        super("Your unit " + unit.getName() + " at the cell (row: " + (unit.getRow() + 1) + "; col: " + (unit.getCol() + 1) + ") has not prepared an attack already.");
    }
}
