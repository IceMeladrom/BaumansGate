package main.Exceptions;

import main.Players.Player;

public class NotEnoughCoins extends Exception {
    public NotEnoughCoins(Player player, Double unitCost) {
        super("You do not have enough coins! You have " + player.getCoins() + " coins, but you need " + unitCost + " coins!");
    }
}
