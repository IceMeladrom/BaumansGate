package Entities.Units.Creator;

import Entities.Units.Units.*;
import Entities.Units.Units.Physical.PhysicalArcher;
import Entities.Units.Units.Physical.PhysicalRider;
import Entities.Units.Units.Physical.PhysicalWarrior;
import Players.Player;

public class PhysicalUnitFactory implements UnitFactory {
    @Override
    public IWarrior createWarrior(UnitTypes unitType, int row, int col, Player player) throws IllegalArgumentException {
        switch (unitType) {
            case Swordsman -> {
                return new PhysicalWarrior("Swordsman", 50, 5, 1, 8, 3, 10, "S", row, col, player);
            }
            case Spearman -> {
                return new PhysicalWarrior("Spearman", 35, 3, 1, 4, 6, 15, "P", row, col, player);
            }
            case Axeman -> {
                return new PhysicalWarrior("Axeman", 45, 9, 1, 3, 4, 20, "A", row, col, player);
            }
            default -> throw new IllegalArgumentException("You tried to choose non existed unit!");
        }
    }

    @Override
    public IArcher createArcher(UnitTypes unitType, int row, int col, Player player) throws IllegalArgumentException {
        switch (unitType) {
            case ArcherLongBow -> {
                return new PhysicalArcher("ArcherLongBow", 30, 6, 5, 8, 2, 15, "L", row, col, player);
            }
            case ArcherShortBow -> {
                return new PhysicalArcher("ArcherShortBow", 25, 3, 3, 4, 4, 19, "Q", row, col, player);
            }
            case Crossbowman -> {
                return new PhysicalArcher("Crossbowman", 40, 7, 6, 3, 2, 23, "B", row, col, player);
            }
            default -> throw new IllegalArgumentException("You tried to choose non existed unit!");
        }
    }

    @Override
    public IRider createRider(UnitTypes unitType, int row, int col, Player player) throws IllegalArgumentException {
        switch (unitType) {
            case Knight -> {
                return new PhysicalRider("Knight", 30, 5, 1, 3, 6, 20, "K", row, col, player);
            }
            case Cuirassier -> {
                return new PhysicalRider("Cuirassier", 50, 2, 1, 7, 5, 23, "C", row, col, player);
            }
            case HorseArcher -> {
                return new PhysicalRider("HorseArcher", 25, 3, 3, 2, 5, 25, "H", row, col, player);
            }
            default -> throw new IllegalArgumentException("You tried to choose non existed unit!");
        }
    }

    @Override
    public IMage createMage(UnitTypes unitType, int row, int col, Player player) throws IllegalArgumentException {
        throw new IllegalArgumentException("You tried to choose non existed unit!");
    }
}
