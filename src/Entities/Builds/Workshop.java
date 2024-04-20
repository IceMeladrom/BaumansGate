package Entities.Builds;

import Players.Player;

public class Workshop extends House implements IBuilding {
    public Workshop() {
        super(Buildings.Workshop);
    }

    @Override
    public void buff(Player player) {
        System.out.println("Workshop buff");
    }
}
