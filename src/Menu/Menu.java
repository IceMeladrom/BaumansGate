package Menu;

import Entities.Builds.Town;
import Entities.Units.Creator.UnitFactory;
import Entities.Units.Units.Unit;
import Entities.Units.Units.UnitTypes;
import Exceptions.AnotherEntityAtTheCeil;
import Exceptions.NoUnitAtTheCeil;
import Exceptions.NotEnoughCoins;
import Exceptions.UnitDoesNotExist;
import Grid.Grid;
import Players.Player;
import Utilities.Constants.MyRandom;
import Utilities.Constants.MyScanner;

import java.util.Random;
import java.util.Scanner;

import static Utilities.Constants.Colors.*;
import static Utilities.Utils.isCoordsValid;

public class Menu {
    public static void townPlacementMenu(Player player) {
        if (player.needConsole()) {
            Scanner scanner = MyScanner.getScanner();
            Grid grid = Grid.getInstance();
            String err = "";
            int row, col;

            while (true) {
                grid.show();

                if (!err.isEmpty()) {
                    System.out.println(ANSI_RED + err + " Try again." + ANSI_RESET);
                    err = "";
                }

                System.out.println("Enter the coordinates where you want to place your Town.");
                try {
                    System.out.print("Row: ");
                    row = Integer.parseInt(scanner.nextLine()) - 1;
                    System.out.print("Column: ");
                    col = Integer.parseInt(scanner.nextLine()) - 1;
                } catch (NumberFormatException e) {
                    err = "You enter values in wrong format.";
                    continue;
                }

                if (!isCoordsValid(row, col)) {
                    err = "You enter invalid coordinates.";
                    continue;
                }
                System.out.println("Enter the name of town.");
                System.out.print("Town's name: ");
                String townsName = scanner.nextLine();
                Town town = new Town(player, townsName, "T", row, col);
                player.setTown(town);
                grid.placeTown(player, town);
                return;
            }
        } else {
            Random random = MyRandom.getRandom();
            Grid grid = Grid.getInstance();
            int row = 0, col = 0;
            boolean validCoords = false;

            while (!validCoords) {
                row = random.nextInt(0, grid.getSize());
                col = random.nextInt(0, grid.getSize());
                if (grid.isEntityAtCeil(row, col) || grid.isTownAtCeil(row, col))
                    continue;
                validCoords = true;
            }
            Town town = new Town(player, "Mordor", "M", row, col);
            player.setTown(town);
            grid.placeTown(player, town);
        }
    }

    public static void unitsPlacementMenu(Player player) {
        if (player.needConsole()) {
            Scanner scanner = MyScanner.getScanner();
            Grid grid = Grid.getInstance();
            boolean myUnitsArePlaced = false, showInfo = false;
            String boughtUnitName = "", soldUnitName = "";
            String cmd = "", err = "";

            while (!myUnitsArePlaced) {
                grid.show();

                if (!err.isEmpty()) {
                    System.out.println(ANSI_RED + err + " Try again." + ANSI_RESET);
                    err = "";
                }

                if (!boughtUnitName.isEmpty()) {
                    System.out.println(ANSI_GREEN + "You successfully bought " + boughtUnitName + "." + ANSI_RESET);
                    boughtUnitName = "";
                }

                if (!soldUnitName.isEmpty()) {
                    System.out.println(ANSI_GREEN + "You successfully sold " + soldUnitName + "." + ANSI_RESET);
                    soldUnitName = "";
                }

                if (showInfo) {
                    player.showPlayerInfo();
                    showInfo = false;
                }

                System.out.println("Enter number of option you want to choose.\n1. Buy a new Unit.\n2. Sell the unit.\n3. Show my info.\n4. Start the game.\n");
                System.out.print("I choose option: ");

                cmd = scanner.nextLine();

                switch (cmd) {
                    case "1":
                        try {
                            boughtUnitName = buyUnitPlacementMenu(player);
                        } catch (AnotherEntityAtTheCeil | UnitDoesNotExist | NotEnoughCoins | NumberFormatException |
                                 IndexOutOfBoundsException e) {
                            err = e.getMessage();
                            continue;
                        }
                        break;
                    case "2":
                        try {
                            soldUnitName = sellUnitPlacementMenu(player);
                        } catch (NoUnitAtTheCeil | NumberFormatException | IndexOutOfBoundsException e) {
                            err = e.getMessage();
                            continue;
                        }
                        break;
                    case "3":
                        showInfo = true;
                        break;
                    case "4":
                        myUnitsArePlaced = true;
                        break;
                    default:
                        err = "You tried to choose invalid option.";
                }
            }
        } else {
            Random random = MyRandom.getRandom();
            Grid grid = Grid.getInstance();
            Unit unit;
            int row = 0, col = 0;
            while (true) {
                do {
                    row = random.nextInt(0, grid.getSize());
                    col = random.nextInt(0, grid.getSize());
                } while (grid.isEntityAtCeil(row, col));
                if (player.getCoins() < 10)
                    break;
                unit = chooseUnitFromShop(player, row, col);
                player.buyUnit(unit);
                grid.placeUnit(player, unit);
            }
        }
    }


    private static String buyUnitPlacementMenu(Player player) throws AnotherEntityAtTheCeil, UnitDoesNotExist, NotEnoughCoins, NumberFormatException, IndexOutOfBoundsException {
        Scanner scanner = MyScanner.getScanner();
        Grid grid = Grid.getInstance();
        int row, col;
        System.out.println("Enter the coordinates where you want to place your Unit.");
        try {
            System.out.print("Row: ");
            row = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.print("Column: ");
            col = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("You enter values in wrong format!");
        }

        if (!isCoordsValid(row, col))
            throw new IndexOutOfBoundsException("You enter invalid coordinates!");
        if (grid.isEntityAtCeil(row, col))
            throw new AnotherEntityAtTheCeil();

        Unit unit = chooseUnitFromShop(player, row, col);
        if (unit == null)
            throw new UnitDoesNotExist();
        if (!player.buyUnit(unit)) {
            throw new NotEnoughCoins(player, unit.getCost());
        }

        grid.placeUnit(player, unit);
        return unit.getName();
    }

    private static Unit chooseUnitFromShop(Player player, int row, int col) {
        if (player.needConsole()) {
            Scanner scanner = MyScanner.getScanner();
            boolean unitSelected = false;
            Unit unit = null;
            String cmd = "", err = "";
            while (!unitSelected) {
                if (!err.isEmpty()) {
                    System.out.println(ANSI_RED + err + ANSI_RESET);
                    err = "";
                }

                System.out.println("\n" + ANSI_PURPLE + "You have " + player.getCoins() + " coins" + ANSI_RESET);
                System.out.println("Choose a unit");

                UnitTypes.showUnitsShop();

                System.out.println("\nEnter the unit's name to buy it.\nOr type " + ANSI_RED + "Exit" + ANSI_RESET + ".");
                cmd = scanner.nextLine();
                if (cmd.equals("Exit"))
                    return null;

                try {
                    unit = UnitFactory.createUnit(UnitTypes.valueOf(cmd), row, col, player);
                    unitSelected = true;
                } catch (IllegalArgumentException e) {
                    err = "You tried to choose non existed unit!";
                }
            }

            return unit;
        } else {
            UnitTypes unitName;
            Unit unit;
            while (true) {
                unitName = UnitTypes.randomUnit();
                if (unitName.cost <= player.getCoins()) {
                    unit = UnitFactory.createUnit(unitName, row, col, player);
                    break;
                }
            }
            return unit;
        }
    }


    private static String sellUnitPlacementMenu(Player player) throws NoUnitAtTheCeil, NumberFormatException, IndexOutOfBoundsException {
        Scanner scanner = MyScanner.getScanner();
        Grid grid = Grid.getInstance();
        int row, col;
        System.out.println("Enter the coordinates where is your Unit.");
        try {
            System.out.print("Row: ");
            row = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.print("Column: ");
            col = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("You enter values in wrong format!");
        }
        if (!isCoordsValid(row, col))
            throw new IndexOutOfBoundsException("You enter invalid values or in the wrong format!");

        Unit unit = grid.getUnit(row, col);
        if (!grid.isUnitAtCeil(row, col))
            throw new NoUnitAtTheCeil();

        player.sellUnit(unit);
        grid.deleteUnit(row, col);
        return unit.getName();
    }

}
