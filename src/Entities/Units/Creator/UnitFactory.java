package Entities.Units.Creator;

import Entities.Damage.DamageFactory;
import Entities.Damage.DamageType;
import Entities.Units.Units.*;
import Entities.Units.Units.Archer;
import Entities.Units.Units.Warrior;
import Entities.Units.Units.Rider;
import Players.Player;

public class UnitFactory {
    public static IUnit createUnit(UnitType unitType, int row, int col, Player player) throws IllegalArgumentException {
        switch (unitType) {
            case Swordsman -> {
                return new Warrior(UnitType.Swordsman.getName(), UnitType.Swordsman.getHp(), UnitType.Swordsman.getDamage(), 1, 8, 3, 10, "S", row, col, player);
            }
            case Spearman -> {
                return new Warrior("Spearman", 35, DamageFactory.createDamage(DamageType.Cold, 3), 1, 4, 6, 15, "P", row, col, player);
            }
            case Axeman -> {
                return new Warrior("Axeman", 45, DamageFactory.createDamage(DamageType.Physical, 9), 1, 3, 4, 20, "A", row, col, player);
            }
            case ArcherLongBow -> {
                return new Archer("ArcherLongBow", 30, DamageFactory.createDamage(DamageType.Physical, 6), 5, 8, 2, 15, "L", row, col, player);
            }
            case ArcherShortBow -> {
                return new Archer("ArcherShortBow", 25, DamageFactory.createDamage(DamageType.Physical, 3), 3, 4, 4, 19, "Q", row, col, player);
            }
            case Crossbowman -> {
                return new Archer("Crossbowman", 40, DamageFactory.createDamage(DamageType.Acid, 7), 6, 3, 2, 23, "B", row, col, player);
            }
            case Knight -> {
                return new Rider("Knight", 30, DamageFactory.createDamage(DamageType.Physical, 5), 1, 3, 6, 20, "K", row, col, player);
            }
            case Cuirassier -> {
                return new Rider("Cuirassier", 50, DamageFactory.createDamage(DamageType.Physical, 2), 1, 7, 5, 23, "C", row, col, player);
            }
            case HorseArcher -> {
                return new Rider("HorseArcher", 25, DamageFactory.createDamage(DamageType.Physical, 3), 3, 2, 5, 25, "H", row, col, player);
            }
            case GodMelee -> {
                return new Warrior("GodMelee", 999, DamageFactory.createDamage(DamageType.Physical, 999), 1, 999, 999, 999, "G", row, col, player);
            }
            case GodRange -> {
                return new Warrior("GodRange", 999, DamageFactory.createDamage(DamageType.Physical, 999), 999, 999, 999, 999, "G", row, col, player);
            }
            case Mage -> {
                return new Mage("Mage", 15, DamageFactory.createDamage(DamageType.Fire, 9), 3, 4, 3, 30, "M", row, col, player);
            }
            default -> throw new IllegalArgumentException("You tried to choose non existed unit!");
        }
    }
}
