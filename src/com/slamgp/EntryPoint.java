package com.slamgp;

import com.slamgp.service.CallActivityAnalyzer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class EntryPoint {
    private static Logger log = Logger.getLogger(EntryPoint.class.getName());
    private final static String APP_PROPERTY_FILE_NAME = "src//app.properties";
    private final static String USE_CUSTOM_LOGGER_PROPERTIES_NAME = "use.custom.logger.properties";
    private final static String DATA_SOURCE_FILE_NAME = "data.source.file";
    private final static String LOGING_PROPERTIES_FILE_NAME = "/logging.properties";

    public static void main(String[] args) {

        Properties property = loadProperty();
        if (property != null) {
            if (Boolean.valueOf(property.getProperty(USE_CUSTOM_LOGGER_PROPERTIES_NAME))) {
                loadLoggerConfig();
            }
            try {
                CallActivityAnalyzer analyzer = new CallActivityAnalyzer(property.getProperty(DATA_SOURCE_FILE_NAME));
                log.info("peack = " + analyzer.getPeak().toString());
            } catch (IOException e) {
                log.info(e.getClass() + ": " + e.getMessage());
            }
        }

    }


    private static Properties loadProperty() {
        Properties property = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(APP_PROPERTY_FILE_NAME);
        } catch (FileNotFoundException e) {
            log.info(e.getClass() + ": " + e.getMessage());
            return null;
        }
        try {
            property.load(fis);
        } catch (IOException e) {
            log.info(e.getClass() + ": " + e.getMessage());
            return null;
        }
        return property;
    }

    private static void loadLoggerConfig() {
        try {
            LogManager.getLogManager().readConfiguration(
                    EntryPoint.class.getResourceAsStream(LOGING_PROPERTIES_FILE_NAME));
        } catch (IOException e) {
            log.info(e.getClass() + ": " + e.getMessage());
        }
    }
}
