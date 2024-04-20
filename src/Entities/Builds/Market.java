package Entities.Builds;

import Players.Player;

public class Market extends House implements IBuilding {
    public Market() {
        super(Buildings.Market);
    }

    @Override
    public void buff(Player player) {
        System.out.println("Market buff");
    }
}
