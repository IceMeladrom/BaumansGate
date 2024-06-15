package main.Grid;

import main.Entities.Builds.Town;
import main.Entities.Units.Units.IUnit;
import main.Players.Player;

import static main.Utilities.Constants.Colors.ANSI_RESET;

public class Cell {
    private IUnit unit;
    private Town town;
    private String terrain;
    private Player player;
    private Boolean availableForUnit;
    private int row, col;

    public Cell(int row, int col, IUnit unit, String terrain) {
        this.row = row;
        this.col = col;
        this.unit = unit;
        this.terrain = terrain;
        if (unit == null)
            player = null;
        else
            player = unit.getPlayer();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public IUnit getUnit() {
        return unit;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setUnit(IUnit unit) {
        if (unit == null) {
            if (town == null)
                this.player = null;
            this.unit = null;
            return;
        }
        this.player = unit.getPlayer();
        this.unit = unit;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public String getColor() {
        if (getPlayer() == null)
            return ANSI_RESET;
        return getPlayer().getColor();
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
        this.player = town.getPlayer();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        if (player == null) {
            this.player = null;
            this.unit = null;
            return;
        }
        this.player = player;
    }

    public Boolean getAvailableForUnit() {
        return availableForUnit;
    }

    public void setAvailableForUnit(Boolean availableForUnit) {
        this.availableForUnit = availableForUnit;
    }
}