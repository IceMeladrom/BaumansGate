package main.Utilities.Constants;

import java.util.Random;

public class MyRandom {
    private static final Random random = new Random();

    public static Random getRandom() {
        return random;
    }
}
