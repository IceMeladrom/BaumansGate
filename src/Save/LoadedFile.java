package Save;

import Entities.Builds.Town;
import Entities.Units.Units.IUnit;
import Grid.Cell;
import Grid.Grid;
import Players.Player;
import Utilities.Constants.GridSize;

import java.util.ArrayList;

public class LoadedFile {
    private ArrayList<ArrayList<Cell>> grid;
    private Player me, bot;
    private Town myTown, botTown;
    private ArrayList<IUnit> myUnits, botUnits;


    public LoadedFile(ArrayList<ArrayList<Cell>> grid, Player me, Player bot, Town myTown, Town botTown, ArrayList<IUnit> myUnits, ArrayList<IUnit> botUnits) {
        this.grid = grid;
        this.me = me;
        this.bot = bot;
        this.myTown = myTown;
        this.botTown = botTown;
        this.myUnits = myUnits;
        this.botUnits = botUnits;

        GridSize.setSize(grid.size());
        Grid newGrid = Grid.getInstance();
        newGrid.setGrid(grid);

        me.setTown(myTown);
        newGrid.getCell(myTown.getRow(), myTown.getCol()).setTown(myTown);
        bot.setTown(botTown);
        newGrid.getCell(botTown.getRow(), botTown.getCol()).setTown(botTown);

        me.setUnits(myUnits);
        for(IUnit unit : myUnits)
            newGrid.getCell(unit.getRow(), unit.getCol()).setUnit(unit);
        bot.setUnits(botUnits);
        for(IUnit unit : botUnits)
            newGrid.getCell(unit.getRow(), unit.getCol()).setUnit(unit);

    }

    public ArrayList<ArrayList<Cell>> getGrid() {
        return grid;
    }

    public void setGrid(ArrayList<ArrayList<Cell>> grid) {
        this.grid = grid;
    }

    public Player getMe() {
        return me;
    }

    public void setMe(Player me) {
        this.me = me;
    }

    public Player getBot() {
        return bot;
    }

    public void setBot(Player bot) {
        this.bot = bot;
    }

    public Town getMyTown() {
        return myTown;
    }

    public void setMyTown(Town myTown) {
        this.myTown = myTown;
    }

    public Town getBotTown() {
        return botTown;
    }

    public void setBotTown(Town botTown) {
        this.botTown = botTown;
    }

    public ArrayList<IUnit> getMyUnits() {
        return myUnits;
    }

    public void setMyUnits(ArrayList<IUnit> myUnits) {
        this.myUnits = myUnits;
    }

    public ArrayList<IUnit> getBotUnits() {
        return botUnits;
    }

    public void setBotUnits(ArrayList<IUnit> botUnits) {
        this.botUnits = botUnits;
    }
}
