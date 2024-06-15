package main.Exceptions;

public class MageAlreadyHasMinPreparationTime extends Exception{
    public MageAlreadyHasMinPreparationTime(){
        super("The mage already has minimum preparation time");
    }
}
