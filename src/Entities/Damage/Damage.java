package Entities.Damage;

public class Damage implements IDamage {
    private DamageType damageType;
    private int value;

    public Damage(DamageType damageType, int value) {
        this.damageType = damageType;
        this.value = value;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public int getValue() {
        return value;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
