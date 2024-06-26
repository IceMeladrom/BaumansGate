package main.Entities.Builds;

import main.Exceptions.MageAlreadyHasMinPreparationTime;
import main.Exceptions.NotEnoughCoins;
import main.Players.Player;

import java.util.HashMap;

public abstract class House implements IBuilding {
    private Integer level;
    private final HashMap<Integer, HashMap<String, Integer>> cost;

    public House(Buildings building) {
        cost = new HashMap<>() {{
            for (int i = 1; i <= 3; i++) {
                int finalI = i;
                put(i, new HashMap<>() {{
                    put("wood", building.getWood() + (5 * (finalI - 1)));
                    put("stone", building.getStone() + (5 * (finalI - 1)));
                }});
            }
        }};
        level = 1;
    }

    @Override
    public void upgrade(Player player) {
        level += 1;
        try {
            buff(player);
        } catch (MageAlreadyHasMinPreparationTime | NotEnoughCoins e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public HashMap<Integer, HashMap<String, Integer>> getCost() {
        return cost;
    }
}
