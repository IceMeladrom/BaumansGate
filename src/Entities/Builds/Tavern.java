package Entities.Builds;

import Entities.Units.Units.IUnit;
import Entities.Units.Units.UnitType;
import Players.Player;
import Utilities.Constants.MyScanner;

import java.util.HashMap;
import java.util.Scanner;

import static Utilities.Constants.Colors.*;

public class Tavern extends House implements IBuilding {
    public Tavern() {
        super(Buildings.Tavern);
    }

    @Override
    public void buff(Player player) {
        System.out.printf("%sTavern buff%s%n", ANSI_GREEN, ANSI_RESET);

        Scanner scanner = MyScanner.getScanner();
        System.out.println("1. +0.5 to max energy\n2. -0.5 to reduce movement penalties");
        while (true) {
            System.out.print("Enter the option: ");
            String option = scanner.nextLine();
            switch (option) {
                case "1" -> {
                    for (IUnit unit : player.getUnits()) {
                        unit.setMaxEnergy(unit.getMaxEnergy() + 0.5);
                        unit.energyRecharge();
                    }
                    for (UnitType type : UnitType.values()) {
                        type.setEnergy(type.getEnergy() + 1.0);
                    }
                }
                case "2" -> {
                    for (IUnit unit : player.getUnits()) {
                        HashMap<String, Double> unitTerrains = unit.getTerrains();
                        for (UnitType type : UnitType.values()) {
                            HashMap<String, Double> typeTerrains = type.getTerrains();
                            for (String key : unitTerrains.keySet()) {
                                if (unitTerrains.get(key) - 0.2 < 1e-6) {
                                    unitTerrains.put(key, 0.0);
                                    // Change energy for future units
                                    typeTerrains.put(key, 0.0);
                                } else {
                                    unitTerrains.put(key, unitTerrains.get(key) - 0.2);
                                    // Change energy for future units
                                    typeTerrains.put(key, typeTerrains.get(key) - 0.2);
                                }
                            }
                        }
                    }
                }
                default -> {
                    System.out.printf("%sInvalid option%s%n", ANSI_RED, ANSI_RESET);
                    continue;
                }
            }
            break;
        }
    }
}
