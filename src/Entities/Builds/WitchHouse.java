package Entities.Builds;

import Players.Player;

import java.util.HashMap;

public class WitchHouse extends House implements IBuilding {
    public WitchHouse() {
        super(Buildings.WitchHouse);
    }

    @Override
    public void buff(Player player) {

    }
}
