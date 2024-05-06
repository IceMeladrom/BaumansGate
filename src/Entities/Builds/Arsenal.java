package Entities.Builds;

import Entities.Units.Units.IUnit;
import Entities.Units.Units.UnitType;
import Players.Player;

import java.util.ArrayList;

import static Utilities.Constants.Colors.ANSI_GREEN;
import static Utilities.Constants.Colors.ANSI_RESET;

public class Arsenal extends House implements IBuilding {
    public Arsenal() {
        super(Buildings.Arsenal);
    }

    @Override
    public void buff(Player player) {
        System.out.printf("%sArsenal buff%s%n", ANSI_GREEN, ANSI_RESET);
        ArrayList<IUnit> units = player.getUnits();
        for (IUnit unit: units){
            unit.setMaxDefence(unit.getMaxDefence()+1);
            unit.setDefence(unit.getMaxDefence());
        }

        // Change defence for future units
        for(UnitType type : UnitType.values()){
            type.setDefence(type.getDefence() + 1);
        }
    }
}
