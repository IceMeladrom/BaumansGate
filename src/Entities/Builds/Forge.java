package Entities.Builds;

import Entities.Units.Units.IUnit;
import Entities.Units.Units.UnitType;
import Players.Player;

import java.util.ArrayList;

import static Utilities.Constants.Colors.ANSI_GREEN;
import static Utilities.Constants.Colors.ANSI_RESET;

public class Forge extends House implements IBuilding {
    public Forge() {
        super(Buildings.Forge);
    }

    @Override
    public void buff(Player player) {
        System.out.printf("%sForge buff%s%n", ANSI_GREEN, ANSI_RESET);
        ArrayList<IUnit> units = player.getUnits();
        for (IUnit unit : units) {
            unit.getDamage().setValue(unit.getDamage().getValue() + 1);
        }

        // Change defence for future units
        for (UnitType type : UnitType.values()) {
            type.setDamageValue(type.getDamageValue() + 1);
        }
    }
}
