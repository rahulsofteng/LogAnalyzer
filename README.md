# **Log Analyzer**

## Description

This project is designed to parse XML data from log files and store them in MongoDB for later analysis. It parses each line of the log file as an XML string, converts it into a BSON document and stores it into a MongoDB collection.

## Prerequisites
 - Java 11 or newer
 - MongoDB 4.0 or newer
 - Maven

## Libraries Used
 - MongoDB Java Driver
 - JUnit 5
 - Mockito
 - Logback
 - SLF4J
 - Java DOM Parser: For parsing XML data.

## Usage
1. Ensure MongoDB is installed and running on your system.
2. Clone the repository to your local machine.
3. Open the project in your favorite IDE (IntelliJ IDEA recommended).
4. Update the MongoDB connection string, database name, and collection name in Main.java according to your setup.
5. Run Main.java to start the program.

## Testing

Queries
Find a Document by fxallId:
````
db.getCollection("log_analyzer").find({"Order.fxallId" : "338241881"})
````
find a Document by _id:
````
db.getCollection("log_analyzer").find({"_id" : ObjectId("64aef54dffc59e1f7ce5f6bb")})
````    

## How to install MongoDB on Windows

* Download the MongoDB Community Server from the MongoDB website. Select the version that's right for your version of Windows.
* Run the downloaded file (MSI package). This will open the MongoDB installation wizard.
* Follow the steps in the wizard. Choose "Complete" installation when asked.
* At the end of the installation process, uncheck "Install MongoDB Compass" (unless you want this tool).
* MongoDB will be installed in the "C:\Program Files\MongoDB\Server\4.x\bin" directory by default. You can add this path to your Windows PATH environment variable for easy command line access.
* MongoDB requires a data directory to store its files. The default location is "C:\data\db". Create this folder using the command prompt:

## How to install MongoDB on Mac(using Homebrew):

* Open the Terminal app and type brew update.
* brew tap mongodb/brew
  brew install mongodb-community
* brew services start mongodb-community

## Run the MongoDB server

* Open a new command prompt and type mongosh. This will start the MongoDB server on port 27017 by default.
* use admin 
* Run this command:
```
  db.createUser(
  {
  user: "test",
  pwd: "test",
  roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
  }
  )

You can replace "test" and "test" with your desired username and password.
```
* Run this command to see DB's:
``` 
show dbs
```
* Run this command to see collections:
```
show collections
```
* Run this command to see documents:
``` 
db.getCollection("log_analyzer").find()
``` 
