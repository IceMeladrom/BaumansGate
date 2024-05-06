package Save;

import Entities.Builds.Town;
import Entities.Units.Units.IUnit;
import Grid.Grid;
import Menu.Menu;
import Players.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SaveGame {
    public static void save() {
        try {
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH-mm-ss-SS");
            String time = dateFormat.format(now);
            Path path = Path.of("Saves/" + time);
            File dir = new File(String.valueOf(path));
            dir.mkdir();

            saveGrid(String.valueOf(path));
            savePlayers(String.valueOf(path));
            saveTowns(String.valueOf(path));
            saveUnits(String.valueOf(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

    private static void saveGrid(String path) throws IOException {
        Grid grid = Grid.getInstance();
        FileWriter gridFile = new FileWriter(path + "/grid.txt");
        gridFile.write(grid.save());
        gridFile.close();
    }

    private static void savePlayers(String path) throws IOException {
        FileWriter meFile = new FileWriter(path + "/me.txt");
        FileWriter botFile = new FileWriter(path + "/bot.txt");

        Player me = Menu.getMe();
        Player bot = Menu.getBot();

        meFile.write(me.save());
        botFile.write(bot.save());

        meFile.close();
        botFile.close();
    }

    private static void saveTowns(String path) throws IOException {
        Player me = Menu.getMe();
        Town myTown = me.getTown();

        Player bot = Menu.getBot();
        Town botTown = bot.getTown();

        FileWriter meFile = new FileWriter(path + "/myTown.txt");
        FileWriter botFile = new FileWriter(path + "/botTown.txt");

        meFile.write(myTown.save());
        botFile.write(botTown.save());

        meFile.close();
        botFile.close();
    }

    private static void saveUnits(String path) throws IOException {
        Player me = Menu.getMe();
        ArrayList<IUnit> myUnits = me.getUnits();

        Player bot = Menu.getBot();
        ArrayList<IUnit> botUnits = bot.getUnits();

        FileWriter meFile = new FileWriter(path + "/myUnits.txt");
        FileWriter botFile = new FileWriter(path + "/botUnits.txt");

        for (IUnit unit : myUnits)
            meFile.write(unit.save());
        for (IUnit unit : botUnits)
            botFile.write(unit.save());

        meFile.close();
        botFile.close();
    }
}
