import Entities.Builds.Town;
import Entities.Units.Creator.TempUnitFactory;
import Entities.Units.Units.IUnit;
import Entities.Units.Units.UnitTypes;
import Grid.Grid;
import Menu.Menu;
import Players.Player;
import Players.Players.Bot;
import Players.Players.RealPlayer;

import java.util.Random;

import static Utilities.Constants.Colors.ANSI_GREEN;
import static Utilities.Constants.Colors.ANSI_RED;

public class Main {
    public static void main(String[] args) {


        boolean DEBUG = true;


        Grid grid = Grid.getInstance();
        RealPlayer me = new RealPlayer("Robert", 999999, ANSI_GREEN);
        Bot bot = new Bot("Botinok", 50, ANSI_RED);

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


        // The game starts.
        while (!me.getUnits().isEmpty() && !bot.getUnits().isEmpty()) {
            // Energize all units
            me.energize();
            bot.energize();

            // Players do their move
            grid.show();
            me.move();
            bot.move();
        }
        System.out.println("\n\nGAME IS OVER\n\n");
    }

    private static void debug(Player me, Player bot) {
        Grid grid = Grid.getInstance();
        Random random = new Random();
        grid.placeTown(me, new Town(me, "Whiterun", "T", random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize())));
        grid.placeTown(bot, new Town(bot, "Mordor", "M", random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize())));
        int row, col;
        IUnit unit;
        do {
            row = random.nextInt(0, grid.getSize());
            col = random.nextInt(0, grid.getSize());
        } while (grid.isEntityAtCeil(row, col));
        unit = TempUnitFactory.createUnit(UnitTypes.Swordsman, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), me);
        me.buyUnit(unit);
        grid.placeUnit(me, unit);

        do {
            row = random.nextInt(0, grid.getSize());
            col = random.nextInt(0, grid.getSize());
        } while (grid.isEntityAtCeil(row, col));
        unit = TempUnitFactory.createUnit(UnitTypes.Swordsman, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), me);
        me.buyUnit(unit);
        grid.placeUnit(me, unit);

        do {
            row = random.nextInt(0, grid.getSize());
            col = random.nextInt(0, grid.getSize());
        } while (grid.isEntityAtCeil(row, col));
        unit = TempUnitFactory.createUnit(UnitTypes.Knight, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), me);
        me.buyUnit(unit);
        grid.placeUnit(me, unit);

        do {
            row = random.nextInt(0, grid.getSize());
            col = random.nextInt(0, grid.getSize());
        } while (grid.isEntityAtCeil(row, col));
        unit = TempUnitFactory.createUnit(UnitTypes.Crossbowman, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), bot);
        bot.buyUnit(unit);
        grid.placeUnit(bot, unit);

        do {
            row = random.nextInt(0, grid.getSize());
            col = random.nextInt(0, grid.getSize());
        } while (grid.isEntityAtCeil(row, col));
        unit = TempUnitFactory.createUnit(UnitTypes.Spearman, random.nextInt(0, grid.getSize()), random.nextInt(0, grid.getSize()), bot);
        bot.buyUnit(unit);
        grid.placeUnit(bot, unit);
    }
}
