package me.mathsanalysis.lifesteal.utils;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;

@UtilityClass
public class ConsoleUtils {

    public void sendLoggerMessage(String message, Logger logger) {
        logger.info(message);
    }
}
