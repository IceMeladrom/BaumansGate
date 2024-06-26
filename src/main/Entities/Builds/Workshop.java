package main.Entities.Builds;

import main.Players.Player;

import static main.Utilities.Constants.Colors.*;

public class Workshop extends House implements IBuilding {
    public Workshop() {
        super(Buildings.Workshop);
    }

    @Override
    public void buff(Player player) {
        System.out.format("%sWorkshop buff%s%n", ANSI_GREEN, ANSI_RESET);
        player.addCoins(3.0);
    }
}
