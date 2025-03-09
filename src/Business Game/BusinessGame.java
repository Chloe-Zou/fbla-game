import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class BusinessGame {
    private Business business;
    private Competition competition;
    private Investor investor;
    private Market market; 
    private Random random;
    private Scanner scanner;

    public static void main(String[] args) {
        BusinessGame game = new BusinessGame();
        game.start();
    }

    /**
     * starts the game, initializes game objects, displays the initial status
     * runs game loop until win or lose
     */
    public void start() {
        random = new Random();
        scanner = new Scanner(System.in);

        // initialize game objects
        business = new Business("Techy Technicians", 10000, 50, "Smart Devices");
        competition = new Competition(40, 100);
        investor = new Investor(20000, false);
        market = new Market(); 

        // display initial business status and situation
        System.out.println("Welcome to the Business Game!");
        System.out.println("====================================================");
        System.out.println("You are the owner of a startup tech business, and you have the responsibility to make the correct decisions to lead your business to success!");
        System.out.println("Your business, " + business.getName() + ", is located in the middle of San Francisco.");
        System.out.println("You specialize in " + business.getProducts() + ", but the market is highly competitive.");
        System.out.println("Your competition, 'Tech Titans', is a well-established company with a strong foothold in the industry.");
        System.out.println("====================================================");
        System.out.println("Current Challenges:");
        System.out.println("1. Rising Rent Costs: Due to the booming tech industry, the rent for your office space has increased by 20% this year.");
        System.out.println("This has put a strain on your finances, leaving you with only $" + business.getFunds() + " in the bank.");
        System.out.println("\n2. Declining Customer Base: Customers are increasingly opting to your competition's campaign'.");
        System.out.println("Your current popularity is " + business.getPopularity() + " (out of 100), while Tech Titans' popularity is " + competition.getPopularity() + ".");
        System.out.println("\n3. Supply Chain Issues: A recent global chip shortage has driven up the cost of producing your devices.");
        System.out.println("Your product price is currently $" + business.getPrice() + ", but your profit margins are shrinking.");
        System.out.println("====================================================");
        System.out.println("The road ahead is challenging, but with the right decisions, you can overcome these obstacles and grow your business!");
        // display initial business status
        checkStatus();
        
        // game loop
        while (!business.isBankrupt() && !business.hasWon()) {
            System.out.println("\n");
            // update market conditions
            market.updateMarket();
            displayMarketConditions();
            optionsMenu();
            String input = scanner.next();
            if (input.equalsIgnoreCase("stop")) {
                System.out.println("You chose to end the game. Thanks for playing!");
                return;
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 8) {
                    handleChoice(choice);
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                    continue; 
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or type 'stop' to end the game.");
                continue; 
            }
            
            updateGameState();
            triggerRandomEvent();
            business.incrementWeekCount(2);
            checkStatus();
        }

        // end game conditions
        if (business.hasWon()) {
            System.out.println("\nCongratulations! You reached $1,000,000 and won the game!");
        } else {
            System.out.println("\nGame Over! Your business went bankrupt. Better luck next time!");
        }
    }

    /**
     * displays the current market conditions
     * trends, demand, global event
     */
    private void displayMarketConditions() {
        System.out.println("--- Market Conditions ---");
        System.out.println("Current Trend: " + market.getCurrentTrend());
        System.out.println("Demand Level: " + market.getDemandLevel() + " (out of 100)");
        if (market.isGlobalEventActive()) {
            System.out.println("Global Event: " + market.getGlobalEvent());
        }
    }

    /**
     * displays the option menu
     */
    private void optionsMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Raise product price");
        System.out.println("2. Lower product price");
        System.out.println("3. Launch a new product");
        System.out.println("4. Seek investment");
        System.out.println("5. Sell products");
        System.out.println("6. Run a marketing campaign");
        System.out.println("7. Hire employees");
        System.out.println("8. Check status");
        System.out.println("Type 'stop' at any time to end the game.");
    }

    /**
     * implements the option that the user chooses
     * options 1-8
     */
    private void handleChoice(int choice) {
        switch (choice) {
            case 1:
                raisePrice();
                break;
            case 2:
                lowerPrice();
                break;
            case 3:
                launchNewProduct();
                break;
            case 4:
                seekInvestment();
                break;
            case 5:
                sellProducts();
                break;
            case 6:
                runCampaign();
                break;
            case 7:
                hireEmployees();
                break;
            case 8:
                checkStatus();
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                break;
        }
    }

    /**
     * raises the price of tthe product, specified by user
     * updates the price, popularity, and funds
     */
    private void raisePrice() {
        System.out.println("By how much would you like to raise the price? (Enter a number between 5 and 50):");
        int raiseAmount = validRange(5, 50);

        business.raisePrice(raiseAmount);
        System.out.println("\nYou raised the price of your products by $" + raiseAmount + ".");

        // risk of customer decline
        int riskFactor = random.nextInt(100);
        if (riskFactor < raiseAmount) {
            System.out.println("Customers are outraged by the steep price increase! Many are switching to your competition.");
            int popularityLoss = 20;
            business.setPopularity(business.getPopularity() - popularityLoss);
            competition.setPopularity(competition.getPopularity() + 10);
            System.out.println("Your popularity decreased by " + popularityLoss + ", and your funds decreased by $" + (popularityLoss * 100) + ".");
            business.deductFunds(popularityLoss * 100);
        } else {
            System.out.println("Customers are slightly annoyed but continue to buy your products.");
            int popularityLoss = 5;
            business.setPopularity(business.getPopularity() - popularityLoss);
            System.out.println("Your popularity decreased by " + popularityLoss + ", and your funds decreased by $" + (popularityLoss * 100) + ".");
            business.deductFunds(popularityLoss * 100);
        }

        // competition reaction
        System.out.println("Tech Titans notices your price increase and decides to lower their prices to attract more customers.");
        competition.lowPrice(10);
        System.out.println("Tech Titans' price is now $" + competition.getPrice() + ".");

        // update popularity based on demand
        int newPopularity = market.calculatePopularityImpact(business.getPopularity());
        business.setPopularity(newPopularity);
        System.out.println("Market demand has influenced your popularity. Your populairty is now " + newPopularity);

        // calculates revenue
        int baseRevenue = business.calculateRevenue();
        int adjustedRevenue = market.calculateRevenueImpact(baseRevenue, business.getPopularity());
        business.addFunds(adjustedRevenue);
        System.out.println("You earned $" + adjustedRevenue + " this week.");
    }

    /**
     * lowers the price of products based on user input
     * updates the price, popularity, and funds
     */
    private void lowerPrice() {
        System.out.println("By how much would you like to lower the price? (Enter a number between 5 and 50):");
        int lowerAmount = validRange(5, 50);

        business.lowerPrice(lowerAmount);
        System.out.println("\nYou lowered the price of your products by $" + lowerAmount + ".");

        // Risk of reduced profit margins
        int riskFactor = random.nextInt(100);
        if (riskFactor < 30) {
            System.out.println("The price reduction has severely impacted your profit margins.");
            System.out.println("You struggle to cover production costs, and your funds take a hit.");
            business.deductFunds(1000);
        } else {
            System.out.println("Customers appreciate the lower prices and continue to support your business.");
            int popularityGain = 5;
            business.setPopularity(business.getPopularity() + popularityGain);
            System.out.println("Your popularity increased by " + popularityGain + ", and your funds increased by $" + (popularityGain * 100) + ".");
            business.addFunds(popularityGain * 100);
        }

        // competition reaction
        System.out.println("Tech Titans sees your price drop and responds by launching a new marketing campaign.");
        System.out.println("Their popularity increases by 10.");
        competition.setPopularity(competition.getPopularity() + 10);

        // update popularity based on demand
        int newPopularity = market.calculatePopularityImpact(business.getPopularity());
        business.setPopularity(newPopularity);
        System.out.println("Market demand has influenced your popularity. Your popularity is now " + newPopularity);

        // caculates revenue
        int baseRevenue = business.calculateRevenue();
        int adjustedRevenue = market.calculateRevenueImpact(baseRevenue, business.getPopularity());
        business.addFunds(adjustedRevenue);
        System.out.println("You earned $" + adjustedRevenue + " this week.");
    }

    /**
     * launches a new product based on user input
     * updates the business's product list, popularity, and funds
     */
    private void launchNewProduct() {
        System.out.println("What would you like to name your new product?");
        scanner.nextLine();
        String newProduct = scanner.nextLine();

        business.launchNewProduct(newProduct);
        System.out.println("\nYou launched a new product: " + newProduct + "!");

        // risk of product failure
        int riskFactor = random.nextInt(100);
        if (riskFactor < 20) {
            System.out.println("The new product launch was a disaster! Customers complain about quality issues.");
            int popularityLoss = 15;
            business.setPopularity(business.getPopularity() - popularityLoss);
            System.out.println("Your popularity decreased by " + popularityLoss + ", and your funds decreased by $" + (popularityLoss * 100) + ".");
            business.deductFunds(popularityLoss * 100);
        } else {
            System.out.println("Customers are excited about your new product! Social media buzzes with positive reviews.");
            int popularityGain = 20;
            business.setPopularity(business.getPopularity() + popularityGain);
            System.out.println("Your popularity increased by " + popularityGain + ", and your funds increased by $" + (popularityGain * 100) + ".");
            business.addFunds(popularityGain * 100);
        }

        // competition reaction
        System.out.println("Tech Titans feels threatened by your new product and responds by lowering their prices.");
        competition.lowPrice(15);
        System.out.println("Tech Titans' price is now $" + competition.getPrice() + ".");

        // update popularity based on demand
        int newPopularity = market.calculatePopularityImpact(business.getPopularity());
        business.setPopularity(newPopularity);
        System.out.println("Market demand has influenced your popularity. Your popularity is now " + newPopularity);

        // calculates revenue
        int baseRevenue = business.calculateRevenue();
        int adjustedRevenue = market.calculateRevenueImpact(baseRevenue, business.getPopularity());
        business.addFunds(adjustedRevenue);
        System.out.println("You earned $" + adjustedRevenue + " this week.");
    }

    /**
     * seeks investment for the business based on user input
     * updates the business's funds and popularity
     */
    private void seekInvestment() {
        System.out.println("How much investment are you seeking? (Enter a number between 1000 and 10000):");
        int investmentAmount = validRange(1000, 10000);

        if (investor.isInvesting()) {
            System.out.println("\nYou already have an investor. They are not willing to invest more at this time.");
            return;
        }

        investor.invest(investmentAmount, true);
        business.addFunds(investmentAmount);
        System.out.println("\nYou successfully secured an investment of $" + investmentAmount + "!");

        // risk of investor interference
        int riskFactor = random.nextInt(100);
        if (riskFactor < 40) {
            System.out.println("The investor demands a say in your business decisions, limiting your freedom.");
            int popularityLoss = 10;
            business.setPopularity(business.getPopularity() - popularityLoss);
            System.out.println("Your popularity decreased by " + popularityLoss + ", and your funds decreased by $" + (popularityLoss * 100) + ".");
            business.deductFunds(popularityLoss * 100);
        } else {
            System.out.println("The investment helps stabilize your finances and boosts your business.");
            int popularityGain = 10;
            business.setPopularity(business.getPopularity() + popularityGain);
            System.out.println("Your popularity increased by " + popularityGain + ", and your funds increased by $" + (popularityGain * 100) + ".");
            business.addFunds(popularityGain * 100);
        }

        // competition reaction
        System.out.println("Tech Titans sees your investment as a threat and responds by increasing their marketing budget.");
        System.out.println("Their popularity increases by 15.");
        competition.setPopularity(competition.getPopularity() + 15);

        // update popularity based on demand
        int newPopularity = market.calculatePopularityImpact(business.getPopularity());
        business.setPopularity(newPopularity);
        System.out.println("Market demand has influenced your popularity. Your popularity is now " + newPopularity);

        // calculates revenue
        int baseRevenue = business.calculateRevenue();
        int adjustedRevenue = market.calculateRevenueImpact(baseRevenue, business.getPopularity());
        business.addFunds(adjustedRevenue);
        System.out.println("You earned $" + adjustedRevenue + " this week.");
    }

    /**
     * sells a specified number of units based on user input
     * updates the units sold, popularity, and funds
     */
    private void sellProducts() {
    System.out.println("How many units would you like to sell? (Enter a number between 10 and 100):");
    int unitsSold = validRange(10, 100);

    // risk of overproduction if the player sells too many units
    if (unitsSold > 50) { 
        int riskFactor = random.nextInt(100);
        if (riskFactor < 50) {
            System.out.println("\nYou sold " + unitsSold + " units, but you overproduced!");
            System.out.println("Excess inventory is piling up, and storage costs are increasing.");
            System.out.println("Customers are also noticing the lack of quality control due to rushed production.");
            
            // negative consequences
            int popularityLoss = 10;
            int fundsLoss = 1000;
            business.setPopularity(business.getPopularity() - popularityLoss);
            business.deductFunds(fundsLoss);
            System.out.println("Your popularity decreased by " + popularityLoss + ", and your funds decreased by $" + fundsLoss + ".");
            
            // competition Reaction
            System.out.println("Tech Titans sees your overproduction as an opportunity to steal your customers.");
            System.out.println("They launch a new marketing campaign in response.");
            competition.setPopularity(competition.getPopularity() + 15);
            System.out.println("Tech Titans' popularity increased by 15.");
        } else {
            System.out.println("\nYou sold " + unitsSold + " units, but you barely managed to avoid overproduction.");
            System.out.println("Your team worked overtime to meet demand, but it was a close call.");
        }
    } else {
        System.out.println("\nYou sold " + unitsSold + " units without any issues.");
    }

    // update units sold
    business.updateUnitsSold(unitsSold);
    
    // update popularity based on demand
    int newPopularity = market.calculatePopularityImpact(business.getPopularity());
    business.setPopularity(newPopularity);
    System.out.println("Market demand has influenced your popularity. Your popularity is now " + newPopularity);

    // calculates revenue
    int baseRevenue = business.calculateRevenue();
    int adjustedRevenue = market.calculateRevenueImpact(baseRevenue, business.getPopularity());
    business.addFunds(adjustedRevenue);
    System.out.println("You earned $" + adjustedRevenue + " this week.");
}

    /**
     * runs a marketing campaign that effects popularity
     * 2000 cost and increases popularity if the business has sufficient funds
     */
    private void runCampaign() {
        System.out.println("You decide to run a marketing campaign to boost your popularity.");
        int cost = 2000;
        if (business.getFunds() >= cost) {
            business.deductFunds(cost);
            int popularityGain = 15;
            business.setPopularity(business.getPopularity() + popularityGain);
            System.out.println("Your popularity increased by " + popularityGain + ", and your funds decreased by $" + cost + ".");
        } else {
            System.out.println("You don't have enough funds to run a marketing campaign.");
        }
    }

    /**
     * hires new employees to increase productivity
     * 3000 cost and increases productivity if the business has sufficient funds
     */
    private void hireEmployees() {
        System.out.println("You decide to hire new employees to improve productivity.");
        int cost = 3000;
        if (business.getFunds() >= cost) {
            business.deductFunds(cost);
            int productivityGain = 10;
            business.setProductivity(business.getProductivity() + productivityGain);
            System.out.println("Your productivity increased by " + productivityGain + ", and your funds decreased by $" + cost + ".");
        } else {
            System.out.println("You don't have enough funds to hire new employees.");
        }
    }

    /**
     * displays the status of the business 
     * funds, popularity, competition, popularity, product details, units sold, and current week
     */
    private void checkStatus() {
        System.out.println("\n");
        System.out.println("\n--- Business Status ---");
        System.out.println("Funds: $" + business.getFunds());
        System.out.println("Popularity: " + business.getPopularity() + " (out of 100)");
        System.out.println("Competition Popularity: " + competition.getPopularity() + " (out of 100)");
        System.out.println("Product: " + business.getProducts());
        System.out.println("Product Price: $" + business.getPrice());
        System.out.println("Units Sold: " + business.getUnitsSold());
        System.out.println("Week: " + business.getWeekCount());
    }

    /**
     * updates the game state based on market and competition
     */
    private void updateGameState() {
        int popularityChange = random.nextInt(11) - 5;
        business.setPopularity(business.getPopularity() + popularityChange);
        if (popularityChange > 0) {
            System.out.println("\nMarket trends are favorable! Your popularity increased by " + popularityChange + ", and your funds increased by $" + (popularityChange * 100) + ".");
            business.addFunds(popularityChange * 100);
        } else if (popularityChange < 0) {
            System.out.println("\nMarket trends are unfavorable! Your popularity decreased by " + (-popularityChange) + ", and your funds decreased by $" + (-popularityChange * 100) + ".");
            business.deductFunds(-popularityChange * 100);
        }

        // random competitor actions
        int competitorAction = random.nextInt(3);
        if (competitorAction == 1) {
            competition.raisePrice(5);
            System.out.println("\nYour competition raised their prices!");
        } else if (competitorAction == 2) {
            competition.lowPrice(5);
            System.out.println("\nYour competition lowered their prices!");
        }
    }

    /**
     * triggers a random event
     * can be bad or good
     */
    private void triggerRandomEvent() {
        int eventChance = random.nextInt(100);
        if (eventChance < 30) {
            int eventType = random.nextInt(4);
            switch (eventType) {
                case 0:
                    System.out.println("\nYour competition, Tech Titans, has launched a new product!");
                    System.out.println("Customers are flocking to their business, and your sales drop.");
                    business.setPopularity(business.getPopularity() - 10);
                    competition.setPopularity(competition.getPopularity() + 15);
                    System.out.println("Your popularity decreased by 10, and your funds decreased by $1000.");
                    business.deductFunds(1000);
                    break;
                case 1:
                    System.out.println("\nYour business goes viral on social media!");
                    System.out.println("Customers are excited about your products, and your popularity soars.");
                    business.setPopularity(business.getPopularity() + 20);
                    System.out.println("Your popularity increased by 20, and your funds increased by $2000.");
                    business.addFunds(2000);
                    break;
                case 2:
                    System.out.println("\nA global supply chain disruption has increased your production costs.");
                    System.out.println("Your funds take a hit, and your profit margins shrink.");
                    business.deductFunds(2000);
                    break;
                case 3:
                    System.out.println("\nA prominent tech magazine features your business in their latest issue.");
                    System.out.println("Your popularity increases, and new customers are drawn to your products.");
                    business.setPopularity(business.getPopularity() + 15);
                    System.out.println("Your popularity increased by 15, and your funds increased by $1500.");
                    business.addFunds(1500);
                    break;
            }
        }
    }

    /**
     * prompts the player to enter a valid choice
     * makes sure the inpout is valid by setting a range
     */
    private int validRange(int min, int max) {
        while (true) {
            try {
                int choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
            }
        }
    }
}