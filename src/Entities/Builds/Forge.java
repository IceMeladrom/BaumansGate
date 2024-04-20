package Entities.Builds;

import Players.Player;

public class Forge extends House implements IBuilding{
    public Forge(){
        super(Buildings.Forge);
    }

    @Override
    public void buff(Player player) {
        System.out.println("Forge buff");
    }
}
