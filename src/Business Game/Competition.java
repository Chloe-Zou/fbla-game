public class Competition {
    private int popularity;
    private int price;
    
    /**
     * competition constructor
     */
    public Competition(int popularity, int price) {
        this.popularity = popularity;
        this.price = price;
    }
    
    /**
     * raises price
     */
    public void raisePrice(int amount) {
        this.price += amount;
    }
    
    /**
     * lowers price
     */
    public void lowPrice(int amount) {
        this.price -= amount;
    }
    
    /**
     * getter for popularity
     */
    public int getPopularity() {
        return popularity;
    }
    
    /**
     * getter for populaity
     */
    public int getPrice() {
        return price;
    }

    /**
     * setter for popularity
     */
    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}