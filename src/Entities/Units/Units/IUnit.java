package Entities.Units.Units;

import Entities.Damage.IDamage;
import Exceptions.*;
import Players.Player;

import java.util.HashMap;

public interface IUnit {
    HashMap<String, Float> getTerrains();

    Player getPlayer();

    void setPlayer(Player player);

    Double getHp();

    Double getMaxHp();

    void heal();

    void setHp(Double hp);

    IDamage getDamage();

    void setDamage(IDamage damage);

    Integer getAttackRange();

    void setAttackRange(Integer attackRange);

    Double getDefence();

    Double getMaxDefence();

    void setDefence(Double defence);

    Double getEnergy();

    void setEnergy(Double energy);

    Double getCost();

    void setCost(Double cost);

    int getCol();

    void setCol(int col);

    int getRow();

    void setRow(int row);

    String getName();

    void setName(String name);

    String getSymbol();

    void setSymbol(String symbol);

    Double getMaxEnergy();

    void setMaxEnergy(Double maxEnergy);

    void energyRecharge();

    boolean isAlive();

    boolean getDidAttack();

    void setDidAttack(boolean didAttack);

    void walk(int endRow, int endCol) throws NotEnoughEnergy, AlliedUnitAtTheCeil, UnitHasAlreadyAttacked, NotYourTown, UnitHasNotPreparedAnAttack;
    void attack(IUnit enemy) throws UnitHasAlreadyAttacked, UnitHasNotPreparedAnAttack;

    int getMovesUntilReadyToAttack();

    void setMovesUntilReadyToAttack(int movesUntilReadyToAttack);

    int getMovesToPrepareAnAttack();

    void setMovesToPrepareAnAttack(int movesToPrepareAnAttack);

    void prepareAttack();

    boolean getIsAttackPrepared();

    void setAttackPrepared(boolean attackPrepared);

    void preparing();
}
