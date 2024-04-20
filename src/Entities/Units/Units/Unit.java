package Entities.Units.Units;

import Entities.Builds.Town;
import Entities.Damage.IDamage;
import Exceptions.*;
import Grid.Grid;
import Grid.Pathfinder;
import Players.Player;
import Utilities.Pair;

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
    private Double hp, defence, cost, energy, maxTempHp, maxTempDefence, maxTempEnergy;
    private final Double maxHp, maxDefence, maxEnergy;
    private IDamage damage;
    private Integer attackRange;
    private int row, col;
    private final HashMap<String, Float> terrains;
    private int movesToPrepareAnAttack, movesUntilReadyToAttack;
    private boolean isAttackPrepared, didAttack;

    @Override
    public HashMap<String, Float> getTerrains() {
        return terrains;
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
    public void heal() {
        hp = maxHp;
    }

    @Override
    public void setHp(Double hp) {
        this.hp = hp;
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

    @Override
    public Double getMaxDefence() {
        return maxDefence;
    }

    @Override
    public void setDefence(Double defence) {
        this.defence = defence;
    }

    @Override
    public Double getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(Double energy) {
        this.energy = energy;
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
    public Double getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public void setMaxEnergy(Double maxTempEnergy) {
        this.maxTempEnergy = maxTempEnergy;
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
    public void walk(int endRow, int endCol) throws NotEnoughEnergy, AlliedUnitAtTheCeil, UnitHasAlreadyAttacked, NotYourTown, UnitHasNotPreparedAnAttack {
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
    public void setAttackPrepared(boolean attackPrepared) {
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
}
