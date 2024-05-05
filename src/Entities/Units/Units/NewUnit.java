package Entities.Units.Units;

import Entities.Damage.DamageType;

import java.util.HashMap;

public class NewUnit {
    private final String unitClass, symbol;
    private Double hp, defence, energy, cost, damageValue;
    private DamageType damageType;
    private Integer attackRange;
    private final HashMap<String, Double> terrains;

    public NewUnit(String unitClass, Double hp, DamageType damageType, Double damageValue, Integer attackRange, Double defence, Double energy, Double cost, String symbol) {
        this.unitClass = unitClass;
        this.hp = hp;
        this.damageType = damageType;
        this.damageValue = damageValue;
        this.attackRange = attackRange;
        this.defence = defence;
        this.energy = energy;
        this.cost = cost;
        this.symbol = symbol;

        switch (unitClass) {
            case "Warrior" -> terrains = new HashMap<>() {{
                put("*", 1.0);
                put("#", 1.5);
                put("@", 2.0);
                put("!", 1.2);
            }};
            case "Archer" -> terrains = new HashMap<>() {{
                put("*", 1.0);
                put("#", 1.8);
                put("@", 2.2);
                put("!", 1.0);
            }};
            case "Rider" -> terrains = new HashMap<>() {{
                put("*", 1.0);
                put("#", 2.2);
                put("@", 1.2);
                put("!", 1.5);
            }};
            case "Mage" -> terrains = new HashMap<>() {{
                put("*", 1.5);
                put("#", 2.0);
                put("@", 2.5);
                put("!", 1.8);
            }};
            default -> terrains = new HashMap<>() {{
                put("*", 1.0);
                put("#", 1.5);
                put("@", 2.0);
                put("!", 1.2);
            }};
        }
    }

    public String getUnitClass() {
        return unitClass;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getHp() {
        return hp;
    }

    public void setHp(Double hp) {
        this.hp = hp;
    }

    public Double getDefence() {
        return defence;
    }

    public void setDefence(Double defence) {
        this.defence = defence;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getDamageValue() {
        return damageValue;
    }

    public void setDamageValue(Double damageValue) {
        this.damageValue = damageValue;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public Integer getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(Integer attackRange) {
        this.attackRange = attackRange;
    }

    public HashMap<String, Double> getTerrains() {
        return terrains;
    }
}
