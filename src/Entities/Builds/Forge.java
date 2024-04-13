package Entities.Builds;

import Players.Player;

import java.util.HashMap;

public class Forge  implements IBuilding{
    private Integer level;
    private final HashMap<Integer, HashMap<String, Integer>> cost = new HashMap<>() {{
        for (int i = 1; i <= 3; i++) {
            int finalI = i;
            put(i, new HashMap<>() {{
                put("wood", Buildings.valueOf(this.getClass().getName()).getWood() + (5 * (finalI - 1)));
                put("stone", Buildings.valueOf(this.getClass().getName()).getStone() + (5 * (finalI - 1)));
            }});
        }
    }};

    public Forge(){
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
