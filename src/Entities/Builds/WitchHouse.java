package Entities.Builds;

import Players.Player;

import java.util.HashMap;

public class WitchHouse implements IBuilding {
    private Integer level;
    private final HashMap<Integer, HashMap<String, Integer>> cost = new HashMap<>() {{
        put(1, new HashMap<>() {{
            put("wood", 10);
            put("stone", 5);
        }});
        put(2, new HashMap<>() {{
            put("wood", 15);
            put("stone", 10);
        }});
        put(3, new HashMap<>() {{
            put("wood", 20);
            put("stone", 15);
        }});
    }};

    public WitchHouse() {
        level = 1;
    }

    @Override
    public void upgrade(Player player) {
        if (player.getWood() >= cost.get(level + 1).get("wood") && player.getStone() >= cost.get(level + 1).get("stone"))
            level += 1;
    }

    @Override
    public void buff(Player player) {

    }
}
