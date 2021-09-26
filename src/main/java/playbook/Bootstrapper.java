package playbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import playbook.MongoDB.MongoDB;
import playbook.MongoDB.RealMongoDB;
import playbook.Server.RequestServer;
import playbook.Server.SimpleHttpServer;

import java.util.TimeZone;

public class Bootstrapper {
    private static Logger logger = LoggerFactory.getLogger(Bootstrapper.class);

    public static void main(String... args) throws Exception {
        logger.info("Initialising application...");
        Settings settings = Settings.build();

        MongoDB mongoDB = new RealMongoDB(settings.mongoSettings);
        RequestServer server = new SimpleHttpServer(settings.requestServerSettings, mongoDB);

        try {
            MainApplication application = new MainApplication(server, mongoDB);
            application.start();
        } catch (Exception e) {
            logger.error("Caught exception while starting application", e);
            System.exit(-1);
        }
    }
}
