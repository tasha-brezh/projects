import com.mongodb.MongoClient;
import com.mongodb.client.*;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    public static final String ADD_GOODS = "ДОБАВИТЬ_ТОВАР";
    public static final String ADD_SHOP = "ДОБАВИТЬ_МАГАЗИН";
    public static final String SUBMIT_GOODS = "ВЫСТАВИТЬ_ТОВАР";
    public static final String STATISTICS = "СТАТИСТИКА_ТОВАРОВ";

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("127.0.0.1" , 27017);
        MongoDatabase database = mongoClient.getDatabase("local");
        MongoCollection<Document> goods = database.getCollection("goods");
        MongoCollection<Document> shops = database.getCollection("shops");

        for(; ;) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите команду: " + ADD_GOODS + " или " + SUBMIT_GOODS + " или " + STATISTICS + ". \nСначала укажите команду, затем название товара/магазина в одно слово, без пробелов");
            String input = scanner.nextLine().trim();
            System.out.println(input);
            if (input.contains(ADD_SHOP)) {
                addShop(input, shops);
            } else if (input.contains(ADD_GOODS)) {
                addGood(input, goods);
            } else if (input.contains(SUBMIT_GOODS)) {
                submitGoods(input, shops, goods);
            } else if(input.contains(STATISTICS)){
                showStatistics(shops, goods);
            } else{
                System.out.println("Вы ввели неправильную команду");
            }
        }
    }

    public static void addShop(String input, MongoCollection<Document> shops){
        int index = input.indexOf(" ");
        String shopName = input.substring(index).trim();
        Document newShop = new Document().append("Shop", shopName).append("Goods", new ArrayList<String>());
        shops.insertOne(newShop);
        System.out.println(newShop);
    }

    public static void addGood(String input, MongoCollection<Document> goods){
       String[] strings= input.split(" ");
       String goodName = strings[1];
       int goodPrice = Integer.parseInt(strings[2]);
       Document newGood = new Document().append("Good", goodName).append("Price", goodPrice);
       goods.insertOne(newGood);
        System.out.println(newGood);
    }

    public static void submitGoods(String input, MongoCollection<Document>shops, MongoCollection<Document> goods) {
        String[] strings = input.split(" ");
        String goodName = strings[1];
        String shopName = strings[2];

        if (ifShopExists(shops, shopName) && ifGoodExists(goods, goodName)) {
            Bson filter = eq("Shop", shopName);
            ArrayList<String> goodList = (ArrayList<String>) shops.find(filter).first().get("Goods");
            if ((goodList.size() == 0) || (!goodList.contains(goodName))) {
                goodList.add(goodName);
            }
            shops.findOneAndUpdate(filter, new Document("$set", new Document("Goods", goodList)));
        }
    }

    public static void showStatistics(MongoCollection<Document> shops, MongoCollection<Document> goods){
        AggregateIterable<Document> priceStatistics = shops.aggregate(Arrays.asList(
                Aggregates.lookup(goods.getNamespace().getCollectionName(), "Goods", "Good", "Goods"),
                Aggregates.unwind("$Goods"),
                Aggregates.group("$Shop", Accumulators.sum("GoodsQuantity", 1),
                        Accumulators.avg("AvgPrice", "$Goods.Price"),
                        Accumulators.max("MaxPrice", "$Goods.Price"),
                        Accumulators.min("MinPrice", "$Goods.Price"))
        ));
        priceStatistics.forEach((Consumer<Document>) doc -> {
            System.out.println(doc);
        });

        AggregateIterable<Document> cheapProducts = shops.aggregate(Arrays.asList(
                Aggregates.lookup(goods.getNamespace().getCollectionName(), "Goods", "Good", "Goods"),
                Aggregates.unwind("$Goods"),
                Aggregates.match(lt("Goods.Price", 100)),
                Aggregates.group("$Shop", Accumulators.sum("CheapProductsQuantity", 1))
        ));

        cheapProducts.forEach((Consumer<Document>) doc -> {
            System.out.println(doc);
        });

    }

    public static boolean ifShopExists(MongoCollection<Document> shops, String shopName){
        Bson filter = eq("Shop", shopName);
        if(shops.find(filter).first() != null){
            return true;
        } else{
            System.out.println("Магазин не найден");
            return false;
        }
    }

    public static boolean ifGoodExists(MongoCollection<Document> goods, String goodName){
        Bson filter = eq("Good", goodName);
        if(goods.find(filter).first() != null){
            return true;
        } else{
            System.out.println("Товар не найден");
            return false;
        }
    }
}

