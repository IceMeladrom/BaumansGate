package Save;

import Entities.Builds.*;
import Entities.Damage.Damage;
import Entities.Damage.DamageType;
import Entities.Units.Units.*;
import Grid.Cell;
import Players.Player;
import Players.Players.RealPlayer;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LoadGame {
    public static LoadedFile load(String path) throws IOException {
        ArrayList<ArrayList<Cell>> grid = loadGrid(path);
        Player me = loadPlayer(path, "me");
        Player bot = loadPlayer(path, "bot");
        Town myTown = loadTown(path, "my", me);
        Town botTown = loadTown(path, "bot", bot);
        ArrayList<IUnit> myUnits = loadUnits(path, "my", me);
        ArrayList<IUnit> botUnits = loadUnits(path, "bot", bot);
        ArrayList<NewUnit> unitTypes = loadUnitTypes(path);
        return new LoadedFile(grid, me, bot, myTown, botTown, myUnits, botUnits, unitTypes);
    }

    private static ArrayList<ArrayList<Cell>> loadGrid(String path) throws IOException {
        FileReader gridFile = new FileReader(path + "/grid.txt");
        Scanner scanner = new Scanner(gridFile);

        ArrayList<ArrayList<Cell>> newGrid = new ArrayList<>();
        int size = Integer.parseInt(scanner.nextLine());
        char[] terrainRow;
        for (int i = 0; i < size; i++) {
            terrainRow = scanner.nextLine().toCharArray();
            ArrayList<Cell> tempArr = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                Cell cell = new Cell(i, j, null, String.valueOf(terrainRow[j]));
                tempArr.add(cell);
            }
            newGrid.add(tempArr);
        }
        gridFile.close();
        scanner.close();
        return newGrid;
    }

    private static Player loadPlayer(String path, String who) throws IOException {
        FileReader file = new FileReader(path + "/" + who + ".txt");
        Scanner scanner = new Scanner(file);
        ArrayList<String> playerParams = new ArrayList<>(Arrays.asList(scanner.nextLine().split(";;")));
        Player player = new RealPlayer(playerParams.get(0), Double.parseDouble(playerParams.get(1)), playerParams.get(2), Integer.parseInt(playerParams.get(3)), Integer.parseInt(playerParams.get(4)));
        file.close();
        scanner.close();
        return player;
    }

    private static Town loadTown(String path, String who, Player player) throws IOException {
        FileReader file = new FileReader(path + "/" + who + "Town.txt");
        Scanner scanner = new Scanner(file);
        ArrayList<String> townParams = new ArrayList<>(Arrays.asList(scanner.nextLine().split(";;")));
        Town town = new Town(player, townParams.get(0), townParams.get(1), Integer.parseInt(townParams.get(2)), Integer.parseInt(townParams.get(3)));
        ArrayList<String> townBuildings = new ArrayList<>(Arrays.asList(scanner.nextLine().split(";;")));
        for (String building : townBuildings) {
            List<String> buildingList = Arrays.asList(building.split("--"));
            String buildingName = buildingList.get(0);
            Integer buildingSize = Integer.parseInt(buildingList.get(1));
            Integer buildingLevel = Integer.parseInt(buildingList.get(2));


            switch (Buildings.valueOf(buildingName)) {
                case WitchHouse -> {
                    if (buildingSize > 0)
                        town.getBuildings().put(Buildings.valueOf(buildingName), new ArrayList<>() {{
                            add(new WitchHouse());
                            get(0).setLevel(buildingLevel);
                        }});
                }
                case Tavern -> {
                    if (buildingSize > 0) town.getBuildings().put(Buildings.valueOf(buildingName), new ArrayList<>() {{
                        add(new Tavern());
                        get(0).setLevel(buildingLevel);
                    }});
                }
                case Forge -> {
                    if (buildingSize > 0) town.getBuildings().put(Buildings.valueOf(buildingName), new ArrayList<>() {{
                        add(new Forge());
                        get(0).setLevel(buildingLevel);
                    }});
                }
                case Academy -> {
                    if (buildingSize > 0) town.getBuildings().put(Buildings.valueOf(buildingName), new ArrayList<>() {{
                        add(new Academy());
                    }});
                }
                case Arsenal -> {
                    if (buildingSize > 0) town.getBuildings().put(Buildings.valueOf(buildingName), new ArrayList<>() {{
                        add(new Arsenal());
                        get(0).setLevel(buildingLevel);
                    }});
                }
                case Market -> {
                    if (buildingSize > 0) town.getBuildings().put(Buildings.valueOf(buildingName), new ArrayList<>() {{
                        add(new Market());
                    }});
                }
                case Workshop -> {
                    if (buildingSize > 0) town.getBuildings().put(Buildings.valueOf(buildingName), new ArrayList<>() {{
                        for (int i = 0; i < buildingSize; i++)
                            add(new Workshop());
                    }});
                }
            }
        }
        file.close();
        scanner.close();
        return town;
    }

    private static ArrayList<IUnit> loadUnits(String path, String who, Player player) throws IOException {
        FileReader file = new FileReader(path + "/" + who + "Units.txt");
        Scanner scanner = new Scanner(file);
        ArrayList<IUnit> units = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String unitClass = Arrays.asList(scanner.nextLine().split("\\.")).getLast();

            ArrayList<String> unitParams = new ArrayList<>(Arrays.asList(scanner.nextLine().split(";;")));

            String name = unitParams.get(0);

            Double hp = Double.parseDouble(unitParams.get(1));
            Double maxHp = Double.parseDouble(unitParams.get(2));
            Double maxTempHp = Double.parseDouble(unitParams.get(3));

            DamageType damageType = DamageType.valueOf(unitParams.get(4));
            Double damageValue = Double.parseDouble(unitParams.get(5));

            Integer attackRange = Integer.parseInt(unitParams.get(6));

            Double defence = Double.parseDouble(unitParams.get(7));
            Double maxDefence = Double.parseDouble(unitParams.get(8));
            Double maxTempDefence = Double.parseDouble(unitParams.get(9));

            Double energy = Double.parseDouble(unitParams.get(10));
            Double maxEnergy = Double.parseDouble(unitParams.get(11));
            Double maxTempEnergy = Double.parseDouble(unitParams.get(12));

            Double cost = Double.parseDouble(unitParams.get(13));

            int row = Integer.parseInt(unitParams.get(14));
            int col = Integer.parseInt(unitParams.get(15));

            String symbol = unitParams.get(16);

            boolean didAttack = Boolean.parseBoolean(unitParams.get(17));
            int movesToPrepareAnAttack = Integer.parseInt(unitParams.get(18));
            int movesUntilReadyToAttack = Integer.parseInt(unitParams.get(19));
            boolean isAttackPrepared = Boolean.parseBoolean(unitParams.get(20));

            HashMap<String, Double> terrains = new HashMap<>();
            ArrayList<String> terrainsParams = new ArrayList<>(Arrays.asList(scanner.nextLine().split(";;")));
            for (String terrain : terrainsParams) {
                String key = Arrays.asList(terrain.split("--")).getFirst();
                Double value = Double.parseDouble(Arrays.asList(terrain.split("--")).getLast());
                terrains.put(key, value);
            }
            switch (unitClass) {
                case "Warrior" ->
                        units.add(new Warrior(name, hp, new Damage(damageType, damageValue), attackRange, defence, energy, cost, symbol, row, col, player));
                case "Archer" ->
                        units.add(new Archer(name, hp, new Damage(damageType, damageValue), attackRange, defence, energy, cost, symbol, row, col, player));
                case "Rider" ->
                        units.add(new Rider(name, hp, new Damage(damageType, damageValue), attackRange, defence, energy, cost, symbol, row, col, player));
                case "Mage" ->
                        units.add(new Mage(name, hp, new Damage(damageType, damageValue), attackRange, defence, energy, cost, symbol, row, col, player));
            }
            units.getLast().setMaxHp(maxHp);
            units.getLast().setMaxTempHp(maxTempHp);
            units.getLast().setMaxDefence(maxDefence);
            units.getLast().setMaxTempDefence(maxTempDefence);
            units.getLast().setMaxEnergy(maxEnergy);
            units.getLast().setMaxTempEnergy(maxTempEnergy);
            units.getLast().setDidAttack(didAttack);
            units.getLast().setMovesToPrepareAnAttack(movesToPrepareAnAttack);
            units.getLast().setMovesUntilReadyToAttack(movesUntilReadyToAttack);
            units.getLast().setIsAttackPrepared(isAttackPrepared);
            units.getLast().setTerrains(terrains);
        }

        file.close();
        scanner.close();
        return units;
    }

    private static ArrayList<NewUnit> loadUnitTypes(String path) throws IOException {
        FileReader file = new FileReader(path + "/unitTypes.txt");
        Scanner scanner = new Scanner(file);
        ArrayList<NewUnit> unitTypes = new ArrayList<>();
        while (scanner.hasNextLine()) {
            List<String> unitTypeParams = Arrays.asList(scanner.nextLine().split(";;"));
            unitTypes.add(new NewUnit(
                    unitTypeParams.get(1),
                    unitTypeParams.get(0),
                    Double.parseDouble(unitTypeParams.get(2)),
                    DamageType.valueOf(unitTypeParams.get(3)),
                    Double.parseDouble(unitTypeParams.get(4)),
                    Integer.parseInt(unitTypeParams.get(5)),
                    Double.parseDouble(unitTypeParams.get(6)),
                    Double.parseDouble(unitTypeParams.get(7)),
                    Double.parseDouble(unitTypeParams.get(8)),
                    unitTypeParams.get(9)));
            HashMap<String, Double> terrains = new HashMap<>() {{
                for (int i = 10; i < 14; i++) {
                    put(Arrays.asList(unitTypeParams.get(i).split("--")).getFirst(), Double.valueOf(Arrays.asList(unitTypeParams.get(10).split("--")).getLast()));
                }
            }};
            unitTypes.getLast().setTerrains(terrains);
        }

        file.close();
        scanner.close();
        return unitTypes;
    }
}
