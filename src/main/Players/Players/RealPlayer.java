package main.Players.Players;

import main.Entities.Builds.Town;
import main.Entities.Units.Units.IUnit;
import main.Entities.Units.Units.Mage;
import main.Exceptions.*;
import main.Grid.Grid;
import main.Grid.Pathfinder;
import main.Menu.Menu;
import main.Players.Player;
import main.Save.SaveGame;
import main.Utilities.Constants.MyRandom;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import static main.Utilities.Constants.Colors.*;
import static main.Utilities.Utils.isCoordsValid;

public class RealPlayer implements Player {
    private ArrayList<IUnit> units = new ArrayList<>();
    private Town town;
    private Double coins;
    private String name, color;
    private Integer stone, wood;

    private static final ArrayList<String> msg = new ArrayList<>();

    public RealPlayer(String name, Double coins, String color, Integer wood, Integer stone) {
        this.name = name;
        this.coins = coins;
        this.color = color;
        this.wood = wood;
        this.stone = stone;
    }

    public boolean needConsole() {
        return true;
    }

    public ArrayList<IUnit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<IUnit> units) {
        this.units = units;
    }

    public boolean buyUnit(IUnit unit) {
        if (coins < unit.getCost())
            return false;
        addUnit(unit);
        spendCoins(unit.getCost());
        return true;
    }

    public void sellUnit(IUnit unit) {
        addCoins(unit.getCost());
        deleteUnit(unit);
    }

    public void addUnit(IUnit unit) {
        units.add(unit);
    }

    public void deleteUnit(IUnit unit) {
        units.remove(unit);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCoins() {
        return coins;
    }

    public void setCoins(Double coins) {
        this.coins = coins;
        checkCoins();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void addCoins(Double coins) {
        this.coins += coins;
        checkCoins();
    }

    public void spendCoins(Double coins) {
        this.coins -= coins;
        checkCoins();
    }

    public void checkCoins() {
        if (coins < 0)
            coins = 0.0;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {

        this.town = town;
    }

    public boolean canUnitMove(Grid grid, int row, int col) {
        IUnit unit = grid.getUnit(row, col);
        return unit != null && units.contains(unit);
    }

    public void showPlayerInfo() {
        System.out.println("Player: " + getName());
        System.out.println("Coins: " + getCoins());
        if (getUnits().isEmpty())
            return;
        System.out.println();

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat doubleFormat = new DecimalFormat("0.0", otherSymbols);
        for (int i = 0; i < getUnits().size(); i++) {
            IUnit unitTemp = getUnits().get(i);
            System.out.println(i + 1 + ". " + unitTemp.getName() + "(" + getColor() + unitTemp.getSymbol() + ANSI_RESET + ")");
            System.out.println("\tCoords: row: " + (unitTemp.getRow() + 1) + " col: " + (unitTemp.getCol() + 1));
            System.out.println("\tHP: " + doubleFormat.format(unitTemp.getHp()) + "/" + doubleFormat.format(unitTemp.getMaxHp()));
            System.out.println("\tDefence: " + doubleFormat.format(unitTemp.getDefence()) + "/" + doubleFormat.format(unitTemp.getMaxDefence()));
            System.out.println("\tDamage: " + doubleFormat.format(unitTemp.getDamage().getValue()) + " " + unitTemp.getDamage().getColoredDamageType());
            System.out.println("\tAttack Range: " + unitTemp.getAttackRange());
            System.out.println("\tEnergy: " + doubleFormat.format(unitTemp.getEnergy()) + "/" + doubleFormat.format(unitTemp.getMaxEnergy()));
            System.out.println("\tHas attack ability: " + (unitTemp.getDidAttack() ? (ANSI_RED + "NO") : (ANSI_GREEN + "YES")) + ANSI_RESET);
            if (unitTemp.getClass().equals(Mage.class)) {
                if (!unitTemp.getIsAttackPrepared()) {
                    if (unitTemp.getMovesUntilReadyToAttack() != -1)
                        System.out.println("\tSpecial attack will be prepared until: " + ANSI_RED + unitTemp.getMovesUntilReadyToAttack() + " moves" + ANSI_RESET);
                    else
                        System.out.println("\tSpecial attack " + ANSI_RED + "is not preparing" + ANSI_RESET);
                } else
                    System.out.println("\tSpecial attack " + ANSI_GREEN + "prepared" + ANSI_RESET);
                System.out.println("\tSpells: " + ((Mage) unitTemp).getSpells());
            }
        }
        System.out.println();
    }

    @Override
    public void move() {
        Grid grid = Grid.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean endOfMove = false, showInfo = false;
        int row, col;
        String err = "", cmd = "";
        IUnit unit;

        while (!endOfMove) {
            grid.show();

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
                showPlayerInfo();
                showInfo = false;
            }

            System.out.println("1. Choose Unit to move\n2. Prepare an attack\n3. Enter to town's menu\n4. Show info\n5. Buy unit\n6. End move\n7. Save the game");
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
                    if (!isCoordsValid(row, col)) {
                        err = "You enter invalid values or in the wrong format.";
                        continue;
                    }

                    if (!grid.isUnitAtCeil(row, col)) {
                        err = "You tried to choose non-existent unit.";
                        continue;
                    }

                    if (grid.isUnitAtCeil(row, col) && grid.getPlayerByCell(row, col) != this) {
                        err = "You tried to choose not your unit.";
                        continue;
                    }

                    unit = grid.getUnit(row, col);
                    Pathfinder.reset();
                    Pathfinder.availableCells(unit);
                    Pathfinder.show();
                    grid.showWithAvailablePaths(unit);
                    boolean usedSpell = false;
                    if (unit.getClass().equals(Mage.class) && ((Mage) unit).getSpells() > 0) {
                        while (!usedSpell) {
                            System.out.print("You want to use teleportation spell?\n\t1. Yes\n\t2. No\nEnter the option: ");
                            cmd = scanner.nextLine();
                            if (cmd.equals("1")) {
                                ((Mage) unit).reduceSpell();
                                int rndRow, rndCol;
                                do {
                                    rndRow = MyRandom.getRandom().nextInt(0, 15);
                                    rndCol = MyRandom.getRandom().nextInt(0, 15);
                                } while (Grid.getInstance().getCell(rndRow, rndCol).getPlayer() != null);
                                log(ANSI_GREEN + "You teleported to row: " + (rndRow + 1) + " col: " + (rndCol + 1) + ANSI_RESET);
                                grid.getCell(rndRow, rndCol).setUnit(null);
                                unit.setRow(rndRow);
                                unit.setCol(rndCol);
                                grid.getCell(rndRow, rndCol).setUnit(unit);

                                usedSpell = true;
                            } else if (cmd.equals("2")) {
                                break;
                            } else {
                                System.out.println(ANSI_RED + "Invalid option" + ANSI_RESET);
                            }
                        }
                    }
                    if (usedSpell)
                        break;

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
                    if (!isCoordsValid(row, col)) {
                        err = "You enter invalid values or in the wrong format.";
                        continue;
                    }


                    try {
                        unit.walk(row, col);
                    } catch (NotEnoughEnergy | AlliedUnitAtTheCeil | UnitHasAlreadyAttacked | NotYourTown |
                             UnitHasNotPreparedAnAttack | NotEnoughRangeAttack e) {
                        err = e.getMessage();
                        continue;
                    }
                    break;
                case "2":
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
                    if (!isCoordsValid(row, col)) {
                        err = "You enter invalid values or in the wrong format.";
                        continue;
                    }

                    if (!grid.isUnitAtCeil(row, col)) {
                        err = "You tried to choose non-existent unit.";
                        continue;
                    }

                    if (grid.isUnitAtCeil(row, col) && grid.getPlayerByCell(row, col) != this) {
                        err = "You tried to choose not your unit.";
                        continue;
                    }

                    unit = grid.getUnit(row, col);
                    unit.prepareAttack();
                    break;
                case "3":
                    Menu.townManagementMenu(this);
                    break;
                case "4":
                    showInfo = true;
                    break;
                case "5":
                    Menu.unitsPlacementMenu(this);
                    break;
                case "6":
                    endOfMove = true;
                    break;
                case "7":
                    SaveGame.save(true, true, true, true);
                    break;
                default:
                    err = "You tried to enter invalid option!";
                    break;
            }
        }
    }

    public void energize() {
        for (IUnit unit : units) {
            unit.energyRecharge();
            unit.setDidAttack(false);
            unit.preparing();
        }
    }

    public static void log(String msgtmp) {
        msg.add(msgtmp);
    }

    @Override
    public Integer getStone() {
        return stone;
    }

    @Override
    public void setStone(Integer stone) {
        this.stone = stone;
    }

    @Override
    public Integer getWood() {
        return wood;
    }

    @Override
    public void setWood(Integer wood) {
        this.wood = wood;
    }


    public String save() {
        StringBuilder ret = new StringBuilder();
        ret.append(name).append(";;")
                .append(coins).append(";;")
                .append(color).append(";;")
                .append(wood).append(";;")
                .append(stone).append("\n");
        ret.append(town.toString()).append("\n");
        units.forEach(unit -> ret.append(unit).append(";;"));

        return ret.toString();
    }
}
