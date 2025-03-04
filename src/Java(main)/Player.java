public class Player {
    private boolean bankrupt = false;
    private String name;
    private int money;
    private int charisma;

    public Player(String name, int money, int charisma) {
        this.name = name;
        this.money = money;
        this.charisma = charisma;
    }

    public void buy(int money) {
        this.money -= money;
        if (this.money < 0) {
            bankrupt = true;
        }
    }

    public void sell(int money) {
        this.money += money;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public void ask(int charisma) {
        System.out.println("You used your charisma to negotiate a better deal!");
    }

    public int getMoney() {
        return money;
    }

    public int getCharisma() {
        return charisma;
    }

    public String getName() {
        return name;
    }
}