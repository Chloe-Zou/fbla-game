import java.util.Random;

public class Market {
    private Random random;
    private String currentTrend; 
    private int demandLevel; 
    private boolean globalEventActive; 
    private String globalEvent; 

    public Market() {
        random = new Random();
        currentTrend = "Stable"; 
        demandLevel = 50; 
        globalEventActive = false;
        globalEvent = "None";
    }

    /**
     * Simulate market trends and events
     */
    public void updateMarket() {
        int trendChange = random.nextInt(100);
        if (trendChange < 20) {
            currentTrend = "Boom";
            demandLevel += 10; 
        } else if (trendChange < 40) {
            currentTrend = "Recession";
            demandLevel -= 10; 
        } else {
            currentTrend = "Stable";
        }

        // Ensure demand level stays within bounds
        demandLevel = Math.max(0, Math.min(100, demandLevel));

        // Randomly trigger a global event
        if (random.nextInt(100) < 10) {
            globalEventActive = true;
            String[] events = {
                "Pandemic: Demand for tech products surges!",
                "Natural Disaster: Supply chain disrupted!",
                "Trade War: Import costs increase!",
                "Tech Breakthrough: New innovation boosts sales!"
            };
            globalEvent = events[random.nextInt(events.length)];
        } else {
            globalEventActive = false;
            globalEvent = "None";
        }
    }

    /**
     * Get the current market trend
     */
    public String getCurrentTrend() {
        return currentTrend;
    }

    /**
     * Get the current demand level
     */
    public int getDemandLevel() {
        return demandLevel;
    }

    /**
     * Check if a global event is active
     */
    public boolean isGlobalEventActive() {
        return globalEventActive;
    }

    /**
     * Get the current global event
     */
    public String getGlobalEvent() {
        return globalEvent;
    }

    /**
     * determines how the popularity changes demand level
     * determines what global event affects popularity
     */
    public int calculatePopularityImpact(int basePopularity) {
        int popularityChange = 0;

        // Demand affects popularity
        if (demandLevel > 70) {
            popularityChange += 10; 
        } else if (demandLevel < 30) {
            popularityChange -= 10; 
        }

        // global events affect popularity
        if (globalEventActive) {
            if (globalEvent.contains("Pandemic")) {
                popularityChange += 15; 
            } else if (globalEvent.contains("Natural Disaster")) {
                popularityChange -= 15; 
            } else if (globalEvent.contains("Trade War")) {
                popularityChange -= 5; 
            } else if (globalEvent.contains("Tech Breakthrough")) {
                popularityChange += 20; 
            }
        }

        return basePopularity + popularityChange;
    }

    /**
     * calculates how popularity and demand affects revenue
     */
    public int calculateRevenueImpact(int baseRevenue, int popularity) {
        double multiplier = 1.0;

        // Popularity affects revenue
        if (popularity > 80) {
            multiplier *= 1.5; 
        } else if (popularity < 30) {
            multiplier *= 0.5; 
        }

        // Market trend affects revenue
        if (currentTrend.equals("Boom")) {
            multiplier *= 1.2; 
        } else if (currentTrend.equals("Recession")) {
            multiplier *= 0.8; 
        }

        return (int) (baseRevenue * multiplier);
    }
}