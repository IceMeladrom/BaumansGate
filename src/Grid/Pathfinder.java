package Grid;

import java.util.*;

public class Pathfinder {
    public static void availableCells(Cell startCell){
        Queue<Cell> queue = new LinkedList<>();
        Map<Cell, Boolean> visited = new HashMap<>();
        Grid grid = Grid.getInstance();

        visited.put(startCell, true);
        queue.add(startCell);

        while(!queue.isEmpty()){
            Cell currentCell = queue.poll();

        }

    }
}
