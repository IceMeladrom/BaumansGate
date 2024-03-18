import Entities.Builds.Town;
import Entities.Units.Units.Unit;
import Entities.Units.Units.UnitTypes;
import Grid.Grid;
import Players.Players.Bot;
import Players.Players.RealPlayer;
import Entities.Units.Creator.UnitFactory;

import java.util.Random;

import static Utilities.Constants.Colors.*;

public class Main {
    private static Grid grid;

    public static void main(String[] args) {
        boolean DEBUG = true;

        Grid grid = new Grid(10);
        RealPlayer me = new RealPlayer("Robert", 999999, ANSI_GREEN);
        Bot bot = new Bot("Botinok", 50, ANSI_RED);
        UnitFactory unitCreator = new UnitFactory();

        if (!DEBUG) {
            // Players place their towns;
            me.placeTown(grid);
            bot.placeTown(grid);
            // Players buy their units and place them.
            me.placeUnits(grid, unitCreator);
            bot.placeUnits(grid, unitCreator);
        } else {
            Random random = new Random();
            grid.placeTown(me, new Town(me, "Whiterun", "T", random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize())));
            grid.placeTown(bot, new Town(bot, "Mordor", "M", random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize())));
            int row, col;
            Unit unit;
            do {
                row = random.nextInt(0, grid.getSize());
                col = random.nextInt(0, grid.getSize());
            } while (grid.isEntityAtCeil(row, col));
            unit = unitCreator.createUnit(UnitTypes.Swordsman, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), me);
            me.buyUnit(unit);
            grid.placeUnit(me, unit);

            do {
                row = random.nextInt(0, grid.getSize());
                col = random.nextInt(0, grid.getSize());
            } while (grid.isEntityAtCeil(row, col));
            unit = unitCreator.createUnit(UnitTypes.Swordsman, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), me);
            me.buyUnit(unit);
            grid.placeUnit(me, unit);

            do {
                row = random.nextInt(0, grid.getSize());
                col = random.nextInt(0, grid.getSize());
            } while (grid.isEntityAtCeil(row, col));
            unit = unitCreator.createUnit(UnitTypes.Knight, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), me);
            me.buyUnit(unit);
            grid.placeUnit(me, unit);

            do {
                row = random.nextInt(0, grid.getSize());
                col = random.nextInt(0, grid.getSize());
            } while (grid.isEntityAtCeil(row, col));
            unit = unitCreator.createUnit(UnitTypes.Crossbowman, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), bot);
            bot.buyUnit(unit);
            grid.placeUnit(bot, unit);

            do {
                row = random.nextInt(0, grid.getSize());
                col = random.nextInt(0, grid.getSize());
            } while (grid.isEntityAtCeil(row, col));
            unit = unitCreator.createUnit(UnitTypes.Spearman, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), bot);
            bot.buyUnit(unit);
            grid.placeUnit(bot, unit);
        }
        // The game starts.
        while (!me.getUnits().isEmpty() && !bot.getUnits().isEmpty()) {
            // Energize all units
            me.energize();
            bot.energize();

            // Players do their move
            grid.show();
            me.move(grid);
            bot.move(grid);
        }
        System.out.println("\n\nGAME IS OVER\n\n");
    }
}
