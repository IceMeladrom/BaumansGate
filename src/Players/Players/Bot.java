package Players.Players;

import Entities.Builds.Town;
import Entities.Units.Units.Unit;
import Grid.Grid;
import Players.Player;
import Utilities.Constants.MyRandom;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class Bot implements Player {
    private ArrayList<Unit> units = new ArrayList<>();
    private int coins;
    private String name, color;
    private static final Random random = MyRandom.getRandom();
    private Town town;

    public Bot(String name, int coins, String color) {
        this.name = name;
        this.coins = coins;
        this.color = color;
    }

    @Override
    public boolean needConsole() {
        return false;
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

    @Override
    public void setTown(Town town) {

    }

    @Override
    public Town getTown() {
        return null;
    }


    public boolean canUnitMove(@NotNull Grid grid, int row, int col) {
        Unit unit = grid.getUnit(row, col);
        return unit != null && units.contains(unit);
    }

    public void energize() {
        for (Unit unit : units)
            unit.energyRecharge();
    }

    @Override
    public void showPlayerInfo() {

    }

    public void move() {
        Grid grid = Grid.getInstance();
        int row, col;
        for (Unit unit : units) {
            while (true) {
                col = unit.getCol() + random.nextInt(-(int) unit.getEnergy(), (int) unit.getEnergy() + 1);
                row = unit.getRow() + random.nextInt(-(int) unit.getEnergy(), (int) unit.getEnergy() + 1);
                try {
                    unit.walk(row, col);
                } catch (Exception e) {
                    continue;
                }
                break;
            }
        }
    }
}
