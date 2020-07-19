import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class MovementAnalytycs {
    static double getTotalIncome(){
        Double totalValue = 0.0;
        for (Operation operation : MovementListParser.getOperationList()) {
            totalValue += operation.getIncome();
        }
        return totalValue;
    }

    static double getTotalOutcome(){
        double totalOutcome = 0.0;

        for (Operation operation : MovementListParser.getOperationList()) {
            totalOutcome += operation.getOutcome();
        }
        return totalOutcome;
    }

    static ArrayList<Operation> getOutcomeList() {
        ArrayList<Operation> outcomeList = new ArrayList<>();

        for (Operation operation : MovementListParser.getOperationList()) {
            if (operation.getOutcome() > 0) {
                String[] descriptionArray = operation.getDescription().split("\\d{2}\\.\\d{2}\\.\\d{2}");
                int index;
                if(descriptionArray[0].contains("\\")) {
                    index = descriptionArray[0].lastIndexOf("\\");
                } else{
                    index = descriptionArray[0].lastIndexOf("/");
                }
                String finalDescription = descriptionArray[0].substring(index+1).trim();
                operation.setDescription(finalDescription);
                outcomeList.add(operation);
            }
        }
        return outcomeList;
    }

    static Map<String, Double> getOutcomeMap(){

        Map<String, Double> resultList = getOutcomeList().stream().
                collect(Collectors.groupingBy((Operation:: getDescription), Collectors.summingDouble(Operation::getOutcome)));
        return resultList;
    }

    static void printOutcomeResult(){

        for(Map.Entry<String, Double> item: getOutcomeMap().entrySet()){
            System.out.println("Статья расходов: " + item.getKey() + ": " + item.getValue() + " рублей");
        }
    }

}
