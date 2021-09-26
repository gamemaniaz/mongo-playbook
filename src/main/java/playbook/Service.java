package playbook;

public interface Service<T extends AutoCloseable> {
    T start() throws Exception;
}
