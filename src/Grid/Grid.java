package Grid;

import Entities.Builds.Town;
import Entities.Units.Units.Unit;
import Players.Player;

import java.util.ArrayList;
import java.util.Random;

import static Utilities.Constants.Colors.*;
import static Utilities.Utils.clearConsole;

public class Grid {
    private int size;
    ArrayList<ArrayList<Ceil>> grid = new ArrayList<>(size);

    public Grid(int size) {
        this.size = size;
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            grid.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++) {
                grid.get(i).add(new Ceil(
                        null,
                        generate(rand)
                ));
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

//        System.out.format("+-----+--------+---+---+---+---+---+---+---+---+---+----+----+----+----+----+----+%n");
//        System.out.format("|     | Column | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 |%n");
//        System.out.format("+-----+--------+---+---+---+---+---+---+---+---+---+----+----+----+----+----+----+%n");

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

    public Unit getUnit(int row, int col) {
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

    private String getCeil(Ceil ceil) {
        if (ceil.getUnit() == null && ceil.getTown() == null)
            return ceil.getTerrain();
        if (ceil.getTown() != null && ceil.getUnit() != null)
            return ANSI_WHITE_BACKGROUND + ANSI_BOLD + ceil.getColor() + ceil.getUnit().getSymbol() + ANSI_RESET;
        if (ceil.getTown() != null)
            return ANSI_BOLD + ceil.getColor() + ceil.getTown().getSymbol() + ANSI_RESET;
        return ceil.getColor() + ceil.getUnit().getSymbol() + ANSI_RESET;
    }

    public void placeUnit(Player player, Unit unit) {
        int row = unit.getRow(), col = unit.getCol();
        Ceil ceil = grid.get(row).get(col);
        ceil.setUnit(unit);
        ceil.setPlayer(player);
//        ceil.setColor(player.getColor());
    }

    public void placeTown(Player player, Town town) {
        int row = town.getRow(), col = town.getCol();
        Ceil ceil = grid.get(row).get(col);
        ceil.setTown(town);
        ceil.setPlayer(player);
//        ceil.setColor(player.getColor());
    }

    public Player getPlayerByCeil(int row, int col) {
        return grid.get(row).get(col).getPlayer();
    }

    public void deleteUnit(int row, int col) {
        Ceil ceil = grid.get(row).get(col);
        ceil.setUnit(null);
//        ceil.setColor(ANSI_RESET);
    }

    public Ceil getCeil(int row, int col) {
        return grid.get(row).get(col);
    }

    public int getSize() {
        return size;
    }
}