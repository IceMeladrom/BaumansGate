package Entities.Builds;

import Exceptions.MageAlreadyHasMinPreparationTime;
import Exceptions.NotEnoughCoins;
import Players.Player;

import java.util.HashMap;

public interface IBuilding {
    public void upgrade(Player player);

    public void buff(Player player) throws MageAlreadyHasMinPreparationTime, NotEnoughCoins;

    public Integer getLevel();

    public void setLevel(Integer level);

    public HashMap<Integer, HashMap<String, Integer>> getCost();
}
