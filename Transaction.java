class Transaction {
    private int id;
    private double amount;
    private String date;
    private String description;
    private String category;
    private static int nextId = 1;

    public Transaction(double amount, String date, String description, String category) {
        this.id = nextId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        nextId++;
    }

    public Transaction(int id, double amount, String date, String description, String category) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String toString() {
        return "ID: " + id + ", Amount: " + amount + ", Date: " + date + ", Description: " + description + ", Category: " + category;
    }
}