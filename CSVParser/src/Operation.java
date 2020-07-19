import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class Operation {
    private String accountType;
    private String accountNumber;
    private String currency;
    private Date date;
    private String reference;
    private String description;
    private Double income;
    private Double outcome;


   Operation(String accountType, String accountNumber, String currency, Date date,
                     String reference, String description, Double income, Double outcome) {
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.date = date;
        this.reference = reference;
        this.description = description;
        this.income = income;
        this.outcome = outcome;

    }

    Double getOutcome() {
        return outcome;
    }

    Double getIncome(){return income;}

   String getDescription(){
        return description;
    }

    void setDescription (String description){
        this.description = description;
    }


}