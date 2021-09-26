package playbook.MongoDB;

public enum MongoCollectionName {
    CORRECTION_FILE("CORRECTIONS");

    public final String value;

    MongoCollectionName(String value) {
        this.value = value;
    }
}
