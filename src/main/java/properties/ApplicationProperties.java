package properties;

import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    private String path;
    private String databaseName;
    private String databaseHost;
    private String databasePort;
    private String databaseUser;
    private String databasePassword;

    public ApplicationProperties() {
        Properties properties = new Properties();
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(input);
            path = properties.getProperty("file.path");
            databaseName = properties.getProperty("database.name");
            databaseHost = properties.getProperty("database.host");
            databasePort = properties.getProperty("database.port");
            databaseUser = properties.getProperty("database.user");
            databasePassword = properties.getProperty("database.password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        return path;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }
}
