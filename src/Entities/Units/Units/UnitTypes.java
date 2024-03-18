package Entities.Units.Units;

import java.util.Random;

public enum UnitTypes {
    Swordsman("Swordsman", 50, 5, 1, 8, 3, 10),

    Spearman("Spearman", 35, 3, 1, 4, 6, 15),

    Axeman("Axeman", 45, 9, 1, 3, 4, 20),

    ArcherLongBow("ArcherLongBow", 30, 6, 5, 8, 2, 15),

    ArcherShortBow("ArcherShortBow", 25, 3, 3, 4, 4, 19),

    Crossbowman("Crossbowman", 40, 7, 6, 3, 2, 23),

    Knight("Knight", 30, 5, 1, 3, 6, 20),

    Cuirassier("Cuirassier", 50, 2, 1, 7, 5, 23),

    HorseArcher("HorseArcher", 25, 3, 3, 2, 5, 25),
    GodMelee ("GodMelee", 999, 999, 1, 999, 999, 999),
    GodRange ("GodRange", 999, 999, 999, 999, 999, 999);

    public final String name;
    public final int hp, damage, attackRange, defence, cost;
    public final float energy;
    UnitTypes(String name, int hp, int damage, int attackRange, int defence, float energy, int cost) {
        this.hp = hp;
        this.damage = damage;
        this.attackRange = attackRange;
        this.defence = defence;
        this.energy = energy;
        this.cost = cost;
        this.name = name;
    }

    private static final Random random = new Random();

    public static UnitTypes randomUnit()  {
        UnitTypes[] units = values();
        return units[random.nextInt(units.length)];
    }

}
