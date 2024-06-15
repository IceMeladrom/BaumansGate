package main.Entities.Units.Units;

import main.Entities.Damage.IDamage;
import main.Exceptions.*;
import main.Players.Player;

import java.util.HashMap;

public interface IUnit {
    public HashMap<String, Double> getTerrains();

    void setTerrains(HashMap<String, Double> terrains);

    public Player getPlayer();

    public void setPlayer(Player player);

    public Double getHp();

    public Double getMaxHp();

    public Double getMaxTempHp();

    public void heal();

    public void setHp(Double hp);

    public void setMaxTempHp(Double maxTempHp);

    public void setMaxHp(Double maxHp);

    public IDamage getDamage();

    public void setDamage(IDamage damage);

    public Integer getAttackRange();

    public void setAttackRange(Integer attackRange);

    public Double getDefence();

    public Double getMaxDefence();

    public void setDefence(Double defence);

    public void setMaxTempDefence(Double maxTempDefence);

    public void setMaxDefence(Double maxDefence);

    public Double getEnergy();

    public void setEnergy(Double energy);

    public Double getCost();

    public void setCost(Double cost);

    public int getCol();

    public void setCol(int col);

    public int getRow();

    public void setRow(int row);

    public String getName();

    public void setName(String name);

    public String getSymbol();

    public void setSymbol(String symbol);

    public Double getMaxTempEnergy();

    public Double getMaxEnergy();

    public void setMaxTempEnergy(Double maxTempEnergy);

    public void setMaxEnergy(Double maxEnergy);

    public void energyRecharge();

    public boolean isAlive();

    public boolean getDidAttack();

    public void setDidAttack(boolean didAttack);

    public void walk(int endRow, int endCol) throws NotEnoughEnergy, AlliedUnitAtTheCeil, UnitHasAlreadyAttacked, NotYourTown, UnitHasNotPreparedAnAttack, NotEnoughRangeAttack;
    public void attack(IUnit enemy) throws UnitHasAlreadyAttacked, UnitHasNotPreparedAnAttack, NotEnoughRangeAttack;

    public int getMovesUntilReadyToAttack();

    public void setMovesUntilReadyToAttack(int movesUntilReadyToAttack);

    public int getMovesToPrepareAnAttack();

    public void setMovesToPrepareAnAttack(int movesToPrepareAnAttack);

    public void prepareAttack();

    public boolean getIsAttackPrepared();

    public void setIsAttackPrepared(boolean attackPrepared);

    public void preparing();

    public String save();
}
