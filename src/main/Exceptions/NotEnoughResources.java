package main.Exceptions;

public class NotEnoughResources extends Exception{
    public NotEnoughResources(){
        super("Not enough resources to build house.");
    }
}
