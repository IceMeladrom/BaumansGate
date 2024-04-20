package Entities.Builds;

import Players.Player;

public class Arsenal extends House implements IBuilding {
    public Arsenal() {
        super(Buildings.Arsenal);
    }

    @Override
    public void buff(Player player) {
        System.out.println("Arsenal buff");
    }
}
