import java.util.*;

public class Generator implements Runnable {
    private HashMap<String, Account> accounts;
    private Queue<Operation> operations;
    private Bank bank;

    public Generator(HashMap<String, Account> accounts, Queue<Operation> operations, Bank bank ){
        this.accounts = accounts;
        this.operations = operations;
        this.bank = bank;
    }

    @Override
    public void run(){
        try {
            while (true) {
                Operation ob = operations.poll();
                if (ob != null) {
                  bank.transfer(ob);
                }
                if(ob == null){
                    break;
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(HashMap<String, Account> accounts) {
        this.accounts = accounts;
    }

    public Queue<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Queue<Operation> operations) {
        this.operations = operations;
    }
}
