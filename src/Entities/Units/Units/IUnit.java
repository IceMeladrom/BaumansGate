package Entities.Units.Units;

import Exceptions.AlliedUnitAtTheCeil;
import Exceptions.NotEnoughEnergy;
import Exceptions.NotYourTown;
import Exceptions.UnitHasAlreadyAttacked;
import Players.Player;

public interface IUnit {
    public Player getPlayer();

    public void setPlayer(Player player);

    public int getHp();

    public int getMaxHp();

    public void heal();

    public void setHp(int hp);

    public int getDamage();

    public void setDamage(int damage);

    public int getAttackRange();

    public void setAttackRange(int attackRange);

    public int getDefence();

    public int getMaxDefence();

    public void setDefence(int defence);

    public float getEnergy();

    public void setEnergy(float energy);

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

    public float getMaxEnergy();

    public void setMaxEnergy(float maxEnergy);

    public void energyRecharge();

    public boolean isAlive();

    public void walk(int row, int col) throws NotEnoughEnergy, AlliedUnitAtTheCeil, UnitHasAlreadyAttacked, NotYourTown;

    public void attack(IUnit enemy) throws UnitHasAlreadyAttacked;

    public boolean getDidAttack();

    public void setDidAttack(boolean didAttack);

}
