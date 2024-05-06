package Entities.Builds;

import Players.Player;

import java.util.HashMap;

public interface IBuilding {
    public void upgrade(Player player);

    public void buff(Player player);

    public Integer getLevel();

    public void setLevel(Integer level);

    public HashMap<Integer, HashMap<String, Integer>> getCost();
}
