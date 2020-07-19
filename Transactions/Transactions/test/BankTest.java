import junit.framework.TestCase;
import org.junit.Test;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class BankTest extends TestCase {
    static HashMap<String, Account> accounts = new HashMap<>();
    Bank bank;
    Queue<Operation> operations;
    Account fromAccount;
    Account toAccount;
    Generator generator;

    public BankTest() {
    }

    public void setUp() {
        int minAccount = 10000000;
        int maxAccount = 90000000;
        long minMoney = 0;
        long maxMoney = 100000;

        // создаем список счетов
        for (int i = 0; i < 200; i++) {
            String accNumber = Integer.toString(ThreadLocalRandom.current().nextInt(minAccount, maxAccount));
            long money = ThreadLocalRandom.current().nextLong(minMoney, maxMoney);
            Account account = new Account(accNumber, money);
            accounts.put(accNumber, account);
        }

        // добавляем счета для тестирования
        fromAccount = new Account("1000000", 120000);
        toAccount = new Account("2000000", 20000);
        accounts.put(fromAccount.getAccNumber(),fromAccount);
        accounts.put(toAccount.getAccNumber(), toAccount);
        bank = new Bank(accounts);
        this.operations = createQueue();
        generator = new Generator(accounts, operations, bank);
    }

    public void testBankTransfer(){
        Operation testOperation = new Operation(fromAccount, toAccount, 30000);
        try {
            bank.transfer(testOperation);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long expectedFromAccountBalance = 90000;
        long expectedToAccountBalance = 50000;
        long actualFromAccountBalance = bank.getBalance(fromAccount.getAccNumber());
        long actualToAccountBalance = bank.getBalance(toAccount.getAccNumber());
        assertEquals(expectedFromAccountBalance, actualFromAccountBalance);
        assertEquals(expectedToAccountBalance,actualToAccountBalance);
    }

    public void testFailTransfer(){
        Operation testOperation = new Operation(fromAccount, toAccount, 200000);
        try {
            bank.transfer(testOperation);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long expectedFromAccountBalance = 120000;
        long expectedToAccountBalance = 20000;
        long actualFromAccountBalance = bank.getBalance(fromAccount.getAccNumber());
        long actualToAccountBalance = bank.getBalance(toAccount.getAccNumber());
        assertEquals(expectedFromAccountBalance, actualFromAccountBalance);
        assertEquals(expectedToAccountBalance,actualToAccountBalance);
    }

    @Test
    public void testCheckBankBalance()
    {
        // считаем баланс банка до запуска потоков
        long initialBankBalance = 0;
        for (Account account : accounts.values()) {
           initialBankBalance += account.getMoney();
        }

        // запускаем потоки
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            threads.add(new Thread(generator));
        }
        threads.forEach(t -> t.start());
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        // считаем баланс банка после того, как потоки отработали
        long finalBankBalance =0;
        for (Account account : accounts.values()) {
            finalBankBalance += account.getMoney();
        }
        assertEquals(initialBankBalance, finalBankBalance);
    }

    // генерируем случайные счета
    public Account generateRandomAccount() {
        Random generator = new Random();
        List<String> keys = new ArrayList<String>(accounts.keySet());
        String randomKey = keys.get(generator.nextInt(keys.size()));
        Account value = accounts.get(randomKey);
        return value;
    }

    // генерируем случайные операции
    public Operation generateRandomOperation() {
        Account fromAccount = generateRandomAccount();
        String fromAcc = fromAccount.getAccNumber();
        Account toAccount = generateRandomAccount();
        String toAcc = toAccount.getAccNumber();
        while (toAccount.getAccNumber() == fromAccount.getAccNumber()) {
            toAccount = generateRandomAccount();
            toAcc = toAccount.getAccNumber();
        }
        Long sumTransfer = ThreadLocalRandom.current().nextLong(1000, 200000);
        Operation operation = new Operation(fromAccount, toAccount, sumTransfer);
        return operation;
    }

    // создаем очередь случайных операций
    public Queue<Operation> createQueue() {
        operations = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 1000; i++) {
            Operation operation = generateRandomOperation();
            operations.add(operation);
        }
        return operations;
    }
}