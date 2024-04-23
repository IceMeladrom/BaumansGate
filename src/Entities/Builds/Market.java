package Entities.Builds;

import Players.Player;
import Utilities.Constants.MyRandom;
import Utilities.Constants.MyScanner;

import java.util.Random;
import java.util.Scanner;

import static Utilities.Constants.Colors.*;

public class Market extends House implements IBuilding, IMarket {
    private Integer wood, stone;

    public Market() {
        super(Buildings.Market);
    }

    public void update() {
        Random random = MyRandom.getRandom();
        wood = random.nextInt(0, 10);
        stone = random.nextInt(0, 10);
    }

    @Override
    public void buff(Player player) {
        update();
        System.out.format("%sWelcome to the market!%s%nToday's assortment:%n\t1. Wood: %s%n\t2. Stone: %s%n", ANSI_CYAN, ANSI_RESET, ANSI_GREEN + wood + ANSI_RESET, ANSI_GREEN + stone + ANSI_RESET);

        Scanner scanner = MyScanner.getScanner();
        boolean exit = false;
        String err = "";

        while (exit) {
            if (!err.isEmpty()) {
                System.out.printf("%s%s%s%n", ANSI_RED, err, ANSI_RESET);
                err = "";
            }
            System.out.format("Enter the number of material or type %sExit%s: ", ANSI_RED, ANSI_RESET);
            String option = scanner.nextLine();
            int amount = 0;
            switch (option) {
                case "1" -> {
                    System.out.print("Enter how much wood you want to buy: ");
                    try {
                        amount = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        err = "Invalid amount.";
                        break;
                    }
                    if (amount <= 0) {
                        err = "You wanted to buy a negative number of wood.";
                        break;
                    }
                    player.setWood(player.getWood() + amount);
                    System.out.printf("You bought %s wood", amount);
                }
                case "2" -> {
                    System.out.print("Enter how much stone you want to buy: ");
                    try {
                        amount = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        err = "Invalid amount.";
                        break;
                    }
                    if (amount <= 0) {
                        err = "You wanted to buy a negative number of wood.";
                        break;
                    }
                    player.setStone(player.getStone() + amount);
                    System.out.printf("You bought %s stone", amount);
                }
                case "Exit" -> {
                    exit = true;
                }
                default -> {
                    System.out.format("%sInvalid option%s%n", ANSI_RED, ANSI_RESET);
                }
            }
        }
    }
}
