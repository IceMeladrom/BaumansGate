package Entities.Units.Units;

import Entities.Damage.DamageFactory;
import Entities.Damage.DamageType;
import Entities.Damage.IDamage;
import Utilities.Constants.MyRandom;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

public enum UnitType {
    Swordsman("Swordsman", 50.0, DamageFactory.createDamage(DamageType.Physical, 5.0), 1, 8.0, 3.0, 10.0, "S"),

    Spearman("Spearman", 35.0, DamageFactory.createDamage(DamageType.Cold, 3.0), 1, 4.0, 6.0, 15.0, "P"),

    Axeman("Axeman", 45.0, DamageFactory.createDamage(DamageType.Cold, 9.0), 1, 3.0, 4.0, 20.0, "A"),

    ArcherLongBow("ArcherLongBow", 30.0, DamageFactory.createDamage(DamageType.Acid, 6.0), 5, 8.0, 2.0, 15.0, "L"),

    ArcherShortBow("ArcherShortBow", 25.0, DamageFactory.createDamage(DamageType.Acid, 3.0), 3, 4.0, 4.0, 19.0, "Q"),

    Crossbowman("Crossbowman", 40.0, DamageFactory.createDamage(DamageType.Fire, 7.0), 6, 3.0, 2.0, 23.0, "B"),

    Knight("Knight", 30.0, DamageFactory.createDamage(DamageType.Physical, 5.0), 1, 3.0, 6.0, 20.0, "K"),

    Cuirassier("Cuirassier", 50.0, DamageFactory.createDamage(DamageType.Cold, 2.0), 1, 7.0, 5.0, 23.0, "C"),

    HorseArcher("HorseArcher", 25.0, DamageFactory.createDamage(DamageType.Acid, 3.0), 3, 2.0, 5.0, 25.0, "H"),
    Mage("Mage", 15.0, DamageFactory.createDamage(DamageType.Fire, 9.0), 3, 4.0, 3.0, 30.0, "M"),
    GodMelee("GodMelee", 999.0, DamageFactory.createDamage(DamageType.Cold, 999.0), 1, 999.0, 999.0, 999.0, "G"),
    GodRange("GodRange", 999.0, DamageFactory.createDamage(DamageType.Acid, 999.0), 999, 999.0, 999.0, 999.0, "G");

    private final String name;
    private Double hp, defence, energy, cost;
    private IDamage damage;
    private Integer attackRange;
    private String symbol;

    UnitType(String name, Double hp, IDamage damage, Integer attackRange, Double defence, Double energy, Double cost, String symbol) {
        this.hp = hp;
        this.damage = damage;
        this.attackRange = attackRange;
        this.defence = defence;
        this.energy = energy;
        this.cost = cost;
        this.name = name;
        this.symbol = symbol;
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

    public String getSymbol() {
        return symbol;
    }

    private static final Random random = MyRandom.getRandom();

    public static UnitType randomUnit() {
        UnitType[] units = values();
        return units[random.nextInt(units.length)];
    }


    public static void showUnitsShop() {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat doubleFormat = new DecimalFormat("0.0", otherSymbols);

        String leftAlignment = "| %-16s | %-8s | %-8s | %-24s | %-14s | %-9s | %-8s | %-6s |%n";
        System.out.format("+------------------+----------+----------+---------------+----------------+-----------+----------+--------+%n");
        System.out.format("|       Name       |  Health  |  Damage  |  Damage Type  |  Attack Range  |  Defence  |  Energy  |  Cost  |%n");
        System.out.format("+------------------+----------+----------+---------------+----------------+-----------+----------+--------+%n");
        for (UnitType unitType : UnitType.values()) {
            System.out.format(leftAlignment,
                    unitType.getName(),
                    doubleFormat.format(unitType.getHp()),
                    doubleFormat.format(unitType.getDamage().getValue()),
                    unitType.getDamage().getColoredDamageType(),
                    unitType.getAttackRange(),
                    doubleFormat.format(unitType.getDefence()),
                    doubleFormat.format(unitType.getEnergy()),
                    doubleFormat.format(unitType.getCost()));
        }
        System.out.format("+------------------+----------+----------+---------------+----------------+-----------+----------+--------+%n");
    }

}
