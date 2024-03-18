package Entities.Units.Units;

import Grid.Grid;
import Players.Player;

abstract public class UnitClass implements Unit {
    public UnitClass(String name, int hp, int damage, int attackRange, int defence, float energy, int cost, String symbol, int row, int col, Player player) {
        this.hp = hp;
        this.damage = damage;
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
    }

    private int hp, damage, attackRange, defence, cost;
    private int row, col;
    private final int maxHp, maxDefence;
    private float energy, maxEnergy;
    private String name, symbol;
    private Player player;
    private boolean didAttack;

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
    public int getDamage() {
        return damage;
    }

    @Override
    public void setDamage(int damage) {
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



}
