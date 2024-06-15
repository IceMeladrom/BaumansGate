package main.Entities.Units.Units;

import main.Entities.Builds.Town;
import main.Entities.Damage.IDamage;
import main.Exceptions.*;
import main.Grid.Grid;
import main.Grid.Pathfinder;
import main.Players.Player;
import main.Utilities.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Unit implements IUnit {
    public Unit(String name, Double hp, IDamage damage, Integer attackRange, Double defence, Double energy, Double cost, String symbol, int row, int col, Player player) {
        this.hp = hp;
        maxHp = hp;
        maxTempHp = hp;
        this.damage = damage;
        this.attackRange = attackRange;
        this.defence = defence;
        maxDefence = defence;
        maxTempDefence = defence;
        this.energy = energy;
        maxEnergy = energy;
        maxTempEnergy = energy;
        this.cost = cost;
        this.name = name;
        this.row = row;
        this.col = col;
        this.symbol = symbol;
        this.player = player;
        didAttack = false;
        terrains = new HashMap<>();
        isAttackPrepared = false;
    }

    private Player player;
    private String name, symbol;
    private Double hp, defence, cost, energy, maxTempHp, maxTempDefence, maxTempEnergy, maxHp, maxDefence, maxEnergy;
    private IDamage damage;
    private Integer attackRange;
    private int row, col;
    private HashMap<String, Double> terrains;
    private int movesToPrepareAnAttack, movesUntilReadyToAttack;
    private boolean isAttackPrepared, didAttack;

    @Override
    public HashMap<String, Double> getTerrains() {
        return terrains;
    }

    @Override
    public void setTerrains(HashMap<String, Double> terrains) {
        this.terrains = terrains;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Double getHp() {
        return hp;
    }

    @Override
    public Double getMaxHp() {
        return maxHp;
    }

    @Override
    public Double getMaxTempHp() {
        return maxTempHp;
    }

    @Override
    public void heal() {
        setHp(getMaxTempHp());
    }

    @Override
    public void setHp(Double hp) {
        this.hp = hp;
    }

    @Override
    public void setMaxTempHp(Double maxTempHp) {
        this.maxTempHp = maxTempHp;
    }

    @Override
    public void setMaxHp(Double maxHp) {
        this.maxHp = maxHp;
        setMaxTempHp(maxHp);
    }

    @Override
    public IDamage getDamage() {
        return damage;
    }

    @Override
    public void setDamage(IDamage damage) {
        this.damage = damage;
    }

    @Override
    public Integer getAttackRange() {
        return attackRange;
    }

    @Override
    public void setAttackRange(Integer attackRange) {
        this.attackRange = attackRange;
    }

    @Override
    public Double getDefence() {
        return defence;
    }

    public Double getMaxTempDefence() {
        return maxTempDefence;
    }

    @Override
    public Double getMaxDefence() {
        return maxDefence;
    }

    @Override
    public void setDefence(Double defence) {
        this.defence = defence;
    }

    @Override
    public void setMaxTempDefence(Double maxTempDefence) {
        this.maxTempDefence = maxTempDefence;
    }

    @Override
    public void setMaxDefence(Double maxDefence) {
        this.maxDefence = maxDefence;
        setMaxTempDefence(maxDefence);
    }

    @Override
    public Double getEnergy() {
        return energy;
    }

    @Override
    public Double getMaxTempEnergy() {
        return maxTempEnergy;
    }

    @Override
    public Double getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    @Override
    public void setMaxTempEnergy(Double maxTempEnergy) {
        this.maxTempEnergy = maxTempEnergy;
    }

    @Override
    public void setMaxEnergy(Double maxEnergy) {
        this.maxEnergy = maxEnergy;
        setMaxTempEnergy(maxEnergy);
    }

    @Override
    public Double getCost() {
        return cost;
    }

    @Override
    public void setCost(Double cost) {
        this.cost = cost;
    }


    @Override
    public int getCol() {
        return col;
    }

    @Override
    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void energyRecharge() {
        energy = maxEnergy;
    }

    @Override
    public boolean isAlive() {
        return hp > 0;
    }

    @Override
    public boolean getDidAttack() {
        return didAttack;
    }

    @Override
    public void setDidAttack(boolean didAttack) {
        this.didAttack = didAttack;
    }

    @Override
    public void walk(int endRow, int endCol) throws NotEnoughEnergy, AlliedUnitAtTheCeil, UnitHasAlreadyAttacked, NotYourTown, UnitHasNotPreparedAnAttack, NotEnoughRangeAttack {
        Grid grid = Grid.getInstance();
        ArrayList<ArrayList<Pair>> costs = Pathfinder.getCosts();
        int currentRow = getRow(), currentCol = getCol();
        boolean wantToAttack = false;

        if (currentRow == endRow && currentCol == endCol)
            return;

        Town town = grid.getCell(endRow, endCol).getTown();
        if (town != null) {
            // If the Town is not yours
            if (town.getPlayer() != getPlayer())
                throw new NotYourTown(this);
        }

        // If your unit want to go to the ceil with unit.
        IUnit enemy = grid.getUnit(endRow, endCol);
        if (enemy != null) {
            // If your unit want to go to the ceil with allied unit.
            if (enemy.getPlayer() == getPlayer())
                throw new AlliedUnitAtTheCeil(currentRow, currentCol, endRow, endCol);

            // If your unit want to go to the ceil with enemy unit.
            Pair previous = costs.get(endRow).get(endCol);
            for (int i = 0; i < getAttackRange(); i++) {
                previous = costs.get(previous.getRow()).get(previous.getCol()).getPrevious();
            }
            endRow = previous.getRow();
            endCol = previous.getCol();
            wantToAttack = true;
        }

        Double totalSpentEnergy = costs.get(endRow).get(endCol).getCost();
        if (getEnergy() < totalSpentEnergy)
            throw new NotEnoughEnergy(currentRow, currentCol, endRow, endCol, totalSpentEnergy);
        setEnergy(getEnergy() - totalSpentEnergy);
        grid.getCell(getRow(), getCol()).setUnit(null);
        setRow(endRow);
        setCol(endCol);
        grid.getCell(getRow(), getCol()).setUnit(this);

        if (wantToAttack) {
            attack(enemy);
            if (!enemy.isAlive()) {
                if (getAttackRange() == 1) {
                    grid.getCell(getRow(), getCol()).setUnit(null);
                    setRow(enemy.getRow());
                    setCol(enemy.getCol());
                    grid.getCell(enemy.getRow(), enemy.getCol()).setUnit(this);
                } else {
                    grid.getCell(enemy.getRow(), enemy.getCol()).setUnit(null);
                }
                enemy.getPlayer().deleteUnit(enemy);
            }
        }

        if (town != null)
            town.healUnit(this);
    }

    @Override
    public int getMovesUntilReadyToAttack() {
        return movesUntilReadyToAttack;
    }

    @Override
    public void setMovesUntilReadyToAttack(int movesUntilReadyToAttack) {
        this.movesUntilReadyToAttack = movesUntilReadyToAttack;
    }

    @Override
    public int getMovesToPrepareAnAttack() {
        return movesToPrepareAnAttack;
    }

    @Override
    public void setMovesToPrepareAnAttack(int movesToPrepareAnAttack) {
        this.movesToPrepareAnAttack = movesToPrepareAnAttack;
    }

    @Override
    public void prepareAttack() {
        movesUntilReadyToAttack = movesToPrepareAnAttack;
    }

    @Override
    public boolean getIsAttackPrepared() {
        return isAttackPrepared;
    }


    @Override
    public void setIsAttackPrepared(boolean attackPrepared) {
        isAttackPrepared = attackPrepared;
    }

    @Override
    public void preparing() {
        if (movesUntilReadyToAttack > 0) {
            movesUntilReadyToAttack -= 1;
            if (movesUntilReadyToAttack == 0)
                isAttackPrepared = true;
        } else {
            movesUntilReadyToAttack = -1;
        }
    }

    public String save() {
        StringBuilder ret = new StringBuilder();
        ret.append(getClass().getName()).append("\n");

        ret.append(name).append(";;")
                .append(hp).append(";;")
                .append(maxHp).append(";;")
                .append(maxTempHp).append(";;")
                .append(damage.getDamageType().name()).append(";;")
                .append(damage.getValue()).append(";;")
                .append(attackRange).append(";;")
                .append(defence).append(";;")
                .append(maxDefence).append(";;")
                .append(maxTempDefence).append(";;")
                .append(energy).append(";;")
                .append(maxEnergy).append(";;")
                .append(maxTempEnergy).append(";;")
                .append(cost).append(";;")
                .append(row).append(";;")
                .append(col).append(";;")
                .append(symbol).append(";;")
                .append(didAttack).append(";;")
                .append(movesToPrepareAnAttack).append(";;")
                .append(movesUntilReadyToAttack).append(";;")
                .append(isAttackPrepared).append(";;");
        if (getClass().equals(Mage.class))
            ret.append(((Mage) this).getSpells()).append(";;");
        ret.append("\n");
        ret.append("*--").append(terrains.get("*")).append(";;")
                .append("#--").append(terrains.get("#")).append(";;")
                .append("@--").append(terrains.get("@")).append(";;")
                .append("!--").append(terrains.get("!")).append(";;\n");
        return ret.toString();
    }
}
