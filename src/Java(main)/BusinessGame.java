import java.util.Random;
import java.util.Scanner;

public class BusinessGame {
    private Player player;
    private Business business;
    private Competition competition;
    private Investor investor;
    private Customer customer;
    private GameFunctions gameFunctions;
    private Random random;

    public static void main(String[] args) {
        BusinessGame game = new BusinessGame();
        game.start();
    }

    public void start() {
        random = new Random();
        Scanner scanner = new Scanner(System.in);

        // Initialize game objects
        player = new Player("Tech Entrepreneur", 10000, 50); 
        business = new Business("Tech Innovators", 5000, 30, "Smart Devices"); 
        competition = new Competition(40, 100); 
        investor = new Investor(20000, false); 
        customer = new Customer(5000, false);
        gameFunctions = new GameFunctions(50); 

        // Game introduction
        System.out.println("Welcome to the Business Game!");
        System.out.println("You are the owner of a startup tech business, and you have the responsibility to make the correct decisions to lead your business to success!");
        System.out.println("\n--- Your Business Situation ---");
        System.out.println("Your business, " + business.getName() + ", is located in the heart of Silicon Valley.");
        System.out.println("You specialize in " + business.getProducts() + ", but the market is highly competitive.");
        System.out.println("Your competition, 'Tech Titans', is a well-established company with a strong foothold in the industry.");
        System.out.println("\n--- Current Challenges ---");
        System.out.println("1. **Rising Rent Costs**: Due to the booming tech industry, the rent for your office space has increased by 20% this year.");
        System.out.println("   This has put a strain on your finances, leaving you with only $" + business.getFunds() + " in the bank.");
        System.out.println("2. **Declining Customer Base**: Customers are increasingly drawn to your competition's flashy marketing campaigns.");
        System.out.println("   Your current popularity is " + business.getPopularity() + " (out of 100), while Tech Titans' popularity is " + competition.getPopularity() + ".");
        System.out.println("3. **Supply Chain Issues**: A recent global chip shortage has driven up the cost of producing your devices.");
        System.out.println("   Your product price is currently $" + business.getPrice() + ", but your profit margins are shrinking.");
        System.out.println("\nThe road ahead is challenging, but with the right decisions, you can overcome these obstacles and grow your business!");

        // Game loop
        while (!player.isBankrupt()) {
            System.out.println("\n--- What would you like to do? ---");
            System.out.println("1. Raise product price");
            System.out.println("2. Lower product price");
            System.out.println("3. Launch a new product");
            System.out.println("4. Seek investment");
            System.out.println("5. Sell products");
            System.out.println("6. Check status");
            System.out.println("7. End game");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("By how much would you like to raise the price? (Enter a number between 5 and 50):");
                    int raiseAmount = scanner.nextInt();
                    raiseProductPrice(raiseAmount);
                    break;
                case 2:
                    System.out.println("By how much would you like to lower the price? (Enter a number between 5 and 50):");
                    int lowerAmount = scanner.nextInt();
                    lowerProductPrice(lowerAmount);
                    break;
                case 3:
                    System.out.println("What would you like to name your new product?");
                    scanner.nextLine(); // Consume newline
                    String newProduct = scanner.nextLine();
                    launchNewProduct(newProduct);
                    break;
                case 4:
                    System.out.println("How much investment are you seeking? (Enter a number between 1000 and 10000):");
                    int investmentAmount = scanner.nextInt();
                    seekInvestment(investmentAmount);
                    break;
                case 5:
                    System.out.println("How many units would you like to sell? (Enter a number between 10 and 100):");
                    int unitsSold = scanner.nextInt();
                    sellProducts(unitsSold);
                    break;
                case 6:
                    checkStatus();
                    break;
                case 7:
                    System.out.println("You chose to end the game. Thanks for playing!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }

            // Update game state
            updateGameState();

            // Trigger random event
            triggerRandomEvent();

            // Show scores after each choice
            checkStatus();
        }

        // End game if bankrupt
        System.out.println("\nGame Over! Your business went bankrupt. Better luck next time!");
    }

    private void raiseProductPrice(int amount) {
        if (amount < 5 || amount > 50) {
            System.out.println("Invalid amount. Please choose a value between 5 and 50.");
            return;
        }

        business.raisePrice(amount);
        System.out.println("\nYou raised the price of your products by $" + amount + ".");

        // Risk of customer decline
        int riskFactor = random.nextInt(100); 
        if (riskFactor < amount) {
            System.out.println("Customers are outraged by the steep price increase! Many are switching to your competition.");
            System.out.println("Your popularity takes a massive hit, and sales plummet.");
            business.setPopularity(business.getPopularity() - 20);
            competition.setPopularity(competition.getPopularity() + 10);
        } else {
            System.out.println("Customers are slightly annoyed but continue to buy your products.");
            System.out.println("Your popularity drops slightly, but loyal customers stick around.");
            business.setPopularity(business.getPopularity() - 5);
        }

        // Revenue calculation
        int revenue = 1000 + (amount * 10); // Revenue increases based on the price raise
        business.buy("Smart Devices", -revenue); // Add revenue to funds
        System.out.println("Revenue: $" + revenue);
    }

    private void lowerProductPrice(int amount) {
        if (amount < 5 || amount > 50) {
            System.out.println("Invalid amount. Please choose a value between 5 and 50.");
            return;
        }

        business.lowPrice(amount);
        System.out.println("\nYou lowered the price of your products by $" + amount + ".");

        // Risk of reduced profit margins
        int riskFactor = random.nextInt(100); // Random risk factor (0-99)
        if (riskFactor < 30) { // 30% chance of negative outcome
            System.out.println("The price reduction has severely impacted your profit margins.");
            System.out.println("You struggle to cover production costs, and your funds take a hit.");
            business.buy("Smart Devices", 1000); // Simulate increased costs
        } else {
            System.out.println("Customers appreciate the lower prices and continue to support your business.");
            System.out.println("Your popularity increases slightly as customers feel they're getting a good deal.");
            business.setPopularity(business.getPopularity() + 5);
        }

        // Revenue calculation
        int revenue = 2000 + (amount * 20); // Revenue increases based on the price reduction
        business.buy("Smart Devices", -revenue); // Add revenue to funds
        System.out.println("Revenue: $" + revenue);
    }

    private void launchNewProduct(String newProduct) {
        business.newProduct(newProduct);
        System.out.println("\nYou launched a new product: " + newProduct + "!");

        // Risk of product failure
        int riskFactor = random.nextInt(100); // Random risk factor (0-99)
        if (riskFactor < 20) { // 20% chance of failure
            System.out.println("The new product launch was a disaster! Customers complain about quality issues.");
            System.out.println("Your popularity takes a hit, and sales drop significantly.");
            business.setPopularity(business.getPopularity() - 15);
        } else {
            System.out.println("Customers are excited about your new product! Social media buzzes with positive reviews.");
            System.out.println("Your popularity increases by 20.");
            business.setPopularity(business.getPopularity() + 20);
        }
    }

    private void seekInvestment(int amount) {
        if (amount < 1000 || amount > 10000) {
            System.out.println("Invalid amount. Please choose a value between 1000 and 10000.");
            return;
        }

        if (investor.isInvesting()) {
            System.out.println("\nYou already have an investor. They are not willing to invest more at this time.");
            return;
        }

        investor.invest(amount, true);
        business.buy("Smart Devices", -amount); // Add investment to funds
        System.out.println("\nYou successfully secured an investment of $" + amount + "!");

        // Risk of investor interference
        int riskFactor = random.nextInt(100); // Random risk factor (0-99)
        if (riskFactor < 40) { // 40% chance of negative outcome
            System.out.println("The investor demands a say in your business decisions, limiting your freedom.");
            System.out.println("Your popularity drops as customers sense instability.");
            business.setPopularity(business.getPopularity() - 10);
        } else {
            System.out.println("The investment helps stabilize your finances and boosts your business.");
            System.out.println("Your popularity increases by 10.");
            business.setPopularity(business.getPopularity() + 10);
        }
    }

    private void sellProducts(int unitsSold) {
        if (unitsSold < 10 || unitsSold > 100) {
            System.out.println("Invalid amount. Please choose a value between 10 and 100.");
            return;
        }

        int revenue = unitsSold * business.getPrice(); // Revenue = units sold * price per unit
        business.buy("Smart Devices", -revenue); // Add revenue to funds
        System.out.println("\nYou sold " + unitsSold + " units of your product, earning $" + revenue + "!");

        // Risk of overproduction
        int riskFactor = random.nextInt(100); // Random risk factor (0-99)
        if (riskFactor < 10) { // 10% chance of negative outcome
            System.out.println("You overproduced and now have excess inventory. Storage costs are eating into your profits.");
            business.buy("Smart Devices", 500); // Simulate storage costs
        } else {
            System.out.println("Sales are steady, and your business is thriving.");
        }
    }

    private void checkStatus() {
        System.out.println("\n--- Business Status ---");
        System.out.println("Funds: $" + business.getFunds());
        System.out.println("Popularity: " + business.getPopularity() + " (out of 100)");
        System.out.println("Competition Popularity: " + competition.getPopularity() + " (out of 100)");
        System.out.println("Product: " + business.getProducts());
        System.out.println("Product Price: $" + business.getPrice());
    }

    private void updateGameState() {
        // Random market trends
        gameFunctions.mTrends(business.getPopularity());
        business.setPopularity(gameFunctions.getPopularity());

        // Random competitor actions
        int competitorAction = random.nextInt(3); // 0: no action, 1: raise price, 2: lower price
        if (competitorAction == 1) {
            competition.raisePrice(5);
            System.out.println("\nYour competition raised their prices!");
        } else if (competitorAction == 2) {
            competition.lowPrice(5);
            System.out.println("\nYour competition lowered their prices!");
        }

        // Check for bankruptcy
        if (business.getFunds() < 0) {
            player.buy(0); // Trigger bankruptcy
        }
    }

    private void triggerRandomEvent() {
        int eventChance = random.nextInt(100); // Random chance of an event (0-99)
        if (eventChance < 30) { // 30% chance of a random event
            int eventType = random.nextInt(4); // 0-3: different types of events
            switch (eventType) {
                case 0:
                    System.out.println("\n--- Random Event: Competitor Launches New Product ---");
                    System.out.println("Your competition, Tech Titans, has launched a new product!");
                    System.out.println("Customers are flocking to their business, and your sales drop.");
                    business.setPopularity(business.getPopularity() - 10);
                    competition.setPopularity(competition.getPopularity() + 15);
                    break;
                case 1:
                    System.out.println("\n--- Random Event: Viral Marketing Campaign ---");
                    System.out.println("Your business goes viral on social media!");
                    System.out.println("Customers are excited about your products, and your popularity soars.");
                    business.setPopularity(business.getPopularity() + 20);
                    break;
                case 2:
                    System.out.println("\n--- Random Event: Supply Chain Disruption ---");
                    System.out.println("A global supply chain disruption has increased your production costs.");
                    System.out.println("Your funds take a hit, and your profit margins shrink.");
                    business.buy("Smart Devices", 2000); // Simulate increased costs
                    break;
                case 3:
                    System.out.println("\n--- Random Event: Positive Media Coverage ---");
                    System.out.println("A prominent tech magazine features your business in their latest issue.");
                    System.out.println("Your popularity increases, and new customers are drawn to your products.");
                    business.setPopularity(business.getPopularity() + 15);
                    break;
            }
        }
    }
}