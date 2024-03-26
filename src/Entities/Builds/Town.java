package Entities.Builds;

import Entities.Units.Units.IUnit;
import Players.Player;

public class Town {
    private String name, symbol, color;
    private int row, col;

    private Player player;

    public Town(Player player, String name, String symbol, int row, int col) {
        this.name = name;
        this.symbol = symbol;
        this.color = player.getColor();
        this.row = row;
        this.col = col;
        this.player = player;
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
}
