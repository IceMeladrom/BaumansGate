package Players;

import Entities.Builds.Town;
import Entities.Units.Units.IUnit;
import Grid.Grid;

import java.util.ArrayList;

public interface Player {
    public boolean needConsole();

    public ArrayList<IUnit> getUnits();

    public boolean buyUnit(IUnit unit);

    public void sellUnit(IUnit unit);

    public void addUnit(IUnit unit);

    public void deleteUnit(IUnit unit);

    public String getName();

    public void setName(String name);

    public int getCoins();

    public void setCoins(int coins);

    public String getColor();

    public void setColor(String color);

    public void addCoins(int coins);

    public void spendCoins(int coins);

    public void checkCoins();

    public void setTown(Town town);

    public Town getTown();

    public boolean canUnitMove(Grid grid, int row, int col);

    public void energize();

    public void showPlayerInfo();

    public Integer getStone();

    public void setStone(Integer stone);

    public Integer getWood();

    public void setWood(Integer wood);
}
