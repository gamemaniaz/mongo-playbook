package playbook.MongoDB.documents;

import org.bson.Document;

import java.util.Date;

public class ApprovalDocument extends Document {
    public final Date approvedAt;
    public final ApprovalStatus approvalStatus;
    public final String approvedBy;

    private ApprovalDocument(Date approvedAt, ApprovalStatus approvalStatus, String approvedBy) {
        this.approvedAt = approvedAt;
        this.approvalStatus = approvalStatus;
        this.approvedBy = approvedBy;
    }

    public static ApprovalDocument approvalDocument(Date approvedAt, ApprovalStatus approvalStatus, String approvedBy) {
        ApprovalDocument document = new ApprovalDocument(approvedAt, approvalStatus, approvedBy);
        document.put("approvedAt", approvedAt);
        document.put("approvalStatus", approvalStatus);
        document.put("approvedBy", approvedBy);
        return document;
    }
}
