import java.util.ArrayList;

public class Business {
    private String name;
    private int funds;
    private int popularity;
    private int price;
    private ArrayList<String> products;
    private int unitsSold;
    private boolean bankrupt;
    private int weekCount;
    private int productivity;
    
    /**
     * business object constructor
     * default price, units sold, and productivity
     */
    public Business(String name, int funds, int popularity, String initialProduct) {
        this.name = name;
        this.funds = funds;
        this.popularity = popularity;
        this.price = 100; 
        this.products = new ArrayList<>();
        this.products.add(initialProduct); 
        this.unitsSold = 1000; 
        this.bankrupt = false;
        this.weekCount = 0;
        this.productivity = 50; 
    }

    /**
     * Raise the price of products
     */
    public void raisePrice(int amount) {
        this.price += amount;
    }

    /**
     * Lower the price of products
     */
    public void lowerPrice(int amount) {
        this.price -= amount;
    }

    /**
     * Launch a new product
     */
    public void launchNewProduct(String product) {
        this.products.add(product);
        this.popularity += 10; 
    }

    /**
     * Add funds to the business
     */
    public void addFunds(int amount) {
        this.funds += amount;
    }

    /**
     * Deduct funds from the business
     */
    public void deductFunds(int amount) {
        this.funds -= amount;
        if (this.funds < 0) {
            this.bankrupt = true;
        }
    }

    /**
     * Update units sold
     */
    public void updateUnitsSold(int units) {
        this.unitsSold += units;
    }

    /**
     * Increment week count
     */
    public void incrementWeekCount(int weeks) {
        this.weekCount += weeks;
    }

    /**
     * Calculate revenue based on units sold, price, and popularity
     */
    public int calculateRevenue() {
        return (int) (unitsSold * price * (popularity / 100.0));
    }

    /** Check if the business is bankrupt
     */
    public boolean isBankrupt() {
        return bankrupt;
    }

    /**
     * Check if the business has won
     * >= 1 million dollars
     */
    public boolean hasWon() {
        return funds >= 1000000;
    }

    /**
     * getters and setters
     */
    public int getFunds() {
        return funds;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public String getProducts() {
        return String.join(", ", products); 
    }

    public int getPrice() {
        return price;
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    public int getWeekCount() {
        return weekCount;
    }

    public int getProductivity() {
        return productivity;
    }

    public void setProductivity(int productivity) {
        this.productivity = productivity;
    }
}