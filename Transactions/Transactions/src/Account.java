import java.util.Objects;

public class Account {
    private long money;
    private String accNumber;
    private boolean isActive;

    Account(String accNumber, long money) {
        this.accNumber = accNumber;
        this.isActive = true;
        this.money = money;
    }

    public void withdraw(long amount){
        money -= amount;
    }

    public void add (long amount){
        money += amount;
    }

    public long getMoney() { return money; }

    public void setMoney(long money) {
        this.money = money;
    }

    public void deactivate(){
        isActive = false;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Account that = (Account) o;
            return Objects.equals(this.accNumber, that.accNumber);
        } else {
            return false;
        }
    }
}
