package Entities.Builds;

import Exceptions.CantBuildOrUpgradeHouse;
import Players.Player;

import java.util.ArrayList;
import java.util.HashMap;

public enum Buildings {
    WitchHouse(10, 5, "+1 per level to max HP."),
    Tavern(15, 5, "+0.5 per level to max energy or -0.2 per level to reduce movement penalties."),
    Forge(5, 15, "+1 per level to max damage."),
    Arsenal(5, 15, "+1 per level to max defense."),
    Academy(10, 10, "Military research. Design new unit."),
    Market(15, 0, "Just a market. Exchange wood -> stone or stone -> wood."),
    Workshop(15, 10, "Rent out to residents to earn coins.");
    private final Integer wood, stone;
    private final String description;

    Buildings(Integer wood, Integer stone, String description) {
        this.wood = wood;
        this.stone = stone;
        this.description = description;
    }

    public Integer getStone() {
        return stone;
    }

    public Integer getWood() {
        return wood;
    }

    public boolean hasEnoughMaterials(Player player) throws CantBuildOrUpgradeHouse {
        HashMap<Buildings, ArrayList<IBuilding>> buildings = player.getTown().getBuildings();

        if (!buildings.get(this).isEmpty() && buildings.get(this).getFirst().getLevel() == 3)
            throw new CantBuildOrUpgradeHouse();

        if (buildings.get(this).isEmpty())
            return player.getWood() >= wood && player.getStone() >= stone;
        else {
            Integer level = player.getTown().getBuildings().get(this).getFirst().getLevel();
            HashMap<Integer, HashMap<String, Integer>> costs = player.getTown().getBuildings().get(this).getFirst().getCost();
            return player.getWood() >= costs.get(level + 1).get("wood") && player.getStone() >= costs.get(level + 1).get("stone");
        }
    }

    public String getDescription() {
        return description;
    }

    public static void showBuildingsShop(Player player) {
        Town town = player.getTown();
        HashMap<Buildings, ArrayList<IBuilding>> buildings = town.getBuildings();
        Integer level, wood, stone;

        String leftAlignment = "| %-12s | %-14s | %-4s | %-5s | %-90s |%n";
        System.out.printf("+--------------+----------------+------+-------+--------------------------------------------------------------------------------------------+%n");
        System.out.printf("|     Name     |     Number     | Wood | Stone |                                        Description                                         |%n");
        System.out.printf("+--------------+----------------+------+-------+--------------------------------------------------------------------------------------------+%n");
        for (Buildings type : Buildings.values()) {
            if (buildings.get(type).isEmpty())
                System.out.printf(leftAlignment, type.name(), buildings.get(type).size(), type.getWood(), type.getStone(), type.getDescription());
            else {
                level = buildings.get(type).getFirst().getLevel();
                switch (type) {
                    case Academy, Market ->
                            System.out.printf(leftAlignment, type.name(), "x" + buildings.get(type).size() + " --> " + level + " level", "-", "-", type.getDescription());
                    case Workshop -> {
                        if (buildings.get(type).size() == 4)
                            System.out.printf(leftAlignment, type.name(), "x" + buildings.get(type).size() + " --> " + level + " level", "-", "-", type.getDescription());
                        else {
                            wood = buildings.get(type).getFirst().getCost().get(level + 1).get("wood");
                            stone = buildings.get(type).getFirst().getCost().get(level + 1).get("stone");
                            System.out.printf(leftAlignment, type.name(), "x" + buildings.get(type).size() + " --> " + level + " level", wood, stone, type.getDescription());
                        }
                    }
                    default -> {
                        if (level == 3)
                            System.out.printf(leftAlignment, type.name(), "x" + buildings.get(type).size() + " --> " + level + " level", "-", "-", type.getDescription());
                        else {
                            wood = buildings.get(type).getFirst().getCost().get(level + 1).get("wood");
                            stone = buildings.get(type).getFirst().getCost().get(level + 1).get("stone");
                            System.out.printf(leftAlignment, type.name(), "x" + buildings.get(type).size() + " --> " + level + " level", wood, stone, type.getDescription());
                        }
                    }
                }
            }
            System.out.printf("+--------------+----------------+------+-------+--------------------------------------------------------------------------------------------+%n");
        }
    }
}
