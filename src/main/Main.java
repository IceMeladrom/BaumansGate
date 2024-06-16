package main;

import main.Entities.Builds.Buildings;
import main.Entities.Builds.IBuilding;
import main.Entities.Builds.Town;
import main.Entities.Builds.Workshop;
import main.Entities.Units.Creator.UnitFactory;
import main.Entities.Units.Units.IUnit;
import main.Entities.Units.Units.UnitType;
import main.Grid.Grid;
import main.Menu.Menu;
import main.Players.Player;
import main.Players.Players.Bot;
import main.Players.Players.RealPlayer;
import main.Save.LoadGame;
import main.Save.LoadedFile;
import main.Utilities.Constants.MyScanner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static main.Utilities.Constants.Colors.*;

public class Main {
    public static void main(String[] args) {
        MyLogger myLogger = new MyLogger();
//        MyLogger.getLogger().info("Hello, World!");

        boolean DEBUG = true, loading = false, loadingAndChange = false;
        Scanner scanner = MyScanner.getScanner();
        while (true) {
            System.out.println("1. Start new game\n2. Load the game\n3. Create the map\n4. Change already existed map");
            System.out.print("Enter the option: ");
            String cmd = scanner.nextLine();
            if (!(cmd.equals("1") || cmd.equals("2") || cmd.equals("3") || cmd.equals("4"))) {
                System.out.println("Invalid option");
                continue;
            }
            switch (cmd) {
                case "2" -> loading = true;
                case "3" -> Grid.createGrid();
                case "4" -> {
                    loading = true;
                    loadingAndChange = true;
                }
            }
            break;
        }
        Player me, bot;

        if (loading && Objects.requireNonNull(new File("Saves/").listFiles()).length > 1) {
            File file = new File("Saves/");
            int counter = 0;
            for (File curFile : Objects.requireNonNull(file.listFiles())) {
                counter++;
                if (counter == Objects.requireNonNull(file.listFiles()).length)
                    break;
                System.out.println(counter + ". " + curFile);
            }
            int numOfSave = 0;
            while (true) {
                System.out.print("Enter the option: ");
                try {
                    numOfSave = Integer.parseInt(scanner.nextLine()) - 1;
                } catch (NumberFormatException e) {
                    System.out.println(ANSI_RED + "Invalid option" + ANSI_RESET);
                    continue;
                }
                if (numOfSave >= 0 && numOfSave < Objects.requireNonNull(file.listFiles()).length)
                    break;
            }
            try {
                LoadedFile options = LoadGame.load(String.valueOf(Arrays.asList(Objects.requireNonNull(file.listFiles())).get(numOfSave)), true, true, true, true);
                if (loadingAndChange)
                    Grid.createGrid();
                me = options.getMe();
                bot = options.getBot();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            if (loading)
                System.out.println(ANSI_RED + "No existed saves" + ANSI_RESET);
            me = new RealPlayer("Robert", 999999.0, ANSI_GREEN, 9999, 9999);
            bot = new Bot("Botinok", 50.0, ANSI_RED, 10, 10);

            if (!DEBUG) {
                // Players place their towns;
                Menu.townPlacementMenu(me);
                Menu.townPlacementMenu(bot);
                // Players buy their units and place them.
                Menu.unitsPlacementMenu(me);
                Menu.unitsPlacementMenu(bot);
            } else {
                debug(me, bot);
            }
        }

        Grid grid = Grid.getInstance();
        Menu.setMe(me);
        Menu.setBot(bot);

        // The game starts.
        while (!me.getUnits().isEmpty() && !bot.getUnits().isEmpty()) {
            // Energize all units
            me.energize();
            bot.energize();

            // Players do their move
            grid.show();
            me.move();
            bot.move();
            for (IBuilding workshop : me.getTown().getBuildings().get(Buildings.Workshop))
                ((Workshop) workshop).buff(me);
            for (IBuilding workshop : bot.getTown().getBuildings().get(Buildings.Workshop))
                ((Workshop) workshop).buff(bot);
        }

        grid.show();
        if (me.getUnits().isEmpty() && bot.getUnits().isEmpty())
            System.out.println("\n\nGAME IS OVER\nDRAW\n\n");
        else if (me.getUnits().isEmpty())
            System.out.println("\n\nGAME IS OVER\nYOU DEFEAT\n\n");
        else
            System.out.println("\n\nGAME IS OVER\nYOU WIN\n\n");
        MyScanner.closeScanner();
    }

    private static void debug(Player me, Player bot) {
        Grid grid = Grid.getInstance();
        Random random = new Random();
        me.setTown(new Town(me, "Whiterun", "▲", random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize())));
        grid.placeTown(me, me.getTown());
        bot.setTown(new Town(bot, "Mordor", "▲", random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize())));
        grid.placeTown(bot, bot.getTown());
        int row, col;
        IUnit unit;
        do {
            row = random.nextInt(0, grid.getSize());
            col = random.nextInt(0, grid.getSize());
        } while (grid.isEntityAtCeil(row, col));
        unit = UnitFactory.createUnit(UnitType.Swordsman, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), me);
        me.buyUnit(unit);
        grid.placeUnit(me, unit);
        do {
            row = random.nextInt(0, grid.getSize());
            col = random.nextInt(0, grid.getSize());
        } while (grid.isEntityAtCeil(row, col));
        unit = UnitFactory.createUnit(UnitType.Swordsman, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), me);
        me.buyUnit(unit);
        grid.placeUnit(me, unit);

        do {
            row = random.nextInt(0, grid.getSize());
            col = random.nextInt(0, grid.getSize());
        } while (grid.isEntityAtCeil(row, col));
        unit = UnitFactory.createUnit(UnitType.Crossbowman, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), me);
        me.buyUnit(unit);
        grid.placeUnit(me, unit);

        do {
            row = random.nextInt(0, grid.getSize());
            col = random.nextInt(0, grid.getSize());
        } while (grid.isEntityAtCeil(row, col));
        unit = UnitFactory.createUnit(UnitType.Knight, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), me);
        me.buyUnit(unit);
        grid.placeUnit(me, unit);

        do {
            row = random.nextInt(0, grid.getSize());
            col = random.nextInt(0, grid.getSize());
        } while (grid.isEntityAtCeil(row, col));
        unit = UnitFactory.createUnit(UnitType.Crossbowman, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), bot);
        bot.buyUnit(unit);
        grid.placeUnit(bot, unit);

        do {
            row = random.nextInt(0, grid.getSize());
            col = random.nextInt(0, grid.getSize());
        } while (grid.isEntityAtCeil(row, col));
        unit = UnitFactory.createUnit(UnitType.Spearman, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), bot);
        bot.buyUnit(unit);
        grid.placeUnit(bot, unit);
    }
}
