package Entities.Actions;

import Exceptions.AlliedUnitAtTheCeil;
import Exceptions.NotEnoughEnergy;
import Exceptions.NotYourTown;
import Exceptions.UnitHasAlreadyAttacked;
import Grid.Grid;

public interface Walkable {
    public void walk(Grid grid, int row, int col) throws NotEnoughEnergy, AlliedUnitAtTheCeil, UnitHasAlreadyAttacked, NotYourTown;
}
