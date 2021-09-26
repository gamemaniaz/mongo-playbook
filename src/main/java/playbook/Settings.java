package playbook;

import playbook.MongoDB.MongoSettings;
import playbook.Server.RequestServerSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    public final RequestServerSettings requestServerSettings;
    public final MongoSettings mongoSettings;

    private Settings(RequestServerSettings requestServerSettings, MongoSettings mongoSettings) {
        this.requestServerSettings = requestServerSettings;
        this.mongoSettings = mongoSettings;
    }

    public static Settings build() throws IOException {
        String appConfigPath = Settings.class.getResource("/app.properties").getPath();
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        RequestServerSettings requestServerSettings = RequestServerSettings.requestServerSettings(appProps);
        MongoSettings mongoSettings = MongoSettings.mongoSettings(appProps);

        return new Settings(requestServerSettings, mongoSettings);
    }
}
