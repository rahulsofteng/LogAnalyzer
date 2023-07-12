import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

public class Main {

    public static void main(String[] args) throws Exception {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");  // Replace with your MongoDB server IP and port
        MongoDatabase database = mongoClient.getDatabase("db");  // Replace with your database name
        MongoCollection<Document> collection = database.getCollection("log_analyzer");  // Replace with your collection name

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/tr_gw.log")); // Replace with your file path
        String line = reader.readLine();
        while (line != null) {
            if (!line.trim().isEmpty()) {
                try {
                    org.w3c.dom.Document doc = builder.parse(new InputSource(new StringReader(line)));
                    NodeList tradeList = doc.getElementsByTagName("TradeData");

                    for (int i = 0; i < tradeList.getLength(); i++) {
                        Node trade = tradeList.item(i);
                        Document tradeDoc = createBsonDocument(trade);
                        collection.insertOne(tradeDoc);
                    }
                } catch (Exception e) {
                    System.err.println("Skipping line: " + line);
                    e.printStackTrace();
                }
            }
            line = reader.readLine();
        }
        reader.close();
    }

    private static Document createBsonDocument(Node node) {
        Document doc = new Document();
        if (node.hasAttributes()) {
            NamedNodeMap nodeMap = node.getAttributes();
            for (int i = 0; i < nodeMap.getLength(); i++) {
                Node tempNode = nodeMap.item(i);
                doc.append(tempNode.getNodeName(), tempNode.getNodeValue());
            }
        }

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node tempNode = nodeList.item(i);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                Document childDoc = createBsonDocument(tempNode);
                doc.append(tempNode.getNodeName(), childDoc);
            }
        }
        return doc;
    }
}
