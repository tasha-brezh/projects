import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("127.0.0.1" , 27017);
        MongoDatabase database = mongoClient.getDatabase("local");
        MongoCollection<Document> collection = database.getCollection("books");
        collection.drop();
        Document firstBook = new Document()
                .append("Author", "Андрей Курпатов")
                .append("Name", "Красная таблетка")
                .append("Year", "2019");

        Document secondBook = new Document()
                .append("Author", "Андрей Курпатов")
                .append("Name", "Чертоги разума")
                .append("Year", "2020");

        Document thirdBook = new Document()
                .append("Author", "Стивен Р. Кови")
                .append("Name", "7 навыков высокоэффективных людей")
                .append("Year", "2012");

        Document forthBook = new Document()
                .append("Author", "Ли Куан Ю")
                .append("Name", "История Сингапура")
                .append("Year", "2018");

        Document fifthBook = new Document()
                .append("Author", "Джордж Мартин")
                .append("Name", "Битва Королей")
                .append("Year", "1999");

        List<Document> bookList = new ArrayList<>();
        bookList.add(firstBook);
        bookList.add(secondBook);
        bookList.add(thirdBook);
        bookList.add(forthBook);
        bookList.add(fifthBook);

        collection.insertMany(bookList);

        //Напишите запрос, который выводит только книги вашего любимого автора.
        BasicDBObject searchQuery = new BasicDBObject("Author", "Андрей Курпатов");
        FindIterable<Document> resultList = collection.find(searchQuery);
        MongoCursor<Document> iterator = resultList.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        //Напишите запрос для выбора одной самой старой книги из коллекции.
        Document oldestBook = collection.find().sort(new BasicDBObject("Year",1)).first();
        System.out.println(oldestBook);

    }
}
