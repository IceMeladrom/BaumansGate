package Entities.Damage;

import static Utilities.Constants.Colors.*;

public enum DamageType {
    Physical(ANSI_BLACK_BRIGHT), Fire(ANSI_RED_BOLD), Cold(ANSI_CYAN_BOLD), Acid(ANSI_YELLOW_BOLD);

    private final String color;
    DamageType(String color){
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public static float attackMultiplier(DamageType myDamageType, DamageType enemyDamageType) {
        float koef = 1.0F;
        if (myDamageType == enemyDamageType)
            return 0.5F;

        switch (myDamageType) {
            case Physical -> koef = 1.0F;
            case Fire -> {
                switch (enemyDamageType) {
                    case Physical -> koef = 0.8F;
                    case Cold -> koef = 2.0F;
                    case Acid -> koef = 1.1F;
                }
            }
            case Cold -> {
                switch (enemyDamageType) {
                    case Physical -> koef = 0.8F;
                    case Fire -> koef = 1.5F;
                    case Acid -> koef = 0.9F;
                }
            }
            case Acid -> {
                switch (enemyDamageType) {
                    case Physical -> koef = 0.8F;
                    case Fire -> koef = 1.3F;
                    case Cold -> koef = 0.9F;
                }
            }

        }

        return koef;
    }

    public static boolean contains(String test) {
        for (DamageType c : DamageType.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}
