package Entities.Builds;

import Players.Player;

import java.util.HashMap;

public abstract class House implements IBuilding {
    private Integer level;
    private final HashMap<Integer, HashMap<String, Integer>> cost;

    public House(Buildings building) {
        cost = new HashMap<>() {{
            for (int i = 1; i <= 3; i++) {
                int finalI = i;
                put(i, new HashMap<>() {{
                    System.out.println(this.getClass().getName());
                    put("wood", building.getWood() + (5 * (finalI - 1)));
                    put("stone", building.getStone() + (5 * (finalI - 1)));
                }});
            }
        }};
        level = 1;
    }

    @Override
    public void upgrade(Player player) {
        if (player.getWood() >= cost.get(level + 1).get("wood") && player.getStone() >= cost.get(level + 1).get("stone"))
            level += 1;
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
