package com.example.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import java.io.IOException;

public class Comparison {

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("db");
        MongoCollection<Document> collection = database.getCollection("log_analyzer");

        Document doc1 = collection.find(new Document("_id", new ObjectId("64aef52cffc59e1f7ce5f6ba"))).first();
        Document doc2 = collection.find(new Document("_id", new ObjectId("64aef54dffc59e1f7ce5f6bb"))).first();

        JsonWriterSettings settings = JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build();

        boolean isEqual = false;
        try {
            isEqual = compareDocuments(doc1.toJson(settings), doc2.toJson(settings));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("The documents are equal: " + isEqual);
    }

    public static boolean compareDocuments(String doc1, String doc2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert strings to JsonNode
        JsonNode doc1Node = objectMapper.readTree(doc1);
        JsonNode doc2Node = objectMapper.readTree(doc2);

        // Remove '_id' and 'submitTimestamp' from both documents
        ((com.fasterxml.jackson.databind.node.ObjectNode) doc1Node).remove("_id");
        ((com.fasterxml.jackson.databind.node.ObjectNode) doc1Node.get("Order")).remove("submitTimestamp");
        ((com.fasterxml.jackson.databind.node.ObjectNode) doc2Node).remove("_id");
        ((com.fasterxml.jackson.databind.node.ObjectNode) doc2Node.get("Order")).remove("submitTimestamp");

        // Compare remaining content of the documents
        return doc1Node.equals(doc2Node);
    }
}


