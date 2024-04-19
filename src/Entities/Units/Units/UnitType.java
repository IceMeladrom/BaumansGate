package Entities.Units.Units;

import Entities.Damage.DamageFactory;
import Entities.Damage.DamageType;
import Entities.Damage.IDamage;
import Utilities.Constants.MyRandom;

import java.util.Random;

public enum UnitType {
    Swordsman("Swordsman", 50.0, DamageFactory.createDamage(DamageType.Physical, 5.0), 1, 8.0, 3.0, 10.0),

    Spearman("Spearman", 35.0, DamageFactory.createDamage(DamageType.Cold, 3.0), 1, 4.0, 6.0, 15.0),

    Axeman("Axeman", 45.0, DamageFactory.createDamage(DamageType.Cold,9.0), 1, 3.0, 4.0, 20.0),

    ArcherLongBow("ArcherLongBow", 30.0, DamageFactory.createDamage(DamageType.Acid,6.0), 5, 8.0, 2.0, 15.0),

    ArcherShortBow("ArcherShortBow", 25.0, DamageFactory.createDamage(DamageType.Acid,3.0), 3, 4.0, 4.0, 19.0),

    Crossbowman("Crossbowman", 40.0, DamageFactory.createDamage(DamageType.Fire,7.0), 6, 3.0, 2.0, 23.0),

    Knight("Knight", 30.0, DamageFactory.createDamage(DamageType.Physical,5.0), 1, 3.0, 6.0, 20.0),

    Cuirassier("Cuirassier", 50.0, DamageFactory.createDamage(DamageType.Cold,2.0), 1, 7.0, 5.0, 23.0),

    HorseArcher("HorseArcher", 25.0, DamageFactory.createDamage(DamageType.Acid,3.0), 3, 2.0, 5.0, 25.0),
    Mage("Mage", 15.0, DamageFactory.createDamage(DamageType.Fire,9.0), 3, 4.0, 3.0, 30.0),
    GodMelee("GodMelee", 999.0, DamageFactory.createDamage(DamageType.Cold,999.0), 1, 999.0, 999.0, 999.0),
    GodRange("GodRange", 999.0, DamageFactory.createDamage(DamageType.Acid,999.0), 999, 999.0, 999.0, 999.0);

    private final String name;
    private Double hp, defence, energy, cost;
    private IDamage damage;
    private Integer attackRange;
    UnitType(String name, Double hp, IDamage  damage, Integer attackRange, Double defence, Double energy, Double cost) {
        this.hp = hp;
        this.damage = damage;
        this.attackRange = attackRange;
        this.defence = defence;
        this.energy = energy;
        this.cost = cost;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Double getHp() {
        return hp;
    }

    public void setHp(Double hp) {
        this.hp = hp;
    }

    public IDamage getDamage() {
        return damage;
    }

    public void setDamage(IDamage damage) {
        this.damage = damage;
    }

    public Integer getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(Integer attackRange) {
        this.attackRange = attackRange;
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

    private static final Random random = MyRandom.getRandom();

    public static UnitType randomUnit() {
        UnitType[] units = values();
        return units[random.nextInt(units.length)];
    }

    public static void showUnitsShop() {
        String leftAlignment = "| %-14s | %-6s | %-6s | %-12s | %-7s | %-6s | %-4s |%n";
        System.out.format("+----------------+--------+--------+--------------+---------+--------+------+%n");
        System.out.format("|      Name      | Health | Attack | Attack Range | Defence | Energy | Cost |%n");
        System.out.format("+----------------+--------+--------+--------------+---------+--------+------+%n");
        for (UnitType unitType : UnitType.values()) {
            System.out.format(leftAlignment, unitType.name, unitType.hp, unitType.damage,
                    unitType.attackRange, unitType.defence, unitType.energy, unitType.cost);
        }
        System.out.format("+----------------+--------+--------+--------------+---------+--------+------+%n");
    }

}
