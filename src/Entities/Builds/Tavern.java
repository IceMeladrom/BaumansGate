package Entities.Builds;

import Players.Player;

public class Tavern extends House implements IBuilding {
    public Tavern(){
        super(Buildings.Tavern);
    }

    @Override
    public void buff(Player player) {
        System.out.println("Tavern buff");
    }
}
