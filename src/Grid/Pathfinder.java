package Grid;

import Entities.Units.Units.IUnit;
import Utilities.Constants.GridSize;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static Utilities.Utils.clearConsole;
import static Utilities.Utils.isCoordsValid;

public class Pathfinder {
    private static int size = GridSize.size;
    private static int dRow[] = {-1, 0, 1, 0};
    private static int dCol[] = {0, 1, 0, -1};

    private static ArrayList<ArrayList<Float>> costs = new ArrayList<>() {{
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
                    System.out.format(space1Alignment, costs.get(i).get(j));
                else
                    System.out.format(space2Alignment, costs.get(i).get(j));

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


    public static void reset() {
        for (ArrayList<Float> i : costs)
            for (Float j : i)
                j = null;
    }

    public static void availableCells(IUnit unit) {
        Grid grid = Grid.getInstance();
        Queue<Cell> queue = new LinkedList<>();
        Cell previous = null, current;
        Integer cost = 0;
        Cell startCell = grid.getCell(unit.getRow(), unit.getCol());
        queue.add(startCell);

        while (!queue.isEmpty()) {
            
        }

        while (!queue.isEmpty()) {
            current = queue.poll();
            int row = current.getRow();
            int col = current.getCol();

            for (int i = 0; i < 4; i++) {
                int nextRow = row + dRow[i];
                int nextCol = col + dCol[i];
                if (isCoordsValid(nextRow, nextCol) && !grid.isEntityAtCeil(nextRow, nextCol)) {
                    Float currentCost = costs.get(current.getRow()).get(current.getCol());
                    // TODO запоминание прошлой ячейки
                    Float newCost = costs.get(previous.getRow()).get(previous.getCol()) + unit.getTerrains().get(current.getTerrain());
                    if (currentCost == null || currentCost > newCost)
                        costs.get(current.getRow()).set(current.getCol(), newCost);
                    queue.add(grid.getCell(nextRow, nextCol));
                }
            }
        }
    }

    private static class Pair {
        private Cell previous, current;

        private Pair(Cell previous, Cell current) {
            this.previous = previous;
            this.current = current;
        }
    }
}
