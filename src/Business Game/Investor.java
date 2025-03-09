public class Investor {
    private int money;
    private boolean invest;
    
    /** investor object contructor
     */
    public Investor(int money, boolean invest) {
        this.money = money;
        this.invest = invest;
    }
    
    /**
     * determines how much investment
     */
    public void invest(int money, boolean invest) {
        if (invest) {
            this.money -= money;
        }
    }
    
    /**
     * getter for amount of money
     */
    public int getMoney() {
        return money;
    }
    
    /**
     * is or isnt currently invested
     */
    public boolean isInvesting() {
        return invest;
    }
}