package Entities.Units.Units;

import Entities.Damage.IDamage;
import Exceptions.*;
import Players.Player;

import java.util.HashMap;

public interface IUnit {
    public HashMap<String, Float> getTerrains();

    public Player getPlayer();

    public void setPlayer(Player player);

    public int getHp();

    public int getMaxHp();

    public void heal();

    public void setHp(int hp);

    public IDamage getDamage();

    public void setDamage(IDamage damage);

    public int getAttackRange();

    public void setAttackRange(int attackRange);

    public int getDefence();

    public int getMaxDefence();

    public void setDefence(int defence);

    public Double getEnergy();

    public void setEnergy(Double energy);

    public int getCost();

    public void setCost(int cost);

    public int getCol();

    public int getRow();

    public void setCol(int col);

    public void setRow(int row);

    public String getName();

    public void setName(String name);

    public String getSymbol();

    public void setSymbol(String symbol);

    public Double getMaxEnergy();

    public void setMaxEnergy(Double maxEnergy);

    public void energyRecharge();

    public boolean isAlive();

    public int getMovesUntilReadyToAttack();

    public void setMovesUntilReadyToAttack(int movesUntilReadyToAttack);

    public void walk(int row, int col) throws NotEnoughEnergy, AlliedUnitAtTheCeil, UnitHasAlreadyAttacked, NotYourTown, UnitHasNotPreparedAnAttack;

    public void attack(IUnit enemy) throws UnitHasAlreadyAttacked, UnitHasNotPreparedAnAttack;

    public int getMovesToPrepareAnAttack();

    public void setMovesToPrepareAnAttack(int movesToPrepareAnAttack);

    public void prepareAttack();

    public boolean getDidAttack();

    public void setDidAttack(boolean didAttack);

    public boolean getIsAttackPrepared();

    public void setAttackPrepared(boolean attackPrepared);

    public void preparing();
}
