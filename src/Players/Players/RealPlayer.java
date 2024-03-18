package Players.Players;

import Entities.Builds.Town;
import Entities.Units.Creator.UnitFactory;
import Entities.Units.Units.Unit;
import Entities.Units.Units.UnitTypes;
import Exceptions.AlliedUnitAtTheCeil;
import Exceptions.NotEnoughEnergy;
import Exceptions.NotYourTown;
import Exceptions.UnitHasAlreadyAttacked;
import Grid.Grid;
import Players.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import static Utilities.Constants.Colors.*;
import static Utilities.Utils.isCoordsValid;

public class RealPlayer implements Player {
    private ArrayList<Unit> units;
    private Town town;
    private int coins;
    private String name;
    private String color;

    public RealPlayer(String name, int coins, String color) {
        this.name = name;
        this.coins = coins;
        this.color = color;
        units = new ArrayList<>();
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public boolean buyUnit(@NotNull Unit unit) {
        if (coins < unit.getCost())
            return false;
        addUnit(unit);
        spendCoins(unit.getCost());
        return true;
    }

    public void sellUnit(@NotNull Unit unit) {
        addCoins(unit.getCost());
        deleteUnit(unit);
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void deleteUnit(Unit unit) {
        units.remove(unit);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
        checkCoins();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void addCoins(int coins) {
        this.coins += coins;
        checkCoins();
    }

    public void spendCoins(int coins) {
        this.coins -= coins;
        checkCoins();
    }

    public void checkCoins() {
        if (coins < 0)
            coins = 0;
    }

    public void placeTown(Grid grid) {
        Scanner scanner = new Scanner(System.in);
        boolean myTownIsPlaced = false;
        String err = "", cmd = "";
        int row, col;

        while (!myTownIsPlaced) {
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
            } catch (InputMismatchException e) {
                err = "You enter invalid value.";
                continue;
            } catch (NumberFormatException e) {
                err = "You enter values in wrong format.";
                continue;
            }

            if (!isCoordsValid(row, col, grid.getSize()-1)) {
                err = "You enter invalid coordinates.";
                continue;
            }
            System.out.println("Enter the name of town.");
            System.out.print("Town's name: ");
            String townsName = scanner.nextLine();
            town = new Town(this, townsName, "T", row, col);
            grid.placeTown(this, town);
            myTownIsPlaced = true;
        }

    }


    public void placeUnits(Grid grid, UnitFactory unitCreator) {
        Scanner scanner = new Scanner(System.in);
        boolean myUnitsArePlaced = false, showInfo = false;
        String isBought = "";
        Unit unit;
        int row, col;
        String cmd = "", err = "";

        while (!myUnitsArePlaced) {
            grid.show();

            if (!err.isEmpty()) {
                System.out.println(ANSI_RED + err + " Try again." + ANSI_RESET);
                err = "";
            }

            if (!isBought.isEmpty()) {
                System.out.println(ANSI_GREEN + "You successfully bought " + isBought + "." + ANSI_RESET);
                isBought = "";
            }

            if (showInfo) {
                showPlayerInfo();
                showInfo = false;
            }

            System.out.println("Enter number of option you want to choose.\n1. Place a new Unit.\n2. Sell the unit.\n3. Show my info.\n4. Start the game.\n");
            System.out.print("I choose option: ");

            cmd = scanner.nextLine();

            switch (cmd) {
                case "1":
                    System.out.println("Enter the coordinates where you want to place your Unit.");
                    try {
                        System.out.print("Row: ");
                        row = Integer.parseInt(scanner.nextLine()) - 1;
                        System.out.print("Column: ");
                        col = Integer.parseInt(scanner.nextLine()) - 1;
                    } catch (InputMismatchException e) {
                        err = "You enter invalid value.";
                        continue;
                    } catch (NumberFormatException e) {
                        err = "You enter values in wrong format.";
                        continue;
                    }

                    if (!isCoordsValid(row, col, grid.getSize()-1)) {
                        err = "You enter invalid coordinates.";
                        continue;
                    }

                    if (grid.isEntityAtCeil(row, col)) {
                        err = "You tried to place a new unit on an already placed unit.";
                        continue;
                    }

                    unit = chooseUnit(grid, unitCreator, row, col);
                    if (unit == null) {
                        err = "You tried to choose invalid unit.";
                        continue;
                    }
                    if (!buyUnit(unit)) {
                        err = "Not enough coins to buy " + unit.getName() + ".";
                        continue;
                    }
                    grid.placeUnit(this, unit);
                    isBought = unit.getName();
                    break;

                case "2":
                    System.out.println("Enter the coordinates where your Unit.");
                    try {
                        System.out.print("Row: ");
                        row = Integer.parseInt(scanner.nextLine()) - 1;
                        System.out.print("Column: ");
                        col = Integer.parseInt(scanner.nextLine()) - 1;
                    } catch (InputMismatchException e) {
                        err = "You enter invalid value.";
                        continue;
                    } catch (NumberFormatException e) {
                        err = "You enter values in wrong format.";
                        continue;
                    }
                    if (!isCoordsValid(row, col, grid.getSize()-1)) {
                        err = "You enter invalid values or in the wrong format.";
                        continue;
                    }

                    unit = grid.getUnit(row, col);
                    if (!grid.isUnitAtCeil(row, col)) {
                        err = "There is no unit on this ceil.\n";
                        continue;
                    }

                    sellUnit(unit);
                    grid.deleteUnit(row, col);
                    System.out.println(ANSI_GREEN + "You successfully sold " + unit.getName() + "." + ANSI_RESET);
                    break;

                case "3":
                    showInfo = true;
                    break;
                case "4":
                    myUnitsArePlaced = true;
                    break;
                default:
                    err = "You tried to choose invalid option.";
                    continue;
            }
        }
    }


    public Unit chooseUnit(Grid grid, UnitFactory unitCreator, int row, int col) {
        Scanner scanner = new Scanner(System.in);
        boolean unitSelected = false, err = false;
        Unit unit = null;
        String cmd = "";
        while (!unitSelected) {
            if (err)
                System.out.println(ANSI_RED + "You enter invalid value! Try again." + ANSI_RESET);
            err = false;

            String leftAlignment = "| %-14s | %-6s | %-6s | %-12s | %-7s | %-6s | %-4s |%n";
            System.out.println("\n" + ANSI_PURPLE + "You have " + coins + " coins" + ANSI_RESET);
            System.out.println("Choose a unit");
            System.out.format("+----------------+--------+--------+--------------+---------+--------+------+%n");
            System.out.format("|      Name      | Health | Attack | Attack Range | Defence | Energy | Cost |%n");
            System.out.format("+----------------+--------+--------+--------------+---------+--------+------+%n");
            for (UnitTypes unitType : UnitTypes.values()) {
                System.out.format(leftAlignment, unitType.name, unitType.hp, unitType.damage,
                        unitType.attackRange, unitType.defence, unitType.energy, unitType.cost);
            }
            System.out.format("+----------------+--------+--------+--------------+---------+--------+------+%n");

            System.out.println("\nEnter the unit's name to buy it.\nOr type " + ANSI_RED + "Exit" + ANSI_RESET + ".");
            cmd = scanner.nextLine();

            if (cmd.equals("Exit")) {
                return null;
            }

            try {
                unit = unitCreator.createUnit(UnitTypes.valueOf(cmd), row, col, this);
                unitSelected = true;
            } catch (IllegalArgumentException e) {
                err = true;
                continue;
            }
        }

        return unit;
    }

    public boolean canUnitMove(@NotNull Grid grid, int row, int col) {
        Unit unit = grid.getUnit(row, col);
        return unit != null && units.contains(unit);
    }

    public void showPlayerInfo() {
        System.out.println("Player: " + getName());
        System.out.println("Coins: " + getCoins());
        if (getUnits().isEmpty())
            return;
        System.out.println();
        for (int i = 0; i < getUnits().size(); i++) {
            Unit unitTemp = getUnits().get(i);
            System.out.println(i + 1 + ". " + unitTemp.getName() + "(" + getColor() + unitTemp.getSymbol() + ANSI_RESET + ")");
            System.out.println("\tCoords: row: " + (unitTemp.getRow() + 1) + " col: " + (unitTemp.getCol() + 1));
            System.out.println("\tHP: " + unitTemp.getHp() + "/" + unitTemp.getMaxHp());
            System.out.println("\tDefence: " + unitTemp.getDefence() + "/" + unitTemp.getMaxDefence());
            System.out.println("\tDamage: " + unitTemp.getDamage());
            System.out.println("\tAttack Range: " + unitTemp.getAttackRange());
            System.out.println("\tEnergy: " + unitTemp.getEnergy() + "/" + unitTemp.getMaxEnergy());
            System.out.println("\tCan Attack: " + (unitTemp.getDidAttack() ? (ANSI_RED + "NO") : (ANSI_GREEN + "YES")) + ANSI_RESET + "\n");
        }
        System.out.println();
    }

    public void move(Grid grid) {
        Scanner scanner = new Scanner(System.in);
        boolean endOfMove = false, showInfo = false;
        int row, col;
        String err = "", cmd = "";
        Unit unit;

        while (!endOfMove) {
            grid.show();

            if (!err.isEmpty()) {
                System.out.println(ANSI_RED + err + " Try again!" + ANSI_RESET);
                err = "";
            }

            if (showInfo) {
                showPlayerInfo();
                showInfo = false;
            }

            System.out.println("1. Choose Unit to move.\n2. Show info.\n3. End move");
            System.out.print("I choose option: ");
            cmd = scanner.nextLine();

            switch (cmd) {
                case "1":
                    System.out.println("Enter the coordinates of your unit.");
                    try {
                        System.out.print("Row: ");
                        row = Integer.parseInt(scanner.nextLine()) - 1;
                        System.out.print("Column: ");
                        col = Integer.parseInt(scanner.nextLine()) - 1;
                    } catch (InputMismatchException e) {
                        err = "You enter invalid value.";
                        continue;
                    } catch (NumberFormatException e) {
                        err = "You enter values in wrong format.";
                        continue;
                    }
                    if (!isCoordsValid(row, col, grid.getSize()-1)) {
                        err = "You enter invalid values or in the wrong format.";
                        continue;
                    }

                    if (!grid.isUnitAtCeil(row, col)) {
                        err = "You tried to choose non-existent unit.";
                        continue;
                    }

                    if (grid.isUnitAtCeil(row, col) && grid.getPlayerByCeil(row, col) != this) {
                        err = "You tried to choose not your unit.";
                        continue;
                    }

                    unit = grid.getUnit(row, col);

                    System.out.println("Enter the coordinates where you want to go");
                    try {
                        System.out.print("Row: ");
                        row = Integer.parseInt(scanner.nextLine()) - 1;
                        System.out.print("Column: ");
                        col = Integer.parseInt(scanner.nextLine()) - 1;
                    } catch (InputMismatchException e) {
                        err = "You enter invalid value.";
                        continue;
                    } catch (NumberFormatException e) {
                        err = "You enter values in wrong format.";
                        continue;
                    }
                    if (!isCoordsValid(row, col, grid.getSize()-1)) {
                        err = "You enter invalid values or in the wrong format.";
                        continue;
                    }

//                    if (grid.getCeil(row, col).getTown() != null)

                    try {
                        unit.walk(grid, row, col);
                    } catch (NotEnoughEnergy | AlliedUnitAtTheCeil | UnitHasAlreadyAttacked | NotYourTown e) {
                        err = e.getMessage();
                        continue;
                    }
                    break;
                case "2":
                    showInfo = true;
                    break;
                case "3":
                    endOfMove = true;
                    break;
                default:
                    err = "You tried to enter invalid option!";
                    break;
            }
        }
    }

    public void energize() {
        for (Unit unit : units) {
            unit.energyRecharge();
            unit.setDidAttack(false);
        }
    }

}
