package Entities.Builds;

import Entities.Damage.DamageType;
import Entities.Units.Units.NewUnit;
import Entities.Units.Units.UnitType;
import Players.Player;
import Utilities.Constants.MyScanner;

import java.util.Scanner;

public class Academy extends House implements IBuilding {
    public Academy() {
        super(Buildings.Academy);
    }

    @Override
    public void buff(Player player) {
        System.out.println("Academy buff");
        Scanner scanner = MyScanner.getScanner();

        String unitName, unitClass, symbol;
        Double hp, damageValue, defence, energy, cost;
        Integer attackRange;
        DamageType damageType;

        System.out.print("\nEnter the unit's name: ");
        unitName = scanner.nextLine();
        while (true) {
            System.out.println("\nChoose a class for the new unit\n\t1. Warrior\n\t2. Archer\n\t3. Rider\n\t4. Mage");
            switch (scanner.nextLine()) {
                case "1" -> unitClass = "Warrior";
                case "2" -> unitClass = "Archer";
                case "3" -> unitClass = "Rider";
                case "4" -> unitClass = "Mage";
                default -> {
                    System.out.println("Invalid option.");
                    continue;
                }
            }
            break;
        }
        while (true) {
            System.out.print("\nHow much hp unit has: ");
            try {
                hp = Double.parseDouble(scanner.nextLine());
                if (hp <= 0.0) {
                    System.out.println("Non positive value.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid value.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.println("\nChoose a damage type for the new unit\n\t1. Physical\n\t2. Fire\n\t3. Cold\n\t4. Acid");
            switch (scanner.nextLine()) {
                case "1" -> damageType = DamageType.Physical;
                case "2" -> damageType = DamageType.Fire;
                case "3" -> damageType = DamageType.Cold;
                case "4" -> damageType = DamageType.Acid;
                default -> {
                    System.out.println("Invalid option.");
                    continue;
                }
            }
            break;
        }
        while (true) {
            System.out.print("\nHow much damage unit has: ");
            try {
                damageValue = Double.parseDouble(scanner.nextLine());
                if (damageValue <= 0.0) {
                    System.out.println("Non positive value.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid value.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("\nHow much attack range unit has: ");
            try {
                attackRange = Integer.parseInt(scanner.nextLine());
                if (attackRange <= 0) {
                    System.out.println("Non positive value.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid value.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("\nHow much defence unit has: ");
            try {
                defence = Double.parseDouble(scanner.nextLine());
                if (defence <= 0.0) {
                    System.out.println("Non positive value.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid value.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("\nHow much energy unit has: ");
            try {
                energy = Double.parseDouble(scanner.nextLine());
                if (energy <= 0.0) {
                    System.out.println("Non positive value.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid value.");
                continue;
            }
            break;
        }
        cost = Math.floor((hp * damageValue * attackRange * defence * energy) / 300);
        while (true) {
            System.out.println("\nWhat symbol will be displayed on the map?");
            symbol = scanner.nextLine();
            if (symbol.length() != 1) {
                System.out.println("Symbol length is not 1");
                continue;
            }
            break;
        }
        NewUnit newUnit = new NewUnit(unitName, unitClass, hp, damageType, damageValue, attackRange, defence, energy, cost, symbol);
        UnitType.getNewUnitsTypes().put(unitName, newUnit);
        System.out.println("Researched new unit!");
    }
}
