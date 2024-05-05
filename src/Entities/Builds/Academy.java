package Entities.Builds;

import Entities.Units.Units.UnitType;
import Players.Player;

public class Academy extends House implements IBuilding {
    public Academy() {
        super(Buildings.Academy);
    }

    @Override
    public void buff(Player player) {
        System.out.println("Academy buff");

//        UnitType.getNewUnitsTypes().add("aboba")

    }
}
