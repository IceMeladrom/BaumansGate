package Entities.Builds;

import Players.Player;

import java.util.HashMap;

public class Forge extends House implements IBuilding{
    public Forge(){
        super(Buildings.Forge);
    }

    @Override
    public void buff(Player player) {

    }
}
