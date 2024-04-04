package Grid;

import Utilities.Constants.GridSize;

import java.util.ArrayList;

public class Pathfinder {
    private static int size = GridSize.size;
    private static ArrayList<ArrayList<Integer>> costs = new ArrayList<>() {{
        for (int i = 0; i < size; i++) {
            add(new ArrayList<>() {{
                for (int i = 0; i < size; i++) {
                    add(null);
                }
            }});
        }
    }};

    private static void reset() {
        for (ArrayList<Integer> i : costs)
            for (Integer j : i)
                j = null;
    }

//    public static void availableCells(Cell startCell) {
//        Grid grid = Grid.getInstance();
//        Queue<Cell> queue = new LinkedList<>();
//        Cell previous = null, current = null;
//        Integer cost = 0;
//        queue.add(startCell);
//
//        while(!queue.isEmpty()){
//            current = queue.poll();
//            if (previous == null)
//                costs.get(startCell.getRow()).set(startCell.getCol(), 0);
//            else {
//                if (costs.get(current.getRow()).get(current.getCol()) > costs.get(previous.getRow()).get(previous.getCol())){
//                    costs.get(current.getRow()).set(current.getCol(), costs.get(previous.getRow()).get(previous.getCol()) + current.getTerrain());
//                }
//            }
//            previous = current;
//        }
//    }
}
