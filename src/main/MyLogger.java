package main;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MyLogger {
    private static final Logger logger = Logger.getLogger(MyLogger.class.getName());

    static {
        try {
            LogManager.getLogManager().readConfiguration(MyLogger.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {

    }

    public static Logger getLogger() {
        return logger;
    }

}
