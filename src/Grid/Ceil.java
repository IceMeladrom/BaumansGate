package Grid;

import Entities.Builds.Town;
import Entities.Units.Units.Unit;
import Players.Player;

import static Utilities.Constants.Colors.ANSI_RESET;

public class Ceil {
    private Unit unit;
    private Town town;
    private String terrain;
    private Player player;

    public Ceil(Unit unit, String terrain) {
        this.unit = unit;
        this.terrain = terrain;
        if (unit == null)
            player = null;
        else
            player = unit.getPlayer();
    }

    public Unit getUnit() {
        return unit;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setUnit(Unit unit) {
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
}