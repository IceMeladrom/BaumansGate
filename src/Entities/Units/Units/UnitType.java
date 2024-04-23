package Entities.Units.Units;

import Entities.Damage.Damage;
import Entities.Damage.DamageFactory;
import Entities.Damage.DamageType;
import Entities.Damage.IDamage;
import Utilities.Constants.MyRandom;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import static Utilities.Constants.Colors.ANSI_RESET;

public enum UnitType {
    Swordsman("Swordsman", 50.0, DamageType.Physical, 5.0, 1, 8.0, 3.0, 10.0, "S", new HashMap<>() {{
        put("*", 1.0);
        put("#", 1.5);
        put("@", 2.0);
        put("!", 1.2);
    }}),

    Spearman("Spearman", 35.0, DamageType.Cold, 3.0, 1, 4.0, 6.0, 15.0, "P", new HashMap<>() {{
        put("*", 1.0);
        put("#", 1.5);
        put("@", 2.0);
        put("!", 1.2);
    }}),

    Axeman("Axeman", 45.0, DamageType.Cold, 9.0, 1, 3.0, 4.0, 20.0, "A", new HashMap<>() {{
        put("*", 1.0);
        put("#", 1.5);
        put("@", 2.0);
        put("!", 1.2);
    }}),

    ArcherLongBow("ArcherLongBow", 30.0, DamageType.Acid, 6.0, 5, 8.0, 2.0, 15.0, "L", new HashMap<>() {{
        put("*", 1.0);
        put("#", 1.8);
        put("@", 2.2);
        put("!", 1.0);
    }}),

    ArcherShortBow("ArcherShortBow", 25.0, DamageType.Acid, 3.0, 3, 4.0, 4.0, 19.0, "Q", new HashMap<>() {{
        put("*", 1.0);
        put("#", 1.8);
        put("@", 2.2);
        put("!", 1.0);
    }}),

    Crossbowman("Crossbowman", 40.0, DamageType.Fire, 7.0, 6, 3.0, 2.0, 23.0, "B", new HashMap<>() {{
        put("*", 1.0);
        put("#", 1.8);
        put("@", 2.2);
        put("!", 1.0);
    }}),

    Knight("Knight", 30.0, DamageType.Physical, 5.0, 1, 3.0, 6.0, 20.0, "K", new HashMap<>() {{
        put("*", 1.0);
        put("#", 2.2);
        put("@", 1.2);
        put("!", 1.5);
    }}),

    Cuirassier("Cuirassier", 50.0, DamageType.Cold, 2.0, 1, 7.0, 5.0, 23.0, "C", new HashMap<>() {{
        put("*", 1.0);
        put("#", 2.2);
        put("@", 1.2);
        put("!", 1.5);
    }}),

    HorseArcher("HorseArcher", 25.0, DamageType.Acid, 3.0, 3, 2.0, 5.0, 25.0, "H", new HashMap<>() {{
        put("*", 1.0);
        put("#", 2.2);
        put("@", 1.2);
        put("!", 1.5);
    }}),
    Mage("Mage", 15.0, DamageType.Fire, 9.0, 3, 4.0, 3.0, 30.0, "M", new HashMap<>() {{
        put("*", 1.5);
        put("#", 2.0);
        put("@", 2.5);
        put("!", 1.8);
    }}),
    GodMelee("GodMelee", 999.0, DamageType.Cold, 999.0, 1, 999.0, 999.0, 999.0, "G", new HashMap<>() {{
        put("*", 1.0);
        put("#", 1.5);
        put("@", 2.0);
        put("!", 1.2);
    }}),
    GodRange("GodRange", 999.0, DamageType.Acid, 999.0, 999, 999.0, 999.0, 999.0, "G", new HashMap<>() {{
        put("*", 1.0);
        put("#", 1.5);
        put("@", 2.0);
        put("!", 1.2);
    }});

    private final String name;
    private Double hp, defence, energy, cost;
//    private IDamage damage;

    private DamageType damageType;
    private Double damageValue;
    private Integer attackRange;
    private String symbol;
    private final HashMap<String, Double> terrains;

    UnitType(String name, Double hp, DamageType damageType, Double damageValue, Integer attackRange, Double defence, Double energy, Double cost, String symbol, HashMap<String, Double> terrains) {
        this.hp = hp;
        this.damageType = damageType;
        this.damageValue = damageValue;
        this.attackRange = attackRange;
        this.defence = defence;
        this.energy = energy;
        this.cost = cost;
        this.name = name;
        this.symbol = symbol;
        this.terrains = terrains;
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

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public Double getDamageValue() {
        return damageValue;
    }

    public void setDamageValue(Double damageValue) {
        this.damageValue = damageValue;
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

    public HashMap<String, Double> getTerrains() {
        return terrains;
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
                    doubleFormat.format(unitType.getDamageValue()),
                    unitType.getDamageType().getColor() + unitType.getDamageType() + ANSI_RESET,
                    unitType.getAttackRange(),
                    doubleFormat.format(unitType.getDefence()),
                    doubleFormat.format(unitType.getEnergy()),
                    doubleFormat.format(unitType.getCost()));
        }
        System.out.format("+------------------+----------+----------+---------------+----------------+-----------+----------+--------+%n");
    }

}
