package com.gymapp.helpers;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

public class PropertiesHelper {

    public static void loadPropertiesFromFile(Properties prop , String configFileString) {
        try (FileInputStream fis = new FileInputStream(configFileString)) {
            prop.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateDBPath(Properties prop, File DBFile, String configFileString) {
        if (prop.getProperty("db.path") == null ||
            prop.getProperty("db.path").equals("") ||
            !prop.getProperty("db.path").equals(DBFile.getPath())) 
        {
            prop.setProperty("db.path", DBFile.getPath());
            try (FileOutputStream fos = new FileOutputStream(configFileString)) {
                prop.store(fos, "Updated DB Path");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteDBPath(Properties prop, String configFileString) {
        prop.setProperty("db.path", "");
        try (FileOutputStream fos = new FileOutputStream(configFileString)) {
            prop.store(fos, "Deleted DB Path");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
