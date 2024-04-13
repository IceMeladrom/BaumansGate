package Entities.Builds;

import Players.Player;

import java.util.HashMap;

public class Workshop extends House implements IBuilding {
    public Workshop() {
        super(Buildings.Workshop);
    }

    @Override
    public void buff(Player player) {

    }
}
