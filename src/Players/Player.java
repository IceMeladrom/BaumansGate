package Players;

import Entities.Units.Creator.UnitFactory;
import Entities.Units.Units.Unit;
import Grid.Grid;

import java.util.ArrayList;

public interface Player {
    public ArrayList<Unit> getUnits();

    public boolean buyUnit(Unit unit);

    public void sellUnit(Unit unit);

    public void addUnit(Unit unit);

    public void deleteUnit(Unit unit);

    public String getName();

    public void setName(String name);

    public int getCoins();

    public void setCoins(int coins);

    public String getColor();

    public void setColor(String color);

    public void addCoins(int coins);

    public void spendCoins(int coins);

    public void checkCoins();

    public void placeTown(Grid grid);

    public Unit chooseUnit(Grid grid, UnitFactory unitCreator, int row, int col);

    public void placeUnits(Grid grid, UnitFactory unitCreator);

    public boolean canUnitMove(Grid grid, int row, int col);

    public void energize();

}
