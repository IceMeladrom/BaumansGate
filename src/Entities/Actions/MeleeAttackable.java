package Entities.Actions;

import Entities.Units.Units.Unit;
import Exceptions.UnitHasAlreadyAttacked;

public interface MeleeAttackable {
    public void attack(Unit enemy) throws UnitHasAlreadyAttacked;
}
