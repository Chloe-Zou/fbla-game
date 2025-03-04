public class Investor {
    private int money;
    private boolean invest;

    public Investor(int money, boolean invest) {
        this.money = money;
        this.invest = invest;
    }

    public void invest(int money, boolean invest) {
        if (invest) {
            this.money -= money;
        }
    }

    public int getMoney() {
        return money;
    }

    public boolean isInvesting() {
        return invest;
    }
}