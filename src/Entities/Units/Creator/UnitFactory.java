package Entities.Units.Creator;

import Entities.Units.Units.*;
import Players.Player;

public interface UnitFactory {
    public IWarrior createWarrior(UnitTypes unitType, int row, int col, Player player) throws IllegalArgumentException;

    public IArcher createArcher(UnitTypes unitType, int row, int col, Player player) throws IllegalArgumentException;

    public IRider createRider(UnitTypes unitType, int row, int col, Player player) throws IllegalArgumentException;

    public IMage createMage(UnitTypes unitType, int row, int col, Player player) throws IllegalArgumentException;
}
