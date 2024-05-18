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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import static Utilities.Constants.Colors.*;
import static Utilities.Utils.clearConsole;
import static Utilities.Utils.isCoordsValid;

public class Menu {
    private static Player me, bot;
    private static final ArrayList<String> msg = new ArrayList<>();

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
                if (UnitType.getEnums().contains(cmd)) {
                    unit = UnitFactory.createUnit(UnitType.valueOf(cmd), row, col, player);
                    unitSelected = true;
                } else if (UnitType.getNewUnitsTypes().containsKey(cmd)) {
                    unit = UnitFactory.createUnit(UnitType.getNewUnitsTypes().get(cmd), row, col, player);
                    unitSelected = true;
                } else {
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


    public static void townManagementMenu(Player player) {
        if (player.needConsole()) {
            boolean leave = false, showInfo = false;
            String err = "";
            Scanner scanner = MyScanner.getScanner();
            Town town = player.getTown();
            HashMap<Buildings, ArrayList<IBuilding>> buildings = town.getBuildings();
            while (!leave) {
                clearConsole();
                if (!err.isEmpty()) {
                    System.out.println(ANSI_RED + err + " Try again!" + ANSI_RESET);
                    err = "";
                }
                if (!msg.isEmpty()) {
                    for (String s : msg)
                        System.out.println(s);
                    msg.clear();
                }
                if (showInfo) {
                    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
                    otherSymbols.setDecimalSeparator('.');
                    otherSymbols.setGroupingSeparator('.');
                    DecimalFormat doubleFormat = new DecimalFormat("0.0", otherSymbols);
                    player.showPlayerInfo();
                    System.out.format("%nPlayer's name: %s%n", player.getName());
                    System.out.format("Coins: %s%nWood: %d%nStone: %d%n", doubleFormat.format(player.getCoins()), player.getWood(), player.getStone());
                    town.showBuildings();
                    showInfo = false;
                }
                System.out.format("Welcome to the %s town!%n", town.getName());
                System.out.format("Choose the option:\n\t1. Show player info\n\t2. Build/Upgrade building\n\t3. Enter to the Market\n\t4. Enter to the Academy\n\t5. Enter to the Tower\n\t6. Leave town's menu\n");
                System.out.format("Enter the option: ");
                String option = scanner.nextLine();
                switch (option) {
                    case "1" -> {
                        showInfo = true;
                    }
                    case "2" -> {
                        Buildings.showBuildingsShop(player);
                        System.out.format("Enter the option or type %sExit%s: ", ANSI_RED, ANSI_RESET);
                        String houseName = scanner.nextLine();
                        if (houseName.equals("Exit"))
                            continue;
                        try {
                            town.buildHouse(Buildings.valueOf(houseName));
                        } catch (IllegalArgumentException e) {
                            err = "Invalid option";
                            break;
                        } catch (CantBuildOrUpgradeHouse | NotEnoughResources | MageAlreadyHasMinPreparationTime |
                                 NotEnoughCoins e) {
                            err = e.getMessage();
                            break;
                        }
                    }
                    case "3" -> {
                        if (town.getBuildings().get(Buildings.Market).isEmpty()) {
                            err = "Market hasn't built yet!";
                            break;
                        }
                        try {
                            town.getBuildings().get(Buildings.Market).getFirst().buff(player);
                        } catch (MageAlreadyHasMinPreparationTime | NotEnoughCoins e) {
                            err = e.getMessage();
                            break;
                        }
                    }
                    case "4" -> {
                        if (town.getBuildings().get(Buildings.Academy).isEmpty()) {
                            err = "Market hasn't built yet!";
                            break;
                        }
                        try {
                            town.getBuildings().get(Buildings.Academy).getFirst().buff(player);
                        } catch (MageAlreadyHasMinPreparationTime | NotEnoughCoins e) {
                            err = e.getMessage();
                            break;
                        }
                    }
                    case "5" -> {
                        if (town.getBuildings().get(Buildings.Tower).isEmpty()) {
                            err = "Tower hasn't built yet!";
                            break;
                        }
                        try {
                            town.getBuildings().get(Buildings.Tower).getFirst().buff(player);
                        } catch (MageAlreadyHasMinPreparationTime | NotEnoughCoins e) {
                            err = e.getMessage();
                            break;
                        }
                    }
                    case "6" -> {
                        leave = true;
                    }
                    default -> err = new InvalidOption().getMessage();
                }
            }
        } else {

        }
    }

    public static Player getMe() {
        return me;
    }

    public static void setMe(Player me) {
        Menu.me = me;
    }

    public static Player getBot() {
        return bot;
    }

    public static void setBot(Player bot) {
        Menu.bot = bot;
    }

    public static void log(String msgtmp) {
        msg.add(msgtmp);
    }
}
