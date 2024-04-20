package Exceptions;

public class CantBuildOrUpgradeHouse extends Exception{
    public CantBuildOrUpgradeHouse(){
        super("Town has max number of this building or its has max level.");
    }
}
