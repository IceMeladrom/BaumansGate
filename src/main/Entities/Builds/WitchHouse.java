package main.Entities.Builds;

import main.Entities.Units.Units.IUnit;
import main.Entities.Units.Units.UnitType;
import main.Players.Player;

import java.util.ArrayList;

import static main.Utilities.Constants.Colors.*;

public class WitchHouse extends House implements IBuilding {
    public WitchHouse() {
        super(Buildings.WitchHouse);
    }

    @Override
    public void buff(Player player) {
        System.out.printf("%sWitchHouse buff%s%n", ANSI_GREEN, ANSI_RESET);
        ArrayList<IUnit> units = player.getUnits();
        for (IUnit unit: units){
            unit.setMaxHp(unit.getMaxHp()+1);
            unit.heal();
        }

        // Change hp for future units
        for(UnitType type : UnitType.values()){
            type.setHp(type.getHp() + 1);
        }
    }
}
