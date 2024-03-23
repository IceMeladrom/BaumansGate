package Entities.Units.Units;

import Entities.Actions.Archerable;
import Entities.Actions.MeleeAttackable;
import Entities.Actions.Saddleable;
import Entities.Builds.Town;
import Exceptions.AlliedUnitAtTheCeil;
import Exceptions.NotEnoughEnergy;
import Exceptions.NotYourTown;
import Exceptions.UnitHasAlreadyAttacked;
import Grid.Grid;
import Players.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RiderClass extends UnitClass implements MeleeAttackable, Archerable, Saddleable {
    private HashMap<String, Float> terrains = new HashMap<>();

    public RiderClass(String name, int hp, int damage, int attackRange, int defence, float energy, int cost, String symbol, int row, int col, Player player) {
        super(name, hp, damage, attackRange, defence, energy, cost, symbol, row, col, player);
        terrains.put("*", 1F);
        terrains.put("#", 2.2F);
        terrains.put("@", 1.2F);
        terrains.put("!", 1.5F);
    }

    @Override
    public void walk(int endRow, int endCol) throws NotEnoughEnergy, AlliedUnitAtTheCeil, UnitHasAlreadyAttacked, NotYourTown {
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
            Unit enemy = grid.getUnit(finalEndRow, finalEndCol);
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


    @Override
    public void attack(@NotNull Unit enemy) throws UnitHasAlreadyAttacked {
        if (!getDidAttack()) {
            if (enemy.getDefence() >= getDamage())
                enemy.setDefence(enemy.getDefence() - getDamage());
            else {
                enemy.setHp(enemy.getHp() - (getDamage() - enemy.getDefence()));
                enemy.setDefence(0);
            }
            setDidAttack(true);

        } else {
            throw new UnitHasAlreadyAttacked(this);
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
}
