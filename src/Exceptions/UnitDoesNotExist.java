package Exceptions;

public class UnitDoesNotExist extends Exception {
    public UnitDoesNotExist(String unitName) {
        super("Unit type " + unitName + " does not exist.");
    }
    public UnitDoesNotExist() {
        super("You tried to choose invalid unit.");
    }
}
