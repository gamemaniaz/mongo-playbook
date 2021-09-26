package playbook.MongoDB;

import org.bson.Document;
import playbook.Service;

import java.util.List;

public interface MongoDB extends Service<MongoDB>, AutoCloseable {

    void createCollectionIfNotExists(MongoCollectionName mongoCollectionName);

    List<Document> readCollection(MongoCollectionName mongoCollectionName);

    void deleteAllCollections();

    void insertDocuments(MongoCollectionName mongoCollectionName, Document document);

    void createIndex(MongoCollectionName mongoCollectionName);
}
