package Entities.Builds;

import Players.Player;

import java.util.HashMap;

public enum Buildings {
    WitchHouse(10, 5),
    Tavern(15, 5),
    Forge(5, 15),
    Arsenal(5, 15),
    Academy(10, 10),
    Market(15, 0),
    Workshop(15, 10);
    private final Integer wood, stone;

    Buildings(Integer wood, Integer stone) {
        this.wood = wood;
        this.stone = stone;
    }

    public Integer getStone() {
        return stone;
    }

    public Integer getWood() {
        return wood;
    }

    public boolean hasEnoughMaterials(Player player) {
        return player.getWood() >= wood && player.getStone() >= stone;
    }
}
