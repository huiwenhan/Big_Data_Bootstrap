package me.huiwen.example.mobgodb.sample1;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;


public class HelloWord {

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException {
		MongoClient mongo = new MongoClient( "localhost" , 27017 );
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("database name");
		boolean auth = db.authenticate("username", "password".toCharArray());
		List<String> dbs = mongo.getDatabaseNames();
		for(String thedb : dbs){
			System.out.println(thedb);
		}
		DB testdb = mongo.getDB("testdb");
		DBCollection table = testdb.getCollection("user");
		Set<String> tables = testdb.getCollectionNames();
		 
		for(String coll : tables){
			System.out.println(coll);
		}
		//save
		BasicDBObject document = new BasicDBObject();
		document.put("name", "mkyong");
		document.put("age", 30);
		document.put("createdDate", new Date());
		table.insert(document);
		
		//update
		 
		BasicDBObject query = new BasicDBObject();
		query.put("name", "mkyong");
	 
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("name", "mkyong-updated");
	 
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);
	 
		table.update(query, updateObj);
		//find
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "mkyong");
	 
		DBCursor cursor = table.find(searchQuery);
	 
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}

		searchQuery.put("name", "mkyong");
	 
		table.remove(searchQuery);
		
	}

}
