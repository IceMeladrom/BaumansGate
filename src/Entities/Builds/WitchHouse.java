package Entities.Builds;

import Players.Player;

public class WitchHouse extends House implements IBuilding {
    public WitchHouse() {
        super(Buildings.WitchHouse);
    }

    @Override
    public void buff(Player player) {
        System.out.println("WitchHouse buff");
    }
}
