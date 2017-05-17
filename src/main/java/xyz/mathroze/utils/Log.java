package xyz.mathroze.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.mathroze.alchemycraft.References;

/**
 * Created by caleb on 5/16/17.
 */
public class Log {

    public enum LogLevel {
        Release(1),
        Debug(2),
        Verbose(3);

        private int value;
        LogLevel(int value) {
            this.value = value;
        }
    }

    private static Logger logger = LogManager.getFormatterLogger(References.MOD_ID);
    private static LogLevel logLevel = References.LOG_LEVEL;

    public static void release(String text) {
        if (logLevel.value <= LogLevel.Release.value) {
            logger.info(text);
        }
    }

    public static void debug(String text) {
        if (logLevel.value <= LogLevel.Debug.value) {
            logger.info(text);
        }
    }

    public static void verbose(String text) {
        if (logLevel.value <= LogLevel.Verbose.value) {
            logger.info(text);
        }
    }

    public static void warn(String text) {
        logger.warn(text);
    }

    public static void error(String text) {
        logger.error(text);
    }
}
