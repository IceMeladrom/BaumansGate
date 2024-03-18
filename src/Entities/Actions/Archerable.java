package Entities.Actions;

import Entities.Units.Units.Unit;
import Exceptions.UnitHasAlreadyAttacked;

public interface Archerable {
    public void attack(Unit unit) throws UnitHasAlreadyAttacked;
}
