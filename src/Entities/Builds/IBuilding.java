package Entities.Builds;

import Players.Player;

public interface IBuilding {
    public void upgrade(Player player);

    public void buff(Player player);
}
