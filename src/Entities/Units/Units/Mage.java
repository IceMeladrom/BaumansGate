package Entities.Units.Units;

import Entities.Damage.DamageType;
import Entities.Damage.IDamage;
import Exceptions.UnitHasAlreadyAttacked;
import Exceptions.UnitHasNotPreparedAnAttack;
import Players.Player;
import Players.Players.RealPlayer;
import org.jetbrains.annotations.NotNull;

import static Utilities.Constants.Colors.ANSI_RESET;

public class Mage extends Unit implements IMage {
    public Mage(String name, int hp, IDamage damage, int attackRange, int defence, double energy, int cost, String symbol, int row, int col, Player player) {
        super(name, hp, damage, attackRange, defence, energy, cost, symbol, row, col, player);
        getTerrains().put("*", 1F);
        getTerrains().put("#", 1.5F);
        getTerrains().put("@", 2F);
        getTerrains().put("!", 1.2F);
        setMovesUntilReadyToAttack(-1);
    }

    @Override
    public void attack(@NotNull IUnit enemy) throws UnitHasAlreadyAttacked, UnitHasNotPreparedAnAttack {
        if (!getIsAttackPrepared())
            throw new UnitHasNotPreparedAnAttack(this);


        if (!getDidAttack()) {
            float multiplier = DamageType.attackMultiplier(getDamage().getDamageType(), enemy.getDamage().getDamageType());
            if (enemy.getDefence() >= getDamage().getValue() * multiplier)
                enemy.setDefence(enemy.getDefence() - (int) (getDamage().getValue() * multiplier));
            else {
                enemy.setHp(enemy.getHp() - ((int) (getDamage().getValue() * multiplier) - enemy.getDefence()));
                enemy.setDefence(0);
            }
            setDidAttack(true);
            setAttackPrepared(false);
            RealPlayer.log("Unit " + this.getPlayer().getColor() + this.getName() + ANSI_RESET
                    + "(row: " + (getRow() + 1) + ", col: " + (getCol() + 1) + ") has attacked unit " + enemy.getPlayer().getColor() + enemy.getName() + ANSI_RESET
                    + "(row: " + (enemy.getRow() + 1) + ", col: " + (enemy.getCol() + 1) + ") and has dealt " +
                    getDamage().getValue() * DamageType.attackMultiplier(getDamage().getDamageType(), enemy.getDamage().getDamageType()) + " " + getDamage().getDamageType() + " damage");
        } else {
            throw new UnitHasAlreadyAttacked(this);
        }
    }

    @Override
    public void prepareAttack() {
        setMovesUntilReadyToAttack(2);
    }
}
