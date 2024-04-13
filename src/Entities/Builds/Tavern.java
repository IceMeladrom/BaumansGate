package Entities.Builds;

import Players.Player;

import java.util.HashMap;

public class Tavern extends House implements IBuilding {
    public Tavern(){
        super(Buildings.Tavern);
    }

    @Override
    public void buff(Player player) {

    }
}
