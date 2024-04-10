package Grid;

import Entities.Units.Units.IUnit;
import Utilities.Constants.GridSize;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static Utilities.Constants.Colors.*;
import static Utilities.Utils.isCoordsValid;

public class Pathfinder {
    private static final int size = GridSize.size;
    private static final int[] dRow = {-1, 0, 1, 0};
    private static final int[] dCol = {0, 1, 0, -1};

    private static final ArrayList<ArrayList<Double>> costs = new ArrayList<>() {{
        for (int i = 0; i < size; i++) {
            add(new ArrayList<>() {{
                for (int i = 0; i < size; i++) {
                    add(null);
                }
            }});
        }
    }};

    public static void show() {
        String leftAlignment = "| %-3s | %-6s |";
        String space2Alignment = " %-1s |";

        System.out.format("+-----+--------+");
        for (int i = 0; i < size; i++) {
            System.out.format("------+");
        }
        System.out.println();
        System.out.format("|     | Column |");
        for (int i = 0; i < size; i++) {
            System.out.format(space2Alignment, "??.?");
        }
        System.out.println();
        System.out.format("+-----+--------+");
        for (int i = 0; i < size; i++) {
            System.out.format("------+");
        }
        System.out.println();

        System.out.format(leftAlignment, "Row", "");
        for (int i = 0; i < size; i++) {
            System.out.format(space2Alignment, "    ");
        }
        System.out.println();
        System.out.format("+-----+--------+");
        for (int i = 0; i < size; i++) {
            System.out.format("------+");
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.format(leftAlignment, i + 1, "");
            for (int j = 0; j < size; j++) {
                if (costs.get(i).get(j) == null)
                    System.out.format(space2Alignment, ANSI_RED + "??.?" + ANSI_RESET);
                else if (costs.get(i).get(j) == 0)
                    System.out.format(" " + space2Alignment, ANSI_GREEN + new DecimalFormat("0.0").format(costs.get(i).get(j)) + ANSI_RESET);
                else if (costs.get(i).get(j) / 10 >= 1)
                    System.out.format(space2Alignment, new DecimalFormat("0.0").format(costs.get(i).get(j)));
                else
                    System.out.format(" " + space2Alignment, new DecimalFormat("0.0").format(costs.get(i).get(j)));

            }
            System.out.println();
            System.out.format("+-----+--------+");
            for (int k = 0; k < size; k++) {
                System.out.format("------+");
            }
            System.out.println();
        }
    }


    public static void reset() {
        for (ArrayList<Double> i : costs)
            i.replaceAll(ignored -> null);
    }

    public static void availableCells(IUnit unit) {
        Grid grid = Grid.getInstance();
        Queue<Cell> queue = new LinkedList<>();
        Cell startCell = grid.getCell(unit.getRow(), unit.getCol());

        costs.get(unit.getRow()).set(unit.getCol(), 0.0);
        queue.add(startCell);


        while (!queue.isEmpty()) {
            Cell currentCell = queue.poll();
            int curRow = currentCell.getRow();
            int curCol = currentCell.getCol();
            for (int i = 0; i < 4; i++) {
                int nextRow = currentCell.getRow() + dRow[i];
                int nextCol = currentCell.getCol() + dCol[i];
                if (isCoordsValid(nextRow, nextCol) && !grid.isEntityAtCeil(nextRow, nextCol)) {
                    if ((costs.get(nextRow).get(nextCol) == null) ||
                            ((costs.get(curRow).get(curCol) + unit.getTerrains().get(currentCell.getTerrain())) < costs.get(nextRow).get(nextCol))) {
                        Cell nextCell = grid.getCell(nextRow, nextCol);
                        costs.get(nextRow).set(nextCol, costs.get(curRow).get(curCol) + unit.getTerrains().get(nextCell.getTerrain()));
                        queue.add(nextCell);
                    }
                }
            }
        }
    }
}
