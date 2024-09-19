public class Transaction {
    private String projectName;
    private String transactionDate;
    private double transactionPrice;
    private int transactionSqFt;

    // Constructor
    public Transaction(String projectName, String transactionDate, double transactionPrice, int transactionSqFt) {
        this.projectName = projectName;
        this.transactionDate = transactionDate;
        this.transactionPrice = transactionPrice;
        this.transactionSqFt = transactionSqFt;
    }

    // Getters
    public String getProjectName() { return projectName; }
    public String getTransactionDate() { return transactionDate; }
    public double getTransactionPrice() { return transactionPrice; }
    public int getTransactionSqFt() { return transactionSqFt; }

    // Display transaction details
    public void displayTransactionDetails() {
        System.out.println("Transaction Date: " + transactionDate);
        System.out.println("Transaction Price: " + transactionPrice);
        System.out.println("Square Feet: " + transactionSqFt);
        System.out.println("--------------------");
    }
}
