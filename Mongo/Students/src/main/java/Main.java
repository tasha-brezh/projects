import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

   public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Не введен путь к файлу");
            System.exit(1);
        }
        String path = args[0];
        MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );
        MongoDatabase database = mongoClient.getDatabase("local");
        MongoCollection<Document> collection = database.getCollection("Students");
        collection.drop();
        StudentParser studentParser = new StudentParser(path);
        studentParser.loadDataFromFile();
        ArrayList<Student> students = studentParser.getStudentList();
        List<Document> documentList = new ArrayList<>();
        Gson gson = new Gson();
        for (Student student: students) {
            String jsonString = gson.toJson(student);
            Document document = Document.parse(jsonString);
            documentList.add(document);
        }
        collection.insertMany(documentList);



        //— общее количество студентов в базе
        System.out.println(collection.countDocuments());

        // — количество студентов старше 40 лет.
        BasicDBObject query = new BasicDBObject("age",
                new BasicDBObject("$gt", 40));
        System.out.println(collection.countDocuments((query)));

        //— имя самого молодого студента.
        Document youngestStudent = collection.find().sort(new BasicDBObject("age",1)).first();
        System.out.println(youngestStudent.get("name"));

        //— список курсов самого старого студента.
        Document oldestStudent = collection.find().sort(new BasicDBObject("age", -1)).first();
        System.out.println(oldestStudent.get("courses"));
        }
}
