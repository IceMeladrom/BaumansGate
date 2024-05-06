package Entities.Units.Creator;

import Entities.Damage.Damage;
import Entities.Units.Units.*;
import Players.Player;

public class UnitFactory {
    public static IUnit createUnit(UnitType unitType, int row, int col, Player player) throws IllegalArgumentException {
        switch (unitType) {
            case Swordsman, Spearman, Axeman, GodMelee, GodRange -> {
                return new Warrior(unitType.getName(), unitType.getHp(), new Damage(unitType.getDamageType(), unitType.getDamageValue()), unitType.getAttackRange(), unitType.getDefence(), unitType.getEnergy(), unitType.getCost(), unitType.getSymbol(), row, col, player);
            }
            case ArcherLongBow, ArcherShortBow, Crossbowman -> {
                return new Archer(unitType.getName(), unitType.getHp(), new Damage(unitType.getDamageType(), unitType.getDamageValue()), unitType.getAttackRange(), unitType.getDefence(), unitType.getEnergy(), unitType.getCost(), unitType.getSymbol(), row, col, player);
            }
            case Knight, Cuirassier, HorseArcher -> {
                return new Rider(unitType.getName(), unitType.getHp(), new Damage(unitType.getDamageType(), unitType.getDamageValue()), unitType.getAttackRange(), unitType.getDefence(), unitType.getEnergy(), unitType.getCost(), unitType.getSymbol(), row, col, player);
            }
            case Mage -> {
                return new Mage(unitType.getName(), unitType.getHp(), new Damage(unitType.getDamageType(), unitType.getDamageValue()), unitType.getAttackRange(), unitType.getDefence(), unitType.getEnergy(), unitType.getCost(), unitType.getSymbol(), row, col, player);
            }
            default -> throw new IllegalArgumentException("You tried to choose non existed unit!");
        }
    }

    public static IUnit createUnit(NewUnit unitType, int row, int col, Player player) {
        switch (unitType.getUnitClass()) {
            case "Warrior" -> {
                return new Warrior(unitType.getName(), unitType.getHp(), new Damage(unitType.getDamageType(), unitType.getDamageValue()), unitType.getAttackRange(), unitType.getDefence(), unitType.getEnergy(), unitType.getCost(), unitType.getSymbol(), row, col, player);
            }
            case "Archer" -> {
                return new Archer(unitType.getName(), unitType.getHp(), new Damage(unitType.getDamageType(), unitType.getDamageValue()), unitType.getAttackRange(), unitType.getDefence(), unitType.getEnergy(), unitType.getCost(), unitType.getSymbol(), row, col, player);
            }
            case "Rider" -> {
                return new Rider(unitType.getName(), unitType.getHp(), new Damage(unitType.getDamageType(), unitType.getDamageValue()), unitType.getAttackRange(), unitType.getDefence(), unitType.getEnergy(), unitType.getCost(), unitType.getSymbol(), row, col, player);
            }
            case "Mage" -> {
                return new Mage(unitType.getName(), unitType.getHp(), new Damage(unitType.getDamageType(), unitType.getDamageValue()), unitType.getAttackRange(), unitType.getDefence(), unitType.getEnergy(), unitType.getCost(), unitType.getSymbol(), row, col, player);
            }
            default -> throw new IllegalArgumentException("You tried to choose non existed unit!");
        }
    }


}
