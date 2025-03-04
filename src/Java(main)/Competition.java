public class Competition {
    private int popularity;
    private int price;

    public Competition(int popularity, int price) {
        this.popularity = popularity;
        this.price = price;
    }

    public void raisePrice(int amount) {
        this.price += amount;
    }

    public void lowPrice(int amount) {
        this.price -= amount;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getPrice() {
        return price;
    }

    // Add this method
    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}