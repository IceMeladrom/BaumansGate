import main.Entities.Damage.DamageType;
import main.Entities.Units.Creator.UnitFactory;
import main.Entities.Units.Units.IUnit;
import main.Entities.Units.Units.UnitType;
import main.Exceptions.*;
import main.Grid.Grid;
import main.Grid.Pathfinder;
import main.Players.Player;
import main.Players.Players.Bot;
import main.Players.Players.RealPlayer;
import main.Utilities.Constants.GridSize;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;

import static main.Utilities.Constants.Colors.ANSI_GREEN;
import static main.Utilities.Constants.Colors.ANSI_RED;
import static org.junit.jupiter.api.Assertions.*;



public class MainTest {
    @Test
    public void testMyVictory() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Player me = new RealPlayer("Robert", 999999.0, ANSI_GREEN, 9999, 9999);
        Player bot = new Bot("Botinok", 50.0, ANSI_RED, 10, 10);

        me.addUnit(UnitFactory.createUnit(UnitType.Crossbowman, 1, 1, me));
        bot.addUnit(UnitFactory.createUnit(UnitType.Crossbowman, 2, 2, bot));

        bot.deleteUnit(bot.getUnits().getFirst());

        if (me.getUnits().isEmpty() && bot.getUnits().isEmpty())
            System.out.println("\n\nGAME IS OVER\nDRAW\n\n");
        else if (me.getUnits().isEmpty())
            System.out.println("\n\nGAME IS OVER\nYOU DEFEAT\n\n");
        else
            System.out.println("\n\nGAME IS OVER\nYOU WIN\n\n");

        String consoleOutput = outputStream.toString().trim();
        assertEquals("GAME IS OVER\nYOU WIN", consoleOutput);
        System.setOut(System.out);
    }

    @Test
    public void testBotVictory() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Player me = new RealPlayer("Robert", 999999.0, ANSI_GREEN, 9999, 9999);
        Player bot = new Bot("Botinok", 50.0, ANSI_RED, 10, 10);

        me.addUnit(UnitFactory.createUnit(UnitType.Crossbowman, 1, 1, me));
        bot.addUnit(UnitFactory.createUnit(UnitType.Crossbowman, 2, 2, bot));

        me.deleteUnit(me.getUnits().getFirst());

        if (me.getUnits().isEmpty() && bot.getUnits().isEmpty())
            System.out.println("\n\nGAME IS OVER\nDRAW\n\n");
        else if (me.getUnits().isEmpty())
            System.out.println("\n\nGAME IS OVER\nYOU DEFEAT\n\n");
        else
            System.out.println("\n\nGAME IS OVER\nYOU WIN\n\n");

        String consoleOutput = outputStream.toString().trim();
        assertEquals("GAME IS OVER\nYOU DEFEAT", consoleOutput);
        System.setOut(System.out);
    }

    @Test
    public void testPenalties() throws NotYourTown, UnitHasAlreadyAttacked, UnitHasNotPreparedAnAttack, NotEnoughEnergy, AlliedUnitAtTheCeil, NotEnoughRangeAttack {
        GridSize.setSize(5);
        Grid grid = Grid.getInstance();
        for (int j = 0; j < grid.getSize(); j++) {
            grid.getCell(0, j).setTerrain("*");
            grid.getCell(1, j).setTerrain("*");
            grid.getCell(2, j).setTerrain("#");
            grid.getCell(3, j).setTerrain("@");
            grid.getCell(4, j).setTerrain("!");
        }

        Player me = new RealPlayer("Robert", 999999.0, ANSI_GREEN, 9999, 9999);
        me.addUnit(UnitFactory.createUnit(UnitType.Swordsman, 0, 0, me));
        me.addUnit(UnitFactory.createUnit(UnitType.ArcherShortBow, 0, 1, me));
        me.addUnit(UnitFactory.createUnit(UnitType.Knight, 0, 2, me));
        me.addUnit(UnitFactory.createUnit(UnitType.Mage, 0, 3, me));

        HashMap<String, HashMap<String, Double>> penalties = new HashMap() {{
            put("Warrior", new HashMap<>() {{
                put("*", 1.0);
                put("#", 1.5);
                put("@", 2.0);
                put("!", 1.2);
            }});
            put("Archer", new HashMap<>() {{
                put("*", 1.0);
                put("#", 1.8);
                put("@", 2.2);
                put("!", 1.0);
            }});
            put("Rider", new HashMap<>() {{
                put("*", 1.0);
                put("#", 2.2);
                put("@", 1.2);
                put("!", 1.5);
            }});
            put("Mage", new HashMap<>() {{
                put("*", 1.5);
                put("#", 2.0);
                put("@", 2.5);
                put("!", 1.8);
            }});

        }};

        for (int i = 0; i < 4; i++) {
            IUnit unit = me.getUnits().get(i);

            Pathfinder.availableCells(unit);
            unit.energyRecharge();
            Double startEnergy = unit.getEnergy();
            unit.walk(1, unit.getCol());
            Double endEnergy = unit.getEnergy();
            Double penalty = penalties.get(unit.getClass().getSimpleName()).get("*");
            assertEquals(startEnergy - penalty, endEnergy);

            Pathfinder.availableCells(unit);
            unit.energyRecharge();
            startEnergy = unit.getEnergy();
            unit.walk(2, unit.getCol());
            endEnergy = unit.getEnergy();
            penalty = penalties.get(unit.getClass().getSimpleName()).get("#");
            assertEquals(startEnergy - penalty, endEnergy);

            Pathfinder.availableCells(unit);
            unit.energyRecharge();
            startEnergy = unit.getEnergy();
            unit.walk(3, unit.getCol());
            endEnergy = unit.getEnergy();
            penalty = penalties.get(unit.getClass().getSimpleName()).get("@");
            assertEquals(startEnergy - penalty, endEnergy);

            Pathfinder.availableCells(unit);
            unit.energyRecharge();
            startEnergy = unit.getEnergy();
            unit.walk(4, unit.getCol());
            endEnergy = unit.getEnergy();
            penalty = penalties.get(unit.getClass().getSimpleName()).get("!");
            assertEquals(startEnergy - penalty, endEnergy);
        }
    }


    @Test
    public void testRangeAttack() {
        GridSize.setSize(10);
        Grid grid = Grid.getInstance();
        Player me = new RealPlayer("Robert", 999999.0, ANSI_GREEN, 9999, 9999);
        Player bot = new Bot("Botinok", 50.0, ANSI_RED, 10, 10);

        me.addUnit(UnitFactory.createUnit(UnitType.Swordsman, 0, 0, me));
        me.addUnit(UnitFactory.createUnit(UnitType.Crossbowman, 0, 0, me));
        bot.addUnit(UnitFactory.createUnit(UnitType.Swordsman, 0, 1, bot));
        bot.addUnit(UnitFactory.createUnit(UnitType.Swordsman, 0, 2, bot));
        bot.addUnit(UnitFactory.createUnit(UnitType.Swordsman, 2, 2, bot));
        bot.addUnit(UnitFactory.createUnit(UnitType.Crossbowman, 3, 0, bot));
        bot.addUnit(UnitFactory.createUnit(UnitType.Crossbowman, 7, 7, bot));

        IUnit myUnit1 = me.getUnits().get(0);
        IUnit myUnit2 = me.getUnits().get(1);
        IUnit botUnit1 = bot.getUnits().get(0);
        IUnit botUnit2 = bot.getUnits().get(1);
        IUnit botUnit3 = bot.getUnits().get(2);
        IUnit botUnit4 = bot.getUnits().get(3);
        IUnit botUnit5 = bot.getUnits().get(4);

        assertDoesNotThrow(() -> myUnit1.attack(botUnit1));
        assertThrows(NotEnoughRangeAttack.class, () -> myUnit1.attack(botUnit2));

        assertDoesNotThrow(() -> myUnit2.attack(botUnit3));
        assertThrows(NotEnoughRangeAttack.class, () -> myUnit2.attack(botUnit5));

        assertDoesNotThrow(() -> botUnit4.attack(myUnit2));
        assertThrows(NotEnoughRangeAttack.class, () -> botUnit5.attack(myUnit1));

    }

    @Test
    public void testAttack() throws NotEnoughRangeAttack, UnitHasAlreadyAttacked, UnitHasNotPreparedAnAttack {
        GridSize.setSize(10);
        Grid grid = Grid.getInstance();
        Player me = new RealPlayer("Robert", 999999.0, ANSI_GREEN, 9999, 9999);
        Player bot = new Bot("Botinok", 50.0, ANSI_RED, 10, 10);

        me.addUnit(UnitFactory.createUnit(UnitType.Swordsman, 0, 0, me));
        bot.addUnit(UnitFactory.createUnit(UnitType.Swordsman, 0, 1, bot));

        IUnit myUnit = me.getUnits().getFirst();
        IUnit botUnit = bot.getUnits().getFirst();


        HashMap<DamageType, HashMap<DamageType, Float>> attackMultiplier = new HashMap<>() {{
            put(DamageType.Physical,
                    new HashMap<>() {{
                        put(DamageType.Physical, 0.5F);
                        put(DamageType.Fire, 1F);
                        put(DamageType.Cold, 1F);
                        put(DamageType.Acid, 1F);
                    }}
            );
            put(DamageType.Fire,
                    new HashMap<>() {{
                        put(DamageType.Physical, 0.8F);
                        put(DamageType.Fire, 0.5F);
                        put(DamageType.Cold, 2F);
                        put(DamageType.Acid, 1.1F);
                    }}
            );
            put(DamageType.Cold,
                    new HashMap<>() {{
                        put(DamageType.Physical, 0.8F);
                        put(DamageType.Fire, 1.5F);
                        put(DamageType.Cold, 0.5F);
                        put(DamageType.Acid, 0.9F);
                    }}
            );
            put(DamageType.Acid,
                    new HashMap<>() {{
                        put(DamageType.Physical, 0.8F);
                        put(DamageType.Fire, 1.3F);
                        put(DamageType.Cold, 0.9F);
                        put(DamageType.Acid, 0.5F);
                    }}
            );

        }};

        Float koef = DamageType.attackMultiplier(myUnit.getDamage().getDamageType(), botUnit.getDamage().getDamageType());
        assertEquals(attackMultiplier.get(myUnit.getDamage().getDamageType()).get(botUnit.getDamage().getDamageType()), koef);

        Double botStartHP = botUnit.getHp();
        botUnit.setDefence(0.0);
        myUnit.attack(botUnit);
        Double botEndHP = botUnit.getHp();
        assertEquals(botStartHP - myUnit.getDamage().getValue() * koef, botEndHP);

        me.energize();

        botUnit.setDefence(100.0);
        Double botStartDefence = botUnit.getDefence();
        myUnit.attack(botUnit);
        Double botEndDefence = botUnit.getDefence();
        assertEquals(botStartDefence - myUnit.getDamage().getValue() * koef, botEndDefence);
    }


    @Test
    public void testDeath() throws NotEnoughRangeAttack, UnitHasAlreadyAttacked, UnitHasNotPreparedAnAttack {
        Player me = new RealPlayer("Robert", 999999.0, ANSI_GREEN, 9999, 9999);
        Player bot = new Bot("Botinok", 50.0, ANSI_RED, 10, 10);

        me.addUnit(UnitFactory.createUnit(UnitType.Swordsman, 0, 0, me));
        bot.addUnit(UnitFactory.createUnit(UnitType.Swordsman, 0, 1, bot));

        IUnit myUnit = me.getUnits().getFirst();
        IUnit botUnit = bot.getUnits().getFirst();

        botUnit.setHp(1.0);
        assertArrayEquals(bot.getUnits().toArray(new IUnit[0]), bot.getUnits().toArray(new IUnit[0]));
        myUnit.attack(botUnit);
        assertArrayEquals(bot.getUnits().toArray(), bot.getUnits().toArray(new IUnit[0]));
    }

    @Test
    public void testNewGrid() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("*****\n!!!!!\n@@@@@\n#####\n@*!#@\n".getBytes());
        System.setIn(in);

        Grid.nullInstance();
        GridSize.setSize(5);
        Grid.createGrid();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Grid.getInstance().show();
        String consoleOutput = out.toString().trim();
        assertEquals("+-----+--------+---+---+---+---+---+\r\n" +
                "|     | Column | 1 | 2 | 3 | 4 | 5 |\r\n" +
                "+-----+--------+---+---+---+---+---+\r\n" +
                "| Row |        |   |   |   |   |   |\r\n" +
                "+-----+--------+---+---+---+---+---+\r\n" +
                "| 1   |        | * | * | * | * | * |\r\n" +
                "+-----+--------+---+---+---+---+---+\r\n" +
                "| 2   |        | ! | ! | ! | ! | ! |\r\n" +
                "+-----+--------+---+---+---+---+---+\r\n" +
                "| 3   |        | @ | @ | @ | @ | @ |\r\n" +
                "+-----+--------+---+---+---+---+---+\r\n" +
                "| 4   |        | # | # | # | # | # |\r\n" +
                "+-----+--------+---+---+---+---+---+\r\n" +
                "| 5   |        | @ | * | ! | # | @ |\r\n" +
                "+-----+--------+---+---+---+---+---+", consoleOutput);

        System.setIn(System.in);
        System.setOut(System.out);

    }


//    @Test
//    public void testDownloadGrid() throws IOException {
////        System.in.reset();
//        MyScanner.getScanner().next();
//        ByteArrayInputStream in = new ByteArrayInputStream("*******\n!!!!!!!\n@@@!@!@\n#######\n*!@*!#@\n*******\n!@#*!@#\n".getBytes());
//        System.setIn(in);
//
//        Grid.nullInstance();
//        GridSize.setSize(7);
//        Grid.createGrid();
//
//        SaveGame.save(true, false, false, false);
//        File[] dirs = new File("Saves/").listFiles();
//        File dir = Objects.requireNonNull(dirs)[dirs.length - 2];
//
//        ArrayList<ArrayList<Cell>> grid = LoadGame.loadGrid(dir.toString());
//        GridSize.setSize(grid.size());
//        Grid.getInstance().setGrid(grid);
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out));
//        Grid.getInstance().show();
//
//        String consoleOutput = out.toString().trim();
//        assertEquals("+-----+--------+---+---+---+---+---+\r\n" +
//                "|     | Column | 1 | 2 | 3 | 4 | 5 |\r\n" +
//                "+-----+--------+---+---+---+---+---+\r\n" +
//                "| Row |        |   |   |   |   |   |\r\n" +
//                "+-----+--------+---+---+---+---+---+\r\n" +
//                "| 1   |        | * | * | * | * | * |\r\n" +
//                "+-----+--------+---+---+---+---+---+\r\n" +
//                "| 2   |        | ! | ! | ! | ! | ! |\r\n" +
//                "+-----+--------+---+---+---+---+---+\r\n" +
//                "| 3   |        | @ | ! | @ | ! | @ |\r\n" +
//                "+-----+--------+---+---+---+---+---+\r\n" +
//                "| 4   |        | # | # | # | # | # |\r\n" +
//                "+-----+--------+---+---+---+---+---+\r\n" +
//                "| 5   |        | @ | * | ! | # | @ |\r\n" +
//                "+-----+--------+---+---+---+---+---+", consoleOutput);
//
//        System.setIn(System.in);
//        System.setOut(System.out);
//
//        in.close();
//        out.close();
//    }
}
