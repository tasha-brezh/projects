import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0").get();
        ArrayList<String> stationList = new ArrayList<>();
        ArrayList<String> lineList = new ArrayList<>();
        Element table = doc.getElementsByAttributeValue("class", "standard sortable").get(0);
        Elements rows = table.getElementsByTag("tr");
        rows.remove(0);
        for (Element row : rows) {
            Element column = row.child(1);
            String stationName = column.selectFirst("a").text();
            String lineNumber = row.child(0).selectFirst("span").text();
            if(lineNumber.startsWith("0")){
                lineNumber = lineNumber.replace("0", "");
            }
            String lineName = row.child(0).children().attr("title");
            List<String> connectionNumber = row.child(3).children().eachText();
            Parser.connectionNumberList.add(connectionNumber);
            List<String> connectionName = row.child(3).children().eachAttr("title");
            Parser.connectionNameList.add(connectionName);
            Parser.stations.add(lineNumber + ", " + stationName);
            Parser.lines.add(lineNumber + ", " + lineName);
            lineList.add(lineNumber);
        }
    Parser.createFile();
    Parser.readFromFile();
    }
}
