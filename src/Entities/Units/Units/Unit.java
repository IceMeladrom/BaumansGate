package Entities.Units.Units;

import Entities.Builds.Town;
import Entities.Damage.IDamage;
import Exceptions.*;
import Grid.Grid;
import Players.Player;

import java.util.HashMap;

abstract public class Unit implements IUnit {
    public Unit(String name, int hp, IDamage damage, int attackRange, int defence, float energy, int cost, String symbol, int row, int col, Player player) {
        this.hp = hp;
        this.damage = damage;
//        this.movesToPrepareAnAttack = movesToPrepareAnAttack;
        this.attackRange = attackRange;
        this.defence = defence;
        this.energy = energy;
        this.maxEnergy = energy;
        this.cost = cost;
        this.name = name;
        this.row = row;
        this.col = col;
        this.symbol = symbol;
        maxHp = hp;
        maxDefence = defence;
        this.player = player;
        didAttack = false;
        terrains = new HashMap<>();
        isAttackPrepared = false;
    }

    private IDamage damage;
    private HashMap<String, Float> terrains;
    private int movesToPrepareAnAttack, movesUntilReadyToAttack;
    private boolean isAttackPrepared;
    private int hp, attackRange, defence, cost;
    private int row, col;
    private final int maxHp, maxDefence;
    private float energy, maxEnergy;
    private String name, symbol;
    private Player player;
    private boolean didAttack;

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
    public int getHp() {
        return hp;
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }

    public void heal() {
        hp = maxHp;
    }

    @Override
    public void setHp(int hp) {
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
    public int getAttackRange() {
        return attackRange;
    }

    @Override
    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    @Override
    public int getDefence() {
        return defence;
    }

    public int getMaxDefence() {
        return maxDefence;
    }

    @Override
    public void setDefence(int defence) {
        this.defence = defence;
    }

    @Override
    public float getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(float energy) {
        this.energy = energy;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public void setCost(int cost) {
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
    public float getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public void setMaxEnergy(float maxEnergy) {
        this.maxEnergy = maxEnergy;
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

    public void walk(int endRow, int endCol) throws NotEnoughEnergy, AlliedUnitAtTheCeil, UnitHasAlreadyAttacked, NotYourTown, UnitHasNotPreparedAnAttack {
        Grid grid = Grid.getInstance();
        int currentRow = getRow(), currentCol = getCol();
        int finalEndRow = endRow, finalEndCol = endCol;
        if (currentRow == endRow && currentCol == endCol)
            return;

        Town town = grid.getCeil(endRow, endCol).getTown();
        if (town != null) {
            // If Town yours
            if (town.getPlayer() != getPlayer())
                throw new NotYourTown(this);
        }
        // If your unit want to go to the ceil with unit.
        if (grid.getCeil(endRow, endCol).getUnit() != null) {
            // If your unit want to go to the ceil with allied unit.
            if (getPlayer().getUnits().contains(grid.getUnit(endRow, endCol))) {
                throw new AlliedUnitAtTheCeil(currentRow, currentCol, endRow, endCol);
            }

            // If your unit want to go to the ceil with enemy unit.

            // Check if your unit can do range attack
            IUnit enemy = grid.getUnit(finalEndRow, finalEndCol);
            if (getAttackRange() != 1 && getAttackRange() >= (endRow - getRow() + endCol - getCol())) {
                attack(enemy);
                if (!enemy.isAlive()) {
                    grid.getCeil(enemy.getRow(), enemy.getCol()).setUnit(null);
                    enemy.getPlayer().deleteUnit(enemy);
                }
                return;
            }

            if (currentRow == endRow) {
                if (currentCol < endCol)
                    endCol -= getAttackRange();
                else
                    endCol += getAttackRange();
            } else {
                if (currentRow < endRow)
                    endRow -= getAttackRange();
                else
                    endRow += getAttackRange();
            }

            float totalSpendEnergy = spentEnergy(grid, currentRow, endRow, currentCol, endCol);
            if (getEnergy() < totalSpendEnergy)
                throw new NotEnoughEnergy(currentRow, currentCol, endRow, endCol, totalSpendEnergy);
            setEnergy(getEnergy() - totalSpendEnergy);
            grid.getCeil(getRow(), getCol()).setUnit(null);
            setRow(endRow);
            setCol(endCol);
            grid.getCeil(getRow(), getCol()).setUnit(this);
            attack(enemy);
            if (!enemy.isAlive()) {
                grid.getCeil(getRow(), getCol()).setUnit(null);
                setRow(enemy.getRow());
                setCol(enemy.getCol());
                grid.getCeil(enemy.getRow(), enemy.getCol()).setUnit(this);
                enemy.getPlayer().deleteUnit(enemy);
            }
        } else {
            // If your unit have enough energy to go to the free ceil.
            float totalSpendEnergy = spentEnergy(grid, currentRow, endRow, currentCol, endCol);
            if (getEnergy() < totalSpendEnergy)
                throw new NotEnoughEnergy(currentRow, currentCol, endRow, endCol, totalSpendEnergy);
            setEnergy(getEnergy() - totalSpendEnergy);
            grid.getCeil(getRow(), getCol()).setUnit(null);
            setRow(endRow);
            setCol(endCol);
            grid.getCeil(getRow(), getCol()).setUnit(this);
            if (town != null)
                town.healUnit(this);
        }
    }

    private float spentEnergy(Grid grid, int currentRow, int endRow, int currentCol, int endCol) {
        float totalSpendEnergy = 0;
        String terrain = "";
        if (currentRow <= endRow) {
            currentRow += 1;
            for (; currentRow <= endRow; currentRow++) {
                terrain = grid.getCeil(currentRow, currentCol).getTerrain();
                totalSpendEnergy += terrains.get(terrain);
            }
            currentRow -= 1;
        } else {
            currentRow -= 1;
            for (; currentRow >= endRow; currentRow--) {
                terrain = grid.getCeil(currentRow, currentCol).getTerrain();
                totalSpendEnergy += terrains.get(terrain);
            }
            currentRow += 1;
        }

        if (currentCol <= endCol) {
            currentCol += 1;
            for (; currentCol <= endCol; currentCol++) {
                terrain = grid.getCeil(currentRow, currentCol).getTerrain();
                totalSpendEnergy += terrains.get(terrain);
            }
            currentCol -= 1;
        } else {
            currentCol -= 1;
            for (; currentCol >= endCol; currentCol--) {
                terrain = grid.getCeil(currentRow, currentCol).getTerrain();
                totalSpendEnergy += terrains.get(terrain);
            }
            currentCol += 1;
        }

        return totalSpendEnergy;
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
