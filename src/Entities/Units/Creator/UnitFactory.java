package Entities.Units.Creator;

import Entities.Units.Units.*;
import Players.Player;

public class UnitFactory {
    public static Unit createUnit(UnitTypes unitTypes, int row, int col, Player player) throws IllegalArgumentException {
        switch (unitTypes) {
            case Swordsman -> {
                return new WarriorClass("Swordsman", 50, 5, 1, 8, 3, 10, "S", row, col, player);
            }
            case Spearman -> {
                return new WarriorClass("Spearman", 35, 3, 1, 4, 6, 15, "P", row, col, player);
            }
            case Axeman -> {
                return new WarriorClass("Axeman", 45, 9, 1, 3, 4, 20, "A", row, col, player);
            }
            case ArcherLongBow -> {
                return new ArcherClass("ArcherLongBow", 30, 6, 5, 8, 2, 15, "L", row, col, player);
            }
            case ArcherShortBow -> {
                return new ArcherClass("ArcherShortBow", 25, 3, 3, 4, 4, 19, "Q", row, col, player);
            }
            case Crossbowman -> {
                return new ArcherClass("Crossbowman", 40, 7, 6, 3, 2, 23, "B", row, col, player);
            }
            case Knight -> {
                return new RiderClass("Knight", 30, 5, 1, 3, 6, 20, "K", row, col, player);
            }
            case Cuirassier -> {
                return new RiderClass("Cuirassier", 50, 2, 1, 7, 5, 23, "C", row, col, player);
            }
            case HorseArcher -> {
                return new RiderClass("HorseArcher", 25, 3, 3, 2, 5, 25, "H", row, col, player);
            }
            case GodMelee -> {
                return new WarriorClass("GodMelee", 999, 999, 1, 999, 999, 999, "G", row, col, player);
            }
            case GodRange -> {
                return new WarriorClass("GodRange", 999, 999, 999, 999, 999, 999, "G", row, col, player);
            }
            default -> throw new IllegalArgumentException("You tried to choose non existed unit!");
        }
    }
}
