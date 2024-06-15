package main.Entities.Units.Units;

import main.Entities.Damage.DamageType;
import main.Entities.Damage.IDamage;
import main.Exceptions.NotEnoughRangeAttack;
import main.Exceptions.UnitHasAlreadyAttacked;
import main.Players.Player;
import main.Players.Players.RealPlayer;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static main.Utilities.Constants.Colors.ANSI_RESET;

public class Warrior extends Unit implements IWarrior {

    public Warrior(String name, Double hp, IDamage damage, Integer attackRange, Double defence, Double energy, Double cost, String symbol, int row, int col, Player player) {
        super(name, hp, damage, attackRange, defence, energy, cost, symbol, row, col, player);
        getTerrains().putAll(UnitType.Swordsman.getTerrains());
    }

    @Override
    public void attack(IUnit enemy) throws UnitHasAlreadyAttacked, NotEnoughRangeAttack {
        int myRow = getRow(), myCol = getCol();
        int enemyRow = enemy.getRow(), enemyCol = enemy.getCol();
        if(Math.abs(myRow - enemyRow) + Math.abs(myCol - enemyCol) > getAttackRange())
            throw new NotEnoughRangeAttack(myRow, myCol);

        if (!getDidAttack()) {
            float multiplier = DamageType.attackMultiplier(getDamage().getDamageType(), enemy.getDamage().getDamageType());
            if (enemy.getDefence() >= getDamage().getValue() * multiplier)
                enemy.setDefence(enemy.getDefence() - (getDamage().getValue() * multiplier));
            else {
                enemy.setHp(enemy.getHp() - ((getDamage().getValue() * multiplier) - enemy.getDefence()));
                enemy.setDefence(0.0);
            }
            setDidAttack(true);
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
            otherSymbols.setDecimalSeparator('.');
            otherSymbols.setGroupingSeparator('.');
            DecimalFormat doubleFormat = new DecimalFormat("0.0", otherSymbols);
            RealPlayer.log("Unit " + this.getPlayer().getColor() + this.getName() + ANSI_RESET
                    + "(row: " + (getRow() + 1) + ", col: " + (getCol() + 1) + ") has attacked unit " + enemy.getPlayer().getColor() + enemy.getName() + ANSI_RESET
                    + "(row: " + (enemy.getRow() + 1) + ", col: " + (enemy.getCol() + 1) + ") and has dealt " +
                    doubleFormat.format(getDamage().getValue() * DamageType.attackMultiplier(getDamage().getDamageType(), enemy.getDamage().getDamageType())) + " " + getDamage().getColoredDamageType() + " damage");
        } else {
            throw new UnitHasAlreadyAttacked(this);
        }
    }
}
