package Exceptions;

public class AnotherEntityAtTheCeil extends Exception{
    public AnotherEntityAtTheCeil(){
        super("You tried to place a new unit on an already placed unit!");
    }
}
