package main.Entities.Damage;

import static main.Utilities.Constants.Colors.ANSI_RESET;

public class Damage implements IDamage {
    private DamageType damageType;
    private Double value;

    public Damage(DamageType damageType, Double value) {
        this.damageType = damageType;
        this.value = value;
    }

    public DamageType getDamageType() {
        return damageType;
    }
    public String getColoredDamageType() {
        return damageType.getColor() + damageType + ANSI_RESET;
    }

    public Double getValue() {
        return value;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
