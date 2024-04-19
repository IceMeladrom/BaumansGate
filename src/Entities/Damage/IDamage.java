package Entities.Damage;

public interface IDamage {
    public DamageType getDamageType();

    public Double getValue();

    public void setDamageType(DamageType damageType);

    public void setValue(Double value);
}
