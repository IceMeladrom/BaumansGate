package main.Players;

import main.Entities.Builds.Town;
import main.Entities.Units.Units.IUnit;
import main.Grid.Grid;

import java.util.ArrayList;

public interface Player {
    public boolean needConsole();

    public ArrayList<IUnit> getUnits();

    public void setUnits(ArrayList<IUnit> units);

    public boolean buyUnit(IUnit unit);

    public void sellUnit(IUnit unit);

    public void addUnit(IUnit unit);

    public void deleteUnit(IUnit unit);

    public String getName();

    public void setName(String name);

    public Double getCoins();

    public void setCoins(Double coins);

    public String getColor();

    public void setColor(String color);

    public void addCoins(Double coins);

    public void spendCoins(Double coins);

    public void checkCoins();

    public void setTown(Town town);

    public Town getTown();

    public boolean canUnitMove(Grid grid, int row, int col);

    public void move();

    public void energize();

    public void showPlayerInfo();

    public Integer getStone();

    public void setStone(Integer stone);

    public Integer getWood();

    public void setWood(Integer wood);

    public String save();
}
