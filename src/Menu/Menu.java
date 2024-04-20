package Menu;

import Entities.Builds.Buildings;
import Entities.Builds.IBuilding;
import Entities.Builds.Town;
import Entities.Units.Creator.UnitFactory;
import Entities.Units.Units.IUnit;
import Entities.Units.Units.UnitType;
import Exceptions.*;
import Grid.Grid;
import Players.Player;
import Utilities.Constants.MyRandom;
import Utilities.Constants.MyScanner;

import java.util.ArrayList;
import java.util.HashMap;
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
                Town town = new Town(player, townsName, "▲", row, col);
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
            Town town = new Town(player, "Mordor", "▲", row, col);
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
            IUnit unit;
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

        IUnit unit = chooseUnitFromShop(player, row, col);
        if (unit == null)
            throw new UnitDoesNotExist();
        if (!player.buyUnit(unit)) {
            throw new NotEnoughCoins(player, unit.getCost());
        }

        grid.placeUnit(player, unit);
        return unit.getName();
    }

    private static IUnit chooseUnitFromShop(Player player, int row, int col) {
        if (player.needConsole()) {
            Scanner scanner = MyScanner.getScanner();
            boolean unitSelected = false;
            IUnit unit = null;
            String cmd = "", err = "";
            while (!unitSelected) {
                if (!err.isEmpty()) {
                    System.out.println(ANSI_RED + err + ANSI_RESET);
                    err = "";
                }

                System.out.println("\n" + ANSI_PURPLE + "You have " + player.getCoins() + " coins" + ANSI_RESET);
                System.out.println("Choose a unit");

                UnitType.showUnitsShop();

                System.out.println("\nEnter the unit's name to buy it.\nOr type " + ANSI_RED + "Exit" + ANSI_RESET + ".");
                cmd = scanner.nextLine();
                if (cmd.equals("Exit"))
                    return null;

                try {
                    unit = UnitFactory.createUnit(UnitType.valueOf(cmd), row, col, player);
                    unitSelected = true;
                } catch (IllegalArgumentException e) {
                    err = "You tried to choose non existed unit!";
                }
            }

            return unit;
        } else {
            UnitType unitName;
            IUnit unit;
            while (true) {
                unitName = UnitType.randomUnit();
                if (unitName.getCost() <= player.getCoins()) {
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

        IUnit unit = grid.getUnit(row, col);
        if (!grid.isUnitAtCeil(row, col))
            throw new NoUnitAtTheCeil();

        player.sellUnit(unit);
        grid.deleteUnit(row, col);
        return unit.getName();
    }


    public static void townManagementMenu(Player player) throws InvalidOption, NotEnoughResources, CantBuildOrUpgradeHouse {
        if (player.needConsole()) {
            boolean leave = false;
            while (!leave) {
//                clearConsole();
                Scanner scanner = MyScanner.getScanner();

                Town town = player.getTown();
                HashMap<Buildings, ArrayList<IBuilding>> buildings = town.getBuildings();

                System.out.format("Welcome to the %s town!%n", town.getName());
                System.out.format("Choose the option:%n1. Show player info%n2. Build/Upgrade building%n3. Leave town's menu%n");
                System.out.format("Enter the option: ");
                String option = scanner.nextLine();
                switch (option) {
                    case "1" -> {
                        System.out.format("%nPlayer's name: %s%n", player.getName());
                        System.out.format("Coins: %f%nWood: %d%nStone: %d%n", player.getCoins(), player.getWood(), player.getStone());
                        town.showBuildings();
                    }
                    case "2" -> {
                        Buildings.showBuildingsShop(player);
                        System.out.format("Enter the option: ");
                        String houseName = scanner.nextLine();
                        try {
                            town.buildHouse(Buildings.valueOf(houseName));
                        } catch (IllegalArgumentException e) {
                            throw new InvalidOption();
                        }
                    }
                    case "3" -> {
                        leave = true;
                    }
                    default -> throw new InvalidOption();
                }
            }
        } else {

        }
    }
}
