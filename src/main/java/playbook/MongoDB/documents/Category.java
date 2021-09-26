package playbook.MongoDB.documents;

public enum Category {
    AF_PARTNER("AEI_FATCA", "PARTNER"),
    AF_RAW_PAYMENTS("AEI_FATCA", "RAW_PAYMENTS");

    public final String module;
    public final String value;

    Category(String module, String value) {
        this.module = module;
        this.value = value;
    }
}
