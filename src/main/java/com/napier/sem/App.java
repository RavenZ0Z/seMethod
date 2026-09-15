package com.napier.sem;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class App
{
    public static void main(String[] args)
    {
        // Connect to MongoDB
        MongoClient mongoClient = new MongoClient("mongo-dbserver");

        // Get the database
        MongoDatabase database = mongoClient.getDatabase("mydb");

        // Get the collection
        MongoCollection<Document> collection = database.getCollection("test");

        // Create a document
        Document doc = new Document("name", "")
                .append("class", "DevOps")
                .append("year", "2026")
                .append("result", new Document("CW", 95).append("EX", 85));

        // Add document to collection
        collection.insertOne(doc);

        // Check document in collection
        Document myDoc = collection.find().first();

        System.out.println(myDoc.toJson());

        // Close connection
        mongoClient.close();
    }
}