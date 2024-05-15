package Grid;

import Entities.Builds.Town;
import Entities.Units.Units.IUnit;
import Players.Player;
import Utilities.Constants.GridSize;
import Utilities.Constants.MyScanner;
import Utilities.Pair;

import java.util.*;

import static Utilities.Constants.Colors.*;
import static Utilities.Utils.clearConsole;

public class Grid {
    private int size;
    private static Grid instance;
    private ArrayList<ArrayList<Cell>> grid = new ArrayList<>(size);

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

    public ArrayList<ArrayList<Cell>> getGrid() {
        return grid;
    }

    public void setGrid(ArrayList<ArrayList<Cell>> grid) {
        this.grid = grid;
    }

    public void show() {
        clearConsole();

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
                    System.out.format(space1Alignment, getCellWCheck(grid.get(i).get(j)));
                else
                    System.out.format(space2Alignment, getCellWCheck(grid.get(i).get(j)));

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

    public void showWithAvailablePaths(IUnit unit) {
        clearConsole();

        ArrayList<ArrayList<Pair>> costs = Pathfinder.getCosts();
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
                    System.out.format(space1Alignment, getCellWCheckForAvailable(grid.get(i).get(j), unit, costs.get(i).get(j).getCost()));
                else
                    System.out.format(space2Alignment, getCellWCheckForAvailable(grid.get(i).get(j), unit, costs.get(i).get(j).getCost()));

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

    private String getCellWCheck(Cell cell) {
        if (cell.getUnit() == null && cell.getTown() == null)
            return cell.getTerrain();
        if (cell.getTown() != null && cell.getUnit() != null) {
            if (cell.getColor().equals(ANSI_GREEN))
                return ANSI_GREEN_UNDERLINED + cell.getUnit().getSymbol() + ANSI_RESET;
            else
                return ANSI_RED_UNDERLINED + cell.getUnit().getSymbol() + ANSI_RESET;
        }
        if (cell.getTown() != null)
            return ANSI_BOLD + cell.getColor() + cell.getTown().getSymbol() + ANSI_RESET;
        return cell.getColor() + cell.getUnit().getSymbol() + ANSI_RESET;
    }

    private String getCellWCheckForAvailable(Cell cell, IUnit unit, Double cost) {
        if (cell.getUnit() == null && cell.getTown() == null && cost != null && unit.getEnergy() >= cost)
            return ANSI_BLUE_BRIGHT + cell.getTerrain() + ANSI_RESET;
        if (cell.getUnit() == null && cell.getTown() == null)
            return cell.getTerrain();
        if (cell.getTown() != null && cell.getUnit() != null) {
            if (cell.getColor().equals(ANSI_GREEN))
                return ANSI_GREEN_UNDERLINED + cell.getUnit().getSymbol() + ANSI_RESET;
            else
                return ANSI_RED_UNDERLINED + cell.getUnit().getSymbol() + ANSI_RESET;
        }
        if (cell.getTown() != null)
            return cell.getColor() + cell.getTown().getSymbol() + ANSI_RESET;
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

    public Player getPlayerByCell(int row, int col) {
        return grid.get(row).get(col).getPlayer();
    }

    public void deleteUnit(int row, int col) {
        Cell cell = grid.get(row).get(col);
        cell.setUnit(null);
//        ceil.setColor(ANSI_RESET);
    }

    public Cell getCell(int row, int col) {
        return grid.get(row).get(col);
    }

    public int getSize() {
        return size;
    }

    public String save() {
        StringBuilder ret = new StringBuilder();
        ret.append(grid.size()).append("\n");
        for (ArrayList<Cell> arr : grid) {
            for (Cell cell : arr) {
                ret.append(cell.getTerrain());
            }
            ret.append("\n");
        }
        ret.deleteCharAt(ret.length() - 1);
        return String.valueOf(ret);
    }

    public static void createGrid() {
        Grid tmpGrid = getInstance();
        System.out.println("Enter the grid. " + GridSize.getSize() + " characters");
        String rowTer = "";
        for (int i = 0; i < GridSize.getSize(); i++) {
            while (true) {
                System.out.print("\nEnter " + (i + 1) + " row: ");
                rowTer = MyScanner.getScanner().nextLine();
                if (rowTer.length() == GridSize.getSize() && rowTer.matches("^[*#@!]+$")) {
                    for (int j = 0; j < GridSize.getSize(); j++) {
                        tmpGrid.grid.get(i).get(j).setTerrain(String.valueOf(rowTer.charAt(j)));
                    }
                    break;
                }
                System.out.println(ANSI_RED + "Error" + ANSI_RESET);

            }

        }
    }
}