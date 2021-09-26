package playbook.MongoDB.documents;

import org.bson.Document;

import java.util.Date;

public class CreationDocument extends Document {
    public final Date createdAt;
    public final String createdBy;

    private CreationDocument(Date createdAt, String createdBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public static CreationDocument creationDocument(Date createdAt, String createdBy) {
        CreationDocument document = new CreationDocument(createdAt, createdBy);
        document.put("createdAt", document.createdAt);
        document.put("createdBy", document.createdBy);
        return document;
    }
}
