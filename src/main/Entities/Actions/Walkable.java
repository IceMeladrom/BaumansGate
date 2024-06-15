package main.Entities.Actions;

import main.Exceptions.AlliedUnitAtTheCeil;
import main.Exceptions.NotEnoughEnergy;
import main.Exceptions.NotYourTown;
import main.Exceptions.UnitHasAlreadyAttacked;
import main.Grid.Grid;

public interface Walkable {
    public void walk(int row, int col) throws NotEnoughEnergy, AlliedUnitAtTheCeil, UnitHasAlreadyAttacked, NotYourTown;
}
