package playbook.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import playbook.MongoDB.MongoDB;
import playbook.MongoDB.documents.Category;
import playbook.MongoDB.documents.CorrectionsDocument;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static playbook.MongoDB.MongoCollectionName.CORRECTION_FILE;
import static playbook.MongoDB.documents.CorrectionsDocument.correctionsDocument;
import static playbook.MongoDB.documents.CreationDocument.creationDocument;

public class MainHttpHandler implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(MainHttpHandler.class);

    private final MongoDB mongoDB;

    public MainHttpHandler(MongoDB mongoDB) {
        this.mongoDB = mongoDB;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String urlPath = exchange.getRequestURI().getPath();
        String lowerCaseUrlPath = urlPath.toLowerCase();
        logger.info(String.format("[%s] %s", exchange.getRequestMethod(), urlPath));
        if (lowerCaseUrlPath.contains("create"))
            mongoDB.createCollectionIfNotExists(CORRECTION_FILE);
        else if (lowerCaseUrlPath.contains("delete"))
            mongoDB.deleteAllCollections();
        else if (lowerCaseUrlPath.contains("insert")) {
            CorrectionsDocument correctionsDocument = correctionsDocument(
                    creationDocument(new Date(), "louis"),
                    Category.AF_PARTNER,
                    UUID.randomUUID().toString(),
                    UUID.randomUUID() + ".csv",
                    "header1,header2,header3\r\nrow1field1,row1field2,row1field3\r\nrow2field1,row2field2,row2field3"
            );
            mongoDB.insertDocuments(CORRECTION_FILE, correctionsDocument);
        }
        else if (lowerCaseUrlPath.contains("read")) {
            List<Document> documents = mongoDB.readCollection(CORRECTION_FILE);
            logger.info("Results of read:");
            handleReadRequest(exchange, documents);
        }
        else if (lowerCaseUrlPath.contains("index")) {
            mongoDB.createIndex(CORRECTION_FILE);
        }
        else
            logger.info("Unmapped action to handle");
    }

    private void handleReadRequest(HttpExchange exchange, List<Document> documents) throws IOException {
        String responseString = String.format("[%s]", documents.stream().map(Document::toJson).collect(Collectors.joining(",")));
        OutputStream outputStream = exchange.getResponseBody();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseString.length());
        outputStream.write(responseString.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
