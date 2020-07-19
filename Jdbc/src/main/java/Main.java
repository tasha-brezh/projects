import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/skillbox?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String pass = "brezhik1987";
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("\n" +
                    "Select name,\n" +
                    "COUNT(s.subscription_date)/(SELECT PERIOD_DIFF(EXTRACT(YEAR_MONTH FROM max(subscription_date)), EXTRACT(YEAR_MONTH FROM min(subscription_date)))) AS purchase_per_month\n" +
                    "From Courses c\n" +
                    "JOIN Subscriptions s ON c.id = s.course_id\n" +
                    "GROUP BY course_id;");
            while(resultSet.next()){
                 String courseName = resultSet.getString("name");
                 String purchasePerMonth = resultSet.getString("purchase_per_month");
                 System.out.println(courseName + " - " + purchasePerMonth);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
