package playbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class MainApplication implements Service<MainApplication>, AutoCloseable {
    private static Logger logger = LoggerFactory.getLogger(MainApplication.class);

    private final List<Service> services = new ArrayList<>();
    private final List<AutoCloseable> autoCloseables = new ArrayList<>();

    MainApplication(Service... services) {
        this.services.addAll(asList(services));
    }

    @Override
    public MainApplication start() throws Exception {
        for (Service service : services) {
            autoCloseables.add(service.start());
        }
        return this;
    }

    @Override
    public void close() {
        for (AutoCloseable autoCloseable : autoCloseables) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                logger.error("Caught exception while closing " + autoCloseable.getClass().getName(), e);
            }
        }
    }
}
