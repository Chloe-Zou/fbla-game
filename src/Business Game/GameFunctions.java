import java.util.Random;

public class GameFunctions {
    private int popularity;
    
    /** game functions constructor
     */
    public GameFunctions(int popularity) {
        this.popularity = popularity;
    }
    
    /**
     * calculates new popularity
     */
    public void mTrends(int popularity) {
        this.popularity = popularity + new Random().nextInt(10) - 5;
    }
    
    /**
     * getter for popularity
     */
    public int getPopularity() {
        return popularity;
    }
}