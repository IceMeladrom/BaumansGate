package main.Entities.Builds;

import main.Entities.Damage.Damage;
import main.Entities.Damage.DamageType;
import main.Entities.Damage.IDamage;
import main.Entities.Units.Units.IUnit;
import main.Entities.Units.Units.Mage;
import main.Exceptions.MageAlreadyHasMinPreparationTime;
import main.Exceptions.NotEnoughCoins;
import main.Menu.Menu;
import main.Players.Player;
import main.Players.Players.RealPlayer;
import main.Utilities.Constants.MyRandom;
import main.Utilities.Constants.MyScanner;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static main.Utilities.Constants.Colors.*;

public class Tower extends House implements IBuilding {
    public Tower() {
        super(Buildings.Tower);
    }

    @Override
    public void buff(Player player) throws MageAlreadyHasMinPreparationTime, NotEnoughCoins {
        ArrayList<IUnit> mages = new ArrayList<>();
        for (int i = 0; i < player.getUnits().size(); i++) {
            IUnit unit = player.getUnits().get(i);
            if (unit.getClass().equals(Mage.class))
                mages.add(unit);
        }
        if (mages.isEmpty())
            return;
        for (int i = 0; i < mages.size(); i++) {
            System.out.println((i + 1) + ". " + mages.get(i).getName() + "(" + mages.get(i).getDamage().getDamageType().getColor() + mages.get(i).getDamage().getDamageType() + ANSI_RESET + " row: " + (mages.get(i).getRow() + 1) + " col: " + (mages.get(i).getCol() + 1) + ")");
        }
        int option = 0;
        while (true) {
            System.out.print("Select mage for buff: ");
            try {
                option = Integer.parseInt(MyScanner.getScanner().nextLine());
                if (option > mages.size() || option <= 0) {
                    System.out.println(ANSI_RED + "Invalid option!" + ANSI_RESET);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Invalid option!" + ANSI_RESET);
            }
        }
        IUnit mage = mages.get(option - 1);
        boolean end = false;
        while (!end) {
            System.out.format("Welcome to the Tower!\nSelect option:\n\t1. Change damage type\n\t2. Reduce spell preparation time\n\t3. Buy a teleportation potion (%s50%% change to destroy the Tower%s)\n\t4. Exit\n", ANSI_RED, ANSI_RESET);
            System.out.print("I choose option: ");
            String cmd = MyScanner.getScanner().nextLine();
            switch (cmd) {
                case "1" -> {
                    String dmgType = "";
                    for (DamageType dmg : DamageType.values()) {
                        System.out.println("\t" + dmg.getColor() + dmg + ANSI_RESET);
                    }
                    while (true) {
                        System.out.print("Enter new damage type: ");
                        dmgType = MyScanner.getScanner().nextLine();
                        if (DamageType.contains(dmgType)) {
                            mage.setDamage(new Damage(DamageType.valueOf(dmgType), mage.getDamage().getValue()));
                            break;
                        }
                        System.out.println(ANSI_RED + "Invalid option" + ANSI_RESET);
                    }
                    end = true;
                }
                case "2" -> {
                    while (true) {
                        System.out.println("100 coins to reduce the spell preparation time by 1 move\n\t1. Reduce\n\t2. Exit");
                        System.out.print("Enter the option: ");
                        cmd = MyScanner.getScanner().nextLine();
                        if (cmd.equals("1")) {
                            if (player.getCoins() >= 100.0) {
                                player.spendCoins(100.0);
                                if (mage.getMovesToPrepareAnAttack() > 0) {
                                    mage.setMovesToPrepareAnAttack(mage.getMovesToPrepareAnAttack() - 1);
                                    break;
                                } else
                                    throw new MageAlreadyHasMinPreparationTime();
                            } else
                                throw new NotEnoughCoins(player, 100.0);
                        } else if (cmd.equals("2"))
                            break;
                        else
                            System.out.println(ANSI_RED + "Invalid option" + ANSI_RESET);
                    }
                    end = true;
                }
                case "3" -> {
                    while (true) {
                        System.out.println("5 coins to buy the spell\n\t1. Buy\n\t2. Exit");
                        System.out.print("Enter the option: ");
                        cmd = MyScanner.getScanner().nextLine();
                        if (cmd.equals("1")) {
                            if (MyRandom.getRandom().nextInt(0, 2) == 0) {
                                if (player.getCoins() >= 5.0) {
                                    player.spendCoins(5.0);
                                    ((Mage) mage).addSpell();
                                } else
                                    throw new NotEnoughCoins(player, 5.0);
                                System.out.println(ANSI_GREEN + "You bought the spell" + ANSI_RESET);
                            } else {
                                Menu.log(ANSI_RED + "Tower exploded" + ANSI_RESET);
                                player.getTown().getBuildings().get(Buildings.Tower).clear();
                                return;
                            }
                        } else if (cmd.equals("2"))
                            break;
                        else
                            System.out.println(ANSI_RED + "Invalid option" + ANSI_RESET);

                    }
                    end = true;
                }
                case "4" -> {
                    System.out.println(ANSI_RED + "Invalid option" + ANSI_RESET);
                }
            }
        }
    }
}
