package Players.Players;

import Entities.Builds.Town;
import Entities.Units.Units.IUnit;
import Grid.Grid;
import Grid.Pathfinder;
import Players.Player;
import Utilities.Constants.MyRandom;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class Bot implements Player {
    private ArrayList<IUnit> units = new ArrayList<>();
    private int coins;
    private String name, color;
    private static final Random random = MyRandom.getRandom();
    private Town town;
    private Integer wood, stone;

    public Bot(String name, int coins, String color, Integer wood, Integer stone) {
        this.name = name;
        this.coins = coins;
        this.color = color;
        this.wood = wood;
        this.stone = stone;
    }

    @Override
    public boolean needConsole() {
        return false;
    }

    public ArrayList<IUnit> getUnits() {
        return units;
    }

    public boolean buyUnit(@NotNull IUnit unit) {
        if (coins < unit.getCost())
            return false;
        addUnit(unit);
        spendCoins(unit.getCost());
        return true;
    }

    public void sellUnit(@NotNull IUnit unit) {
        addCoins(unit.getCost());
        deleteUnit(unit);
    }

    public void addUnit(IUnit unit) {
        units.add(unit);
    }

    public void deleteUnit(IUnit unit) {
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
        this.town = town;
    }

    @Override
    public Town getTown() {
        return town;
    }


    public boolean canUnitMove(@NotNull Grid grid, int row, int col) {
        IUnit unit = grid.getUnit(row, col);
        return unit != null && units.contains(unit);
    }

    public void energize() {
        for (IUnit unit : units) {
            unit.energyRecharge();
            unit.setDidAttack(false);
            unit.preparing();
        }
    }

    @Override
    public void showPlayerInfo() {
    }

    @Override
    public Integer getStone() {
        return stone;
    }

    @Override
    public void setStone(Integer stone) {
        this.stone = stone;
    }

    @Override
    public Integer getWood() {
        return wood;
    }

    @Override
    public void setWood(Integer wood) {
        this.wood = wood;
    }

    public void move() {
        int row, col;
        for (IUnit unit : units) {
            while (true) {
                Pathfinder.reset();
                Pathfinder.availableCells(unit);
                col = unit.getCol() + random.nextInt(-unit.getEnergy().intValue(), unit.getEnergy().intValue() + 1);
                row = unit.getRow() + random.nextInt(-unit.getEnergy().intValue(), unit.getEnergy().intValue() + 1);
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
