package Entities.Actions;

import Entities.Units.Units.IUnit;
import Exceptions.UnitHasAlreadyAttacked;

public interface MeleeAttackable {
    public void attack(IUnit enemy) throws UnitHasAlreadyAttacked;
}
