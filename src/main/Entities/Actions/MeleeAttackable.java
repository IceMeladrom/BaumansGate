package main.Entities.Actions;

import main.Entities.Units.Units.IUnit;
import main.Exceptions.UnitHasAlreadyAttacked;

public interface MeleeAttackable {
    public void attack(IUnit enemy) throws UnitHasAlreadyAttacked;
}
