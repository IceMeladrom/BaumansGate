package Grid;

import Entities.Builds.Town;
import Entities.Units.Units.IUnit;
import Players.Player;
import Utilities.Constants.GridSize;

import java.util.ArrayList;
import java.util.Random;

import static Utilities.Constants.Colors.*;
import static Utilities.Utils.clearConsole;

public class Grid {
    private int size;
    private static Grid instance;
    ArrayList<ArrayList<Cell>> grid = new ArrayList<>(size);

    public static Grid getInstance() {
        if (instance == null) {
            instance = new Grid();
        }
        return instance;
    }

    private Grid() {
        this.size = GridSize.size;
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            grid.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++) {
                grid.get(i).add(new Cell(i, j, null, generate(rand)));
            }
        }
    }

    public void show() {
        clearConsole();

        ArrayList<String> temp = new ArrayList<>();

        String leftAlignment = "| %-3s | %-6s |";
        String space1Alignment = " %-1s |";
        String space2Alignment = " %-1s  |";

        System.out.format("+-----+--------+");
        for (int i = 0; i < size; i++) {
            if ((i + 1) / 10 == 0)
                System.out.format("---+");
            else
                System.out.format("----+");
        }
        System.out.println();
        System.out.format("|     | Column |");
        for (int i = 0; i < size; i++) {
            System.out.format(" " + (i + 1) + " |");
        }
        System.out.println();
        System.out.format("+-----+--------+");
        for (int i = 0; i < size; i++) {
            if ((i + 1) / 10 == 0)
                System.out.format("---+");
            else
                System.out.format("----+");
        }
        System.out.println();

        System.out.format(leftAlignment, "Row", "");
        for (int i = 0; i < size; i++) {
            if ((i + 1) / 10 == 0)
                System.out.format(space1Alignment, "");
            else
                System.out.format(space2Alignment, "");
        }
        System.out.println();
        System.out.format("+-----+--------+");
        for (int i = 0; i < size; i++) {
            if ((i + 1) / 10 == 0)
                System.out.format("---+");
            else
                System.out.format("----+");
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.format(leftAlignment, i + 1, "");
            for (int j = 0; j < size; j++) {
                if ((j + 1) / 10 == 0)
                    System.out.format(space1Alignment, getCeil(grid.get(i).get(j)));
                else
                    System.out.format(space2Alignment, getCeil(grid.get(i).get(j)));

            }
            System.out.println();
            System.out.format("+-----+--------+");
            for (int k = 0; k < size; k++) {
                if ((k + 1) / 10 == 0)
                    System.out.format("---+");
                else
                    System.out.format("----+");
            }
            System.out.println();
        }
    }

    public boolean isUnitAtCeil(int row, int col) {
        return grid.get(row).get(col).getUnit() != null;
    }

    public boolean isTownAtCeil(int row, int col) {
        return grid.get(row).get(col).getTown() != null;
    }

    public boolean isEntityAtCeil(int row, int col) {
        return isUnitAtCeil(row, col) || isTownAtCeil(row, col);
    }

    public IUnit getUnit(int row, int col) {
        return grid.get(row).get(col).getUnit();
    }

    private String generate(Random rand) {
        int chance = rand.nextInt(100);
        if (chance < 70) {
            return "*";
        } else if (chance < 80) {
            return "#";
        } else if (chance < 90) {
            return "@";
        } else {
            return "!";
        }
    }

    private String getCeil(Cell cell) {
        if (cell.getUnit() == null && cell.getTown() == null)
            return cell.getTerrain();
        if (cell.getTown() != null && cell.getUnit() != null)
            return ANSI_WHITE_BACKGROUND + ANSI_BOLD + cell.getColor() + cell.getUnit().getSymbol() + ANSI_RESET;
        if (cell.getTown() != null)
            return ANSI_BOLD + cell.getColor() + cell.getTown().getSymbol() + ANSI_RESET;
        return cell.getColor() + cell.getUnit().getSymbol() + ANSI_RESET;
    }

    public void placeUnit(Player player, IUnit unit) {
        int row = unit.getRow(), col = unit.getCol();
        Cell cell = grid.get(row).get(col);
        cell.setUnit(unit);
        cell.setPlayer(player);
//        ceil.setColor(player.getColor());
    }

    public void placeTown(Player player, Town town) {
        int row = town.getRow(), col = town.getCol();
        Cell cell = grid.get(row).get(col);
        cell.setTown(town);
        cell.setPlayer(player);
//        ceil.setColor(player.getColor());
    }

    public Player getPlayerByCeil(int row, int col) {
        return grid.get(row).get(col).getPlayer();
    }

    public void deleteUnit(int row, int col) {
        Cell cell = grid.get(row).get(col);
        cell.setUnit(null);
//        ceil.setColor(ANSI_RESET);
    }

    public Cell getCeil(int row, int col) {
        return grid.get(row).get(col);
    }

    public int getSize() {
        return size;
    }
}