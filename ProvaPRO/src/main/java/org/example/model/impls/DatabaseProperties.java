package org.example.model.impls;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseProperties {
    private Properties properties;

    public DatabaseProperties() {
        try (InputStream input = DatabaseProperties.class.getClassLoader().getResourceAsStream("database.properties")) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read database properties file.", e);
        }
    }

    public String getUrl() {
        return properties.getProperty("db.url");
    }

    public String getUser() {
        return properties.getProperty("db.user");
    }

    public String getPassword() {
        return properties.getProperty("db.password");
    }
}