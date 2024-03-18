package Players.Players;

import Entities.Builds.Town;
import Entities.Units.Creator.UnitFactory;
import Entities.Units.Units.Unit;
import Entities.Units.Units.UnitTypes;
import Exceptions.AlliedUnitAtTheCeil;
import Exceptions.NotEnoughEnergy;
import Exceptions.UnitHasAlreadyAttacked;
import Grid.Grid;
import Players.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import static Utilities.Constants.Colors.*;
import static Utilities.Utils.isCoordsValid;

public class Bot implements Player {
    private ArrayList<Unit> units;
    private int coins;
    private String name;
    private String color;
    private Random random;
    private Town town;

    public Bot(String name, int coins, String color) {
        this.name = name;
        this.coins = coins;
        this.color = color;
        units = new ArrayList<>();
        random = new Random();
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public boolean buyUnit(@NotNull Unit unit) {
        if (coins < unit.getCost())
            return false;
        addUnit(unit);
        spendCoins(unit.getCost());
        return true;
    }

    public void sellUnit(@NotNull Unit unit) {
        addCoins(unit.getCost());
        deleteUnit(unit);
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void deleteUnit(Unit unit) {
        units.remove(unit);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
        checkCoins();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void addCoins(int coins) {
        this.coins += coins;
        checkCoins();
    }

    public void spendCoins(int coins) {
        this.coins -= coins;
        checkCoins();
    }

    public void checkCoins() {
        if (coins < 0)
            coins = 0;
    }

    public void placeTown(Grid grid) {
        int row = 0, col = 0;
        boolean validCoords = false;

        while (!validCoords) {
            row = random.nextInt(0, grid.getSize());
            col = random.nextInt(0, grid.getSize());
            if (grid.isEntityAtCeil(row, col) || grid.isTownAtCeil(row, col))
                continue;
            validCoords = true;
        }
        town = new Town(this, "Mordor", "M", row, col);
        grid.placeTown(this, town);
    }

    public Unit chooseUnit(Grid grid, UnitFactory unitCreator, int row, int col) {
        UnitTypes unitName;
        Unit unit;
        while (true) {
            unitName = UnitTypes.randomUnit();
            if (unitName.cost <= getCoins()) {
                unit = unitCreator.createUnit(unitName, row, col, this);
                break;
            }
        }
        return unit;

    }

    public void placeUnits(@NotNull Grid grid, UnitFactory unitCreator) {
        Unit unit;
        int row = 0, col = 0;
        while (true) {
            do {
                row = random.nextInt(0, grid.getSize());
                col = random.nextInt(0, grid.getSize());
            } while (grid.isEntityAtCeil(row, col));
            if (getCoins() < 10)
                break;
            unit = chooseUnit(grid, unitCreator, row, col);
            buyUnit(unit);
            grid.placeUnit(this, unit);
        }
    }


    public boolean canUnitMove(@NotNull Grid grid, int row, int col) {
        Unit unit = grid.getUnit(row, col);
        return unit != null && units.contains(unit);
    }

    public void energize() {
        for (Unit unit : units)
            unit.energyRecharge();
    }


    public void move(Grid grid) {
        int row, col;
        for (Unit unit : units) {
            while (true) {
                col = unit.getCol() + random.nextInt(-(int) unit.getEnergy(), (int) unit.getEnergy() + 1);
                row = unit.getRow() + random.nextInt(-(int) unit.getEnergy(), (int) unit.getEnergy() + 1);
                try {
                    unit.walk(grid, row, col);
                } catch (Exception e) {
                    continue;
                }
                break;
            }
        }
    }
}
