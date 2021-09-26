package playbook.Server;

import java.util.Properties;

public class RequestServerSettings {
    public final int port;

    private RequestServerSettings(int port) {
        this.port = port;
    }

    public static RequestServerSettings requestServerSettings(Properties properties) {
        return new RequestServerSettings(Integer.parseInt(properties.getProperty("server-port")));
    }
}
