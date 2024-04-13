package Entities.Builds;

import Entities.Units.Units.IUnit;
import Players.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class Town {
    private Player player;
    private int row, col;
    private String name, symbol, color;

    private HashMap<Buildings, ArrayList<IBuilding>> buildings;

    public Town(Player player, String name, String symbol, int row, int col) {
        this.name = name;
        this.symbol = symbol;
        this.color = player.getColor();
        this.row = row;
        this.col = col;
        this.player = player;

        buildings.put(Buildings.WitchHouse, new ArrayList<>(1));
        buildings.put(Buildings.Tavern, new ArrayList<>(1));
        buildings.put(Buildings.Forge, new ArrayList<>(1));
        buildings.put(Buildings.Arsenal, new ArrayList<>(1));
        buildings.put(Buildings.Academy, new ArrayList<>(1));
        buildings.put(Buildings.Market, new ArrayList<>(1));
        buildings.put(Buildings.Workshop, new ArrayList<>(4));

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


    public void buildBuilding(Buildings building) {
        if (building.hasEnoughMaterials(getPlayer())) {
            switch (building) {
                case WitchHouse -> {
                    if (buildings.get(building).isEmpty()) buildings.get(building).add(new WitchHouse());
                }
                case Tavern -> {
                    if (buildings.get(building).isEmpty()) buildings.get(building).add(new Tavern());
                }
                case Forge -> {
                    if (buildings.get(building).isEmpty()) buildings.get(building).add(new Forge());
                }
                case Academy -> {
                    if (buildings.get(building).isEmpty()) buildings.get(building).add(new Academy());
                }
                case Arsenal -> {
                    if (buildings.get(building).isEmpty()) buildings.get(building).add(new Arsenal());
                }
                case Market -> {
                    if (buildings.get(building).isEmpty()) buildings.get(building).add(new Market());
                }
                case Workshop -> {
                    if (buildings.get(building).size() < 4) buildings.get(building).add(new Workshop());
                }
            }
        }
    }
}
