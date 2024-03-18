package Utilities;


public class Utils {

    public static void clearConsole() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    public static boolean isCoordsValid(int row, int col, int size) {
        return (0 <= col && col <= size && 0 <= row && row <= size);
    }

}
