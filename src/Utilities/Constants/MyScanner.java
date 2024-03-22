package Utilities.Constants;

import java.util.Scanner;

public class MyScanner {
    private static final Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }

    public static void closeScanner(){
        scanner.close();
    }
}
