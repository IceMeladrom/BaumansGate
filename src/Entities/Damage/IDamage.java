package Entities.Damage;

public interface IDamage {
    public DamageType getDamageType();

    public int getValue();

    public void setDamageType(DamageType damageType);

    public void setValue(int value);
}
