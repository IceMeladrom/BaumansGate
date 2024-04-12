package Entities.Units.Units;

import Entities.Builds.Town;
import Entities.Damage.DamageType;
import Entities.Damage.IDamage;
import Exceptions.AlliedUnitAtTheCeil;
import Exceptions.NotEnoughEnergy;
import Exceptions.NotYourTown;
import Exceptions.UnitHasAlreadyAttacked;
import Grid.Grid;
import Players.Player;
import Players.Players.RealPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static Utilities.Constants.Colors.ANSI_RESET;

public class Archer extends Unit implements IArcher {

    public Archer(String name, int hp, IDamage damage, int attackRange, int defence, double energy, int cost, String symbol, int row, int col, Player player) {
        super(name, hp, damage, attackRange, defence, energy, cost, symbol, row, col, player);
        getTerrains().put("*", 1F);
        getTerrains().put("#", 1.8F);
        getTerrains().put("@", 2.2F);
        getTerrains().put("!", 1F);
    }

    @Override
    public void attack(@NotNull IUnit enemy) throws UnitHasAlreadyAttacked {
        if (!getDidAttack()) {
            float multiplier = DamageType.attackMultiplier(getDamage().getDamageType(), enemy.getDamage().getDamageType());
            if (enemy.getDefence() >= getDamage().getValue() * multiplier)
                enemy.setDefence(enemy.getDefence() - (int) (getDamage().getValue() * multiplier));
            else {
                enemy.setHp(enemy.getHp() - ((int) (getDamage().getValue() * multiplier) - enemy.getDefence()));
                enemy.setDefence(0);
                RealPlayer.log("Unit " + this.getPlayer().getColor() + this.getName() + ANSI_RESET
                        + "(row: " + (getRow() + 1) + ", col: " + (getCol() + 1) + ") has attacked unit " + enemy.getPlayer().getColor() + enemy.getName() + ANSI_RESET
                        + "(row: " + (enemy.getRow() + 1) + ", col: " + (enemy.getCol() + 1) + ") and has dealt " +
                        getDamage().getValue()*DamageType.attackMultiplier(getDamage().getDamageType(), enemy.getDamage().getDamageType()) + " " + getDamage().getDamageType() + " damage");
            }
            setDidAttack(true);

        } else {
            throw new UnitHasAlreadyAttacked(this);
        }
    }
}
