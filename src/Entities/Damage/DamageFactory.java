package Entities.Damage;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class DamageFactory {
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull IDamage createDamage(DamageType damageType, int value){
        return new Damage(damageType, value);
    }
}
