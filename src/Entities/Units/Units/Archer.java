package Entities.Units.Units;

import Entities.Damage.DamageType;
import Entities.Damage.IDamage;
import Exceptions.UnitHasAlreadyAttacked;
import Players.Player;
import Players.Players.RealPlayer;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static Utilities.Constants.Colors.ANSI_RESET;

public class Archer extends Unit implements IArcher {

    public Archer(String name, Double hp, IDamage damage, Integer attackRange, Double defence, Double energy, Double cost, String symbol, int row, int col, Player player) {
        super(name, hp, damage, attackRange, defence, energy, cost, symbol, row, col, player);
        getTerrains().putAll(UnitType.ArcherShortBow.getTerrains());
//        getTerrains().put("*", 1.0);
//        getTerrains().put("#", 1.8);
//        getTerrains().put("@", 2.2);
//        getTerrains().put("!", 1.0);
    }

    @Override
    public void attack(@NotNull IUnit enemy) throws UnitHasAlreadyAttacked {
        if (!getDidAttack()) {
            float multiplier = DamageType.attackMultiplier(getDamage().getDamageType(), enemy.getDamage().getDamageType());
            if (enemy.getDefence() >= getDamage().getValue() * multiplier)
                enemy.setDefence(enemy.getDefence() - (getDamage().getValue() * multiplier));
            else {
                enemy.setHp(enemy.getHp() - ((getDamage().getValue() * multiplier) - enemy.getDefence()));
                enemy.setDefence(0.0);
            }
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
            otherSymbols.setDecimalSeparator('.');
            otherSymbols.setGroupingSeparator('.');
            DecimalFormat doubleFormat = new DecimalFormat("0.0", otherSymbols);
            RealPlayer.log("Unit " + this.getPlayer().getColor() + this.getName() + ANSI_RESET
                    + "(row: " + (getRow() + 1) + ", col: " + (getCol() + 1) + ") has attacked unit " + enemy.getPlayer().getColor() + enemy.getName() + ANSI_RESET
                    + "(row: " + (enemy.getRow() + 1) + ", col: " + (enemy.getCol() + 1) + ") and has dealt " +
                    doubleFormat.format(getDamage().getValue() * DamageType.attackMultiplier(getDamage().getDamageType(), enemy.getDamage().getDamageType())) + " " + getDamage().getColoredDamageType() + " damage");
            setDidAttack(true);

        } else {
            throw new UnitHasAlreadyAttacked(this);
        }
    }
}
