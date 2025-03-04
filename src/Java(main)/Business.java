public class Business {
    private String name;
    private int popularity;
    private int price;
    private String products;
    private int funds;

    public Business(String name, int funds, int popularity, String products) {
        this.name = name;
        this.funds = funds;
        this.popularity = popularity;
        this.products = products;
        this.price = 100; // Default price
    }

    public void raisePrice(int amount) {
        this.price += amount;
    }

    public void lowPrice(int amount) {
        this.price -= amount;
    }

    public void newProduct(String product) {
        this.products = product;
        this.popularity += 10;
    }

    public void buy(String product, int money) {
        this.funds -= money;
        this.products = product;
    }

    public int getFunds() {
        return funds;
    }

    public int getPopularity() {
        return popularity;
    }

    public String getName() {
        return name;
    }

    public String getProducts() {
        return products;
    }

    public int getPrice() {
        return price;
    }

    // Add this method
    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}