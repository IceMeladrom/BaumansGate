package Entities.Builds;

import Players.Player;

import java.util.HashMap;

public class Market extends House implements IBuilding {
    public Market() {
        super(Buildings.Market);
    }

    @Override
    public void buff(Player player) {

    }
}
