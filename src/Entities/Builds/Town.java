package Entities.Builds;

import Entities.Units.Units.IUnit;
import Entities.Units.Units.NewUnit;
import Exceptions.CantBuildOrUpgradeHouse;
import Exceptions.MageAlreadyHasMinPreparationTime;
import Exceptions.NotEnoughCoins;
import Exceptions.NotEnoughResources;
import Players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Town {
    private Player player;
    private int row, col;
    private String name, symbol, color;

    private final HashMap<Buildings, ArrayList<IBuilding>> buildings;

    public Town(Player player, String name, String symbol, int row, int col) {
        this.name = name;
        this.symbol = symbol;
        this.color = player.getColor();
        this.row = row;
        this.col = col;
        this.player = player;

        buildings = new HashMap<>() {{
            for (Buildings b : Buildings.values()) {
                put(b, new ArrayList<>());
            }
        }};
    }

    public HashMap<Buildings, ArrayList<IBuilding>> getBuildings() {
        return buildings;
    }

    public void showBuildings() {
        String leftAlignment = "| %-12s | %-14s | %-90s |%n";
        System.out.printf("+--------------+----------------+--------------------------------------------------------------------------------------------+%n");
        System.out.printf("|     Name     |     Number     |                                        Description                                         |%n");
        System.out.printf("+--------------+----------------+--------------------------------------------------------------------------------------------+%n");
        for (Buildings type : Buildings.values()) {
            if (buildings.get(type).isEmpty())
                System.out.printf(leftAlignment, type.name(), buildings.get(type).size(), type.getDescription());
            else {
                Integer level = buildings.get(type).getFirst().getLevel();
                System.out.printf(leftAlignment, type.name(), "x" + buildings.get(type).size() + " --> " + level + " level", type.getDescription());
            }
            System.out.printf("+--------------+----------------+--------------------------------------------------------------------------------------------+%n");
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void healUnit(IUnit unit) {
        unit.heal();
        unit.energyRecharge();
    }


    public void buildHouse(Buildings building) throws NotEnoughResources, CantBuildOrUpgradeHouse, MageAlreadyHasMinPreparationTime, NotEnoughCoins {
        boolean hasBuilt = false;
        if (building.hasEnoughMaterials(getPlayer())) {
            switch (building) {
                case WitchHouse -> {
                    if (buildings.get(building).isEmpty()) {
                        buildings.get(building).add(new WitchHouse());
                        buildings.get(building).getFirst().buff(player);
                        hasBuilt = true;
                    } else if (buildings.get(building).getFirst().getLevel() < 3) {
                        buildings.get(building).getFirst().upgrade(getPlayer());
                        hasBuilt = true;
                    } else {
                        throw new CantBuildOrUpgradeHouse();
                    }
                }
                case Tavern -> {
                    if (buildings.get(building).isEmpty()) {
                        buildings.get(building).add(new Tavern());
                        buildings.get(building).getFirst().buff(player);
                        hasBuilt = true;
                    } else if (buildings.get(building).getFirst().getLevel() < 3) {
                        buildings.get(building).getFirst().upgrade(getPlayer());
                        hasBuilt = true;
                    } else {
                        throw new CantBuildOrUpgradeHouse();
                    }
                }
                case Forge -> {
                    if (buildings.get(building).isEmpty()) {
                        buildings.get(building).add(new Forge());
                        buildings.get(building).getFirst().buff(player);
                        hasBuilt = true;
                    } else if (buildings.get(building).getFirst().getLevel() < 3) {
                        buildings.get(building).getFirst().upgrade(getPlayer());
                        hasBuilt = true;
                    } else {
                        throw new CantBuildOrUpgradeHouse();
                    }
                }
                case Academy -> {
                    if (buildings.get(building).isEmpty()) {
                        buildings.get(building).add(new Academy());
                        hasBuilt = true;
                    } else {
                        throw new CantBuildOrUpgradeHouse();
                    }
                }
                case Arsenal -> {
                    if (buildings.get(building).isEmpty()) {
                        buildings.get(building).add(new Arsenal());
                        buildings.get(building).getFirst().buff(player);
                        hasBuilt = true;
                    } else if (buildings.get(building).getFirst().getLevel() < 3) {
                        buildings.get(building).getFirst().upgrade(getPlayer());
                        hasBuilt = true;
                    } else {
                        throw new CantBuildOrUpgradeHouse();
                    }
                }
                case Market -> {
                    if (buildings.get(building).isEmpty()) {
                        buildings.get(building).add(new Market());

                        hasBuilt = true;
                    } else {
                        throw new CantBuildOrUpgradeHouse();
                    }
                }
                case Workshop -> {
                    if (buildings.get(building).size() < 4) {
                        buildings.get(building).add(new Workshop());
                        hasBuilt = true;
                    } else {
                        throw new CantBuildOrUpgradeHouse();
                    }
                }
                case Tower -> {
                    if (buildings.get(building).isEmpty()) {
                        buildings.get(building).add(new Tower());
                        hasBuilt = true;
                    } else {
                        throw new CantBuildOrUpgradeHouse();
                    }
                }
            }
            if (hasBuilt) {
                getPlayer().setWood(getPlayer().getWood() - building.getWood());
                getPlayer().setStone(getPlayer().getStone() - building.getStone());
            }

        } else {
            throw new NotEnoughResources();
        }
    }

    public String save() {
        StringBuilder ret = new StringBuilder();
        ret.append(name).append(";;")
                .append(symbol).append(";;")
                .append(row).append(";;")
                .append(col).append("\n");
        for (Map.Entry<Buildings, ArrayList<IBuilding>> entry : buildings.entrySet()) {
            ret.append(entry.getKey()).append("--").append(entry.getValue().size());
            if (!entry.getValue().isEmpty())
                ret.append("--").append(entry.getValue().getFirst().getLevel());
            else
                ret.append("--").append("0");
            ret.append(";;");
        }
        return ret.toString();
    }
}
