package playbook.MongoDB;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class RealMongoDB implements MongoDB {
    private static final Logger logger = LoggerFactory.getLogger(RealMongoDB.class);

    private final MongoSettings config;
    private MongoClient mongoClient;

    public RealMongoDB(MongoSettings config) {
        this.config = config;
    }

    @Override
    public void createCollectionIfNotExists(MongoCollectionName mongoCollectionName) {
        try {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(config.databaseName);
            logger.info("Creating collection " + mongoCollectionName.value);
            if (newArrayList(mongoDatabase.listCollectionNames()).contains(mongoCollectionName.value)) {
                logger.info("Collection " + mongoCollectionName.value + " exists");
            } else {
                mongoDatabase.createCollection(mongoCollectionName.value);
                logger.info("Created collection " + mongoCollectionName.value);
            }
        } catch (Exception e) {
            logger.error("Caught exception while creating collection", e);
        }
    }

    @Override
    public List<Document> readCollection(MongoCollectionName mongoCollectionName) {
        try {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(config.databaseName);
            logger.info("Reading collection " + mongoCollectionName.value);
            MongoCollection<Document> collection = mongoDatabase.getCollection(mongoCollectionName.value);
            return newArrayList(collection.find());
        } catch (Exception e) {
            logger.error("Caught exception while reading collection", e);
            return newArrayList();
        }
    }

    @Override
    public void deleteAllCollections() {
        try {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(config.databaseName);
            logger.info("Deleting all collections");
            for (String collectionName : mongoDatabase.listCollectionNames()) {
                mongoDatabase.getCollection(collectionName).drop();
                logger.info("Deleted " + collectionName);
            }
            logger.info("Current list of collections : " + String.join(", ", mongoDatabase.listCollectionNames()));
        } catch (Exception e) {
            logger.error("Caught exception while deleting all collections", e);
        }
    }

    @Override
    public void insertDocuments(MongoCollectionName mongoCollectionName, Document document) {
        try {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(config.databaseName);
            logger.info("Inserting documents");
            MongoCollection<Document> collection = mongoDatabase.getCollection(mongoCollectionName.value);
            collection.insertOne(document);
            logger.info("Inserted documents");
        } catch (Exception e) {
            logger.error("Caught exception while inserting documents", e);
        }
    }

    @Override
    public void createIndex(MongoCollectionName mongoCollectionName) {
        try {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(config.databaseName);
            logger.info("Creating index for " + mongoCollectionName.value);
            MongoCollection<Document> collection = mongoDatabase.getCollection(mongoCollectionName.value);
            collection.createIndex(Indexes.ascending("jobId"));
            logger.info("Created index for " + mongoCollectionName.value);
        } catch (Exception e) {
            logger.error("Caught exception while creating index", e);
        }
    }

    @Override
    public MongoDB start() {
        ConnectionString connectionString = new ConnectionString(config.connectionString);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        mongoClient = MongoClients.create(settings);
        logger.info("Started MongoDB");
        return this;
    }

    @Override
    public void close() {
        try {
            mongoClient.close();
        } catch (Exception e) {
            logger.error("Caught exception while closing MongoDB client", e);
        }
    }
}
