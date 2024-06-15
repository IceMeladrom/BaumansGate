package main.Exceptions;

public class InvalidOption extends Exception{
    public InvalidOption(){
        super("You tried to choose a non-existent option.");
    }
}
