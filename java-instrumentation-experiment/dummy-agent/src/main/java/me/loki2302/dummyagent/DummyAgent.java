package me.loki2302.dummyagent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class DummyAgent {
    private final static String LOG_NAME = "DUMMY";
    private final List<String> logRecords = new ArrayList<>();

    public void deploy() {
        Logger logger = Logger.getLogger(LOG_NAME);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(new Handler() {
            @Override
            public void publish(LogRecord logRecord) {
                logRecords.add(logRecord.getMessage());
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() throws SecurityException {
                // https://github.com/jacoco/jacoco/blob/master/org.jacoco.core/src/org/jacoco/core/runtime/LoggerRuntime.java#L181
                logger.addHandler(this);
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.printf("Got %d log records\n", logRecords.size());
                for(String logRecord : logRecords) {
                    System.out.printf("%s\n", logRecord);
                }
            }
        });
    }

    public String getLogBeforeCode(String methodName) {
        String logBefore = String.format(
                "java.util.logging.Logger.getLogger(\"%s\").info(\"BEFORE %s\");",
                LOG_NAME,
                methodName);
        return logBefore;
    }

    public String getLogAfterCode(String methodName) {
        String logAfter = String.format(
                "java.util.logging.Logger.getLogger(\"%s\").info(\"AFTER %s\");",
                LOG_NAME,
                methodName);
        return logAfter;
    }
}
