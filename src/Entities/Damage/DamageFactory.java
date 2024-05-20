package Entities.Damage;

public class DamageFactory {
    public static IDamage createDamage(DamageType damageType, Double value){
        return new Damage(damageType, value);
    }
}
