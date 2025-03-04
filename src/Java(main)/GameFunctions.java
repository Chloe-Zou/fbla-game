import java.util.Random;

public class GameFunctions {
    private int popularity;

    public GameFunctions(int popularity) {
        this.popularity = popularity;
    }

    public void mTrends(int popularity) {
        this.popularity = popularity + new Random().nextInt(10) - 5; // Random popularity change
    }

    public int getPopularity() {
        return popularity;
    }
}