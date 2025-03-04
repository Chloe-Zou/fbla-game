public class Customer {
    private int money;
    private boolean buy;

    public Customer(int money, boolean buy) {
        this.money = money;
        this.buy = buy;
    }

    public void buy(int money, boolean buy) {
        if (buy) {
            this.money -= money;
        }
    }

    public int getMoney() {
        return money;
    }

    public boolean isBuying() {
        return buy;
    }
}