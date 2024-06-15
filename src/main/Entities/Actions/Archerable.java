package main.Entities.Actions;

import main.Entities.Units.Units.IUnit;
import main.Exceptions.UnitHasAlreadyAttacked;

public interface Archerable {
    public void attack(IUnit unit) throws UnitHasAlreadyAttacked;
}
