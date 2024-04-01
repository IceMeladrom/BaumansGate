package Entities.Damage;

public enum DamageType {
    Physical, Fire, Cold, Acid;

    public static float attackMultiplier(DamageType myDamageType, DamageType enemyDamageType) {
        float koef = 1.0F;
        if (myDamageType == enemyDamageType)
            return koef;

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
}
