package com.example.entity;

import com.mongodb.client.MongoClient;
        import com.mongodb.client.MongoClients;
        import com.mongodb.client.MongoCollection;
        import com.mongodb.client.MongoDatabase;
        import org.bson.Document;
import org.bson.types.ObjectId;

public class Comparison {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("db");
        MongoCollection<Document> collection = database.getCollection("log_analyzer");

        Document doc1 = collection.find(new Document("_id", new ObjectId("64aef52cffc59e1f7ce5f6ba"))).first();
        Document doc2 = collection.find(new Document("_id", new ObjectId("64aef54dffc59e1f7ce5f6bb"))).first();

        boolean isEqual = doc1.equals(doc2);

        System.out.println("The documents are equal: " + isEqual);
    }
}

