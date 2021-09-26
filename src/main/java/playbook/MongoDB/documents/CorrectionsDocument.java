package playbook.MongoDB.documents;

import org.bson.Document;

public class CorrectionsDocument extends Document {
    public final CreationDocument creationDocument;
    public final Category category;
    public final String jobId;
    public final String fileName;
    public final String csvData;

    private CorrectionsDocument(CreationDocument creationDocument, Category category, String jobId, String fileName, String csvData) {
        this.creationDocument = creationDocument;
        this.category = category;
        this.jobId = jobId;
        this.fileName = fileName;
        this.csvData = csvData;
    }

    public static CorrectionsDocument correctionsDocument(CreationDocument creationDocument, Category category, String jobId, String fileName, String csvData) {
        CorrectionsDocument document = new CorrectionsDocument(creationDocument, category, jobId, fileName, csvData);
        document.put("creationDocument", document.creationDocument);
        document.put("category", document.category.value);
        document.put("jobId", document.jobId);
        document.put("fileName", document.fileName);
        document.put("csvData", document.csvData);
        return document;
    }
}
