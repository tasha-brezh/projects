import java.util.HashMap;
import java.util.Random;

public class Bank
{
    private HashMap<String, Account> accounts;
    private static final Random random = new Random();

    Bank (HashMap<String, Account> accounts){
        this.accounts = accounts;
    }

    public static synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException
    {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(Operation operation) throws InterruptedException {
        Account fromAccount = operation.getFromAccount();
        Account toAccount = operation.getToAccount();
        Object lock1 = fromAccount;
        Object lock2 = toAccount;
        if (fromAccount.getAccNumber().compareTo(toAccount.getAccNumber()) < 0) {
            lock1 = toAccount;
            lock2 = fromAccount;
        }
        synchronized (lock1) {
            synchronized (lock2) {
                if (operation.getAmount() >= fromAccount.getMoney()) {
                    System.out.println("Недостаточно средств для перевода");
                    return;
                } else if (!fromAccount.isActive() || !toAccount.isActive()) {
                    System.out.println("В операции учавствует заблокированный счет. Перевод невозможен.");
                }
                else {
                fromAccount.withdraw(operation.getAmount());
                toAccount.add(operation.getAmount());
                System.out.println("Перевод со счета " + fromAccount.getAccNumber() + "  на счет " + toAccount.getAccNumber() + " cуммы " + operation.getAmount() + " выполнен.");
                }
                if (operation.getAmount() > 50000) {
                    if (isFraud(fromAccount.getAccNumber(), toAccount.getAccNumber(), operation.getAmount())) {
                        System.out.println("Счета " + fromAccount.getAccNumber() + " и " + toAccount.getAccNumber() + " заблокированы");
                        fromAccount.deactivate();
                        toAccount.deactivate();
                    }
                }
            }
        }
    }

    public long getBalance(String accountNum) {
            long balance;
            balance = accounts.get(accountNum).getMoney();
            return balance;
    }
}

