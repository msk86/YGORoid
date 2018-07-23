package org.msk86.ygoroid.newutils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    public static String device() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        }
        return "/Device/";
    }

    public static String baseDir() {
        File mfl =new File(device() + "ygocore/");
        if(!mfl.exists())
            return device() + "YGORoid/";
        else
            return device() + "ygocore/";
    }

    public static String deckPath() {
        return baseDir() + "deck/";
    }

    public static String cardImgPath() {
        return baseDir() + "pics/";
    }

    public static String userDefinedCardImgPath() {
        return baseDir() + "userDefined/";
    }

    public static String texturePath() {
        return baseDir() + "texture/";
    }

    public static String cardImageSuffix() {
        return ".jpg";
    }

    public static String configPropertyFile() {
        return "config.properties";
    }

    public static String databaseName() {
        return "cards.cdb";
    }

    public static final Properties PROPERTIES = loadProperties();

    public static final String PROPERTY_GRAVITY_ENABLE = "GRAVITY";
    public static final String PROPERTY_AUTO_SHUFFLE_ENABLE = "AUTO_SHUFFLE";
    public static final String PROPERTY_AUTO_DB_UPGRADE = "AUTO_DB_UPGRADE";

    private static Properties defaultProperties() {
        Properties properties = new Properties();
        properties.setProperty(PROPERTY_GRAVITY_ENABLE, "1");
        properties.setProperty(PROPERTY_AUTO_SHUFFLE_ENABLE, "1");
        properties.setProperty(PROPERTY_AUTO_DB_UPGRADE, "1");
        return properties;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(
                    baseDir() + configPropertyFile()));

            Properties defaultProperties = defaultProperties();
            String[] pNames = {PROPERTY_GRAVITY_ENABLE, PROPERTY_AUTO_SHUFFLE_ENABLE, PROPERTY_AUTO_DB_UPGRADE};
            for (String name : pNames) {
                Object value = properties.get(name);
                if (value == null) {
                    properties.setProperty(name, (String) defaultProperties.get(name));
                }
            }
        } catch (IOException e) {
            properties = defaultProperties();
        }
        return properties;
    }

    public static boolean configProperties(String key) {
        Object value = PROPERTIES.get(key);
        return "1".equals(value) || "true".equals(value);
    }

    public static void configProperties(String key, boolean value) {
        PROPERTIES.setProperty(key, value ? "1" : "0");
    }

    public static void saveProperties() {
        try {
            PROPERTIES.save(new FileOutputStream(baseDir() + configPropertyFile()), "");
        } catch (FileNotFoundException e) {
        }
    }
}
