public class Main {
    static String path = "data/movementList.csv";
    public static void main(String[] args){
     try{
            MovementListParser movementListParser = new MovementListParser(path);
            movementListParser.loadDataFromFile();
            System.out.println("Общая сумма доходов: " + MovementAnalytycs.getTotalIncome() + " рублей.");
            System.out.println("Общая сумма расходов " + MovementAnalytycs.getTotalOutcome() + " рублей.");
            MovementAnalytycs.printOutcomeResult();
        } catch (Exception e){
           e.printStackTrace();
        }
        }
}
