package playbook.MongoDB;

import java.util.Properties;

public class MongoSettings {
    public final String connUrl;
    public final String username;
    public final String password;
    public final String databaseName;
    public final String connectionString;

    private MongoSettings(String connUrl, String username, String password, String databaseName) {
        this.connUrl = connUrl;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.connectionString = String.format(connUrl, username, password);
    }

    public static MongoSettings mongoSettings(Properties appProps) {
        return new MongoSettings(
                appProps.getProperty("mongo-connection-url"),
                appProps.getProperty("mongo-username"),
                appProps.getProperty("mongo-password"),
                appProps.getProperty("mongo-database")
        );
    }
}
