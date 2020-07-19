import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MovementListParser {
    private static String movementList;
    private static String dateFormat = "dd.MM.yy";
    private static ArrayList<Operation> operationList;
    private final int INDEX_ACCOUNT_TYPE = 0;
    private final int INDEX_ACCOUNT_NUMBER = 1;
    private final int INDEX_CURRENCY = 2;
    private final int INDEX_DATE = 3;
    private final int INDEX_REFERENCE = 4;
    private final int INDEX_DESCRIPTION = 5;
    private final int INDEX_INCOME =6;
    private final int INDEX_OUTCOME = 7;



    MovementListParser(String path) {
       this.movementList = path;
    }

       ArrayList<Operation> loadDataFromFile() {
        operationList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(movementList));
            lines.remove(0);
            for (String line : lines) {
                String[] fragments = line.split(",");
                ArrayList<String> columnList = new ArrayList<String>();
                for (int i = 0; i < fragments.length; i++) {
                    //Если колонка начинается на кавычки или заканчиваеться на кавычки
                    if (IsColumnPart(fragments[i])) {
                        String lastText = columnList.get(columnList.size() - 1);
                        columnList.set(columnList.size() - 1, lastText + "," + fragments[i]);
                    } else {
                        columnList.add(fragments[i]);
                    }
                }
                operationList.add(new Operation(
                        columnList.get(INDEX_ACCOUNT_TYPE),
                        columnList.get(INDEX_ACCOUNT_NUMBER),
                        columnList.get(INDEX_CURRENCY),
                        (new SimpleDateFormat(dateFormat)).parse(columnList.get(INDEX_DATE)),
                        columnList.get(INDEX_REFERENCE),
                        columnList.get(INDEX_DESCRIPTION),
                        Double.parseDouble(columnList.get(INDEX_INCOME).replace("\"", "")
                                .replace(",", ".")),
                        Double.parseDouble(columnList.get(INDEX_OUTCOME).replace("\"", "")
                                .replace(",", "."))
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return operationList;
    }

    private static boolean IsColumnPart(String text) {
        String trimText = text.trim();
        //Если в тексте одна ковычка и текст на нее заканчиваеться значит это часть предыдущей колонки
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }

    static ArrayList<Operation> getOperationList(){

        return operationList;
    }
}
