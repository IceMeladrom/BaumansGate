package Entities.Units.Units.Fire;

import Entities.Units.Units.IMage;
import Entities.Units.Units.IUnit;
import Entities.Units.Units.UnitClass;
import Exceptions.AlliedUnitAtTheCeil;
import Exceptions.NotEnoughEnergy;
import Exceptions.NotYourTown;
import Exceptions.UnitHasAlreadyAttacked;
import Players.Player;

public class FireMage extends UnitClass implements IMage {
    public FireMage(String name, int hp, int damage, int attackRange, int defence, float energy, int cost, String symbol, int row, int col, Player player) {
        super(name, hp, damage, attackRange, defence, energy, cost, symbol, row, col, player);
    }

    @Override
    public void walk(int row, int col) throws NotEnoughEnergy, AlliedUnitAtTheCeil, UnitHasAlreadyAttacked, NotYourTown {

    }

    @Override
    public void attack(IUnit enemy) throws UnitHasAlreadyAttacked {

    }
}
