public final class Operation {
    private final Account fromAccount;
    private final Account toAccount;
    private final long amount;

    public Operation(Account fromAccount, Account toAccount, long amount) {
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

   public Account getToAccount() {
        return toAccount;
    }

    public long getAmount() {
        return amount;
    }

}
