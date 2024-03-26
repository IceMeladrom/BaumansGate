package Entities.Actions;

import Entities.Units.Units.IUnit;
import Exceptions.UnitHasAlreadyAttacked;

public interface Archerable {
    public void attack(IUnit unit) throws UnitHasAlreadyAttacked;
}
