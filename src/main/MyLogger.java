package main;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MyLogger {
    private static final Logger logger = Logger.getLogger(MyLogger.class.getName());

    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().readConfiguration(MyLogger.class.getResourceAsStream("logging.properties"));

    }

    public Logger getLogger() {
        return logger;
    }

}
