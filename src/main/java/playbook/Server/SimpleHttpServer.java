package playbook.Server;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import playbook.MongoDB.MongoDB;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SimpleHttpServer implements RequestServer {
    private static Logger logger = LoggerFactory.getLogger(RequestServer.class);

    private final RequestServerSettings config;
    private final MongoDB mongoDB;
    private HttpServer server;

    public SimpleHttpServer(RequestServerSettings config, MongoDB mongoDB) {
        this.config = config;
        this.mongoDB = mongoDB;
    }

    @Override
    public RequestServer start() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server = HttpServer.create(new InetSocketAddress(config.port), 0);
        server.createContext("/", new MainHttpHandler(mongoDB));
        server.setExecutor(threadPoolExecutor);
        server.start();
        logger.info("Server started on port " + config.port);
        return this;
    }

    @Override
    public void close() throws Exception {
        try {
            server.stop(0);
        } catch (Exception e) {
            logger.error("Caught exception while stopping Request Server", e);
        }
    }
}
