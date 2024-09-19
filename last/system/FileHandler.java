
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String FILE_PATH = "properties.csv";
    private static final String TRANSACTION_FILE_PATH = "transactions.txt";

    // Read properties from a file
    public List<Property> readProperties() {
        List<Property> properties = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] details = line.split(","); // Assuming the data is comma-separated
    
                // Ensure correct number of details (9 fields expected)
                if (details.length < 9) {
                    System.out.println("Skipping improperly formatted line: " + line);
                    continue; // Skip this line
                }
    
                try {
                    int sizeSqM = Integer.parseInt(details[0].trim());
                    int sqFt = Integer.parseInt(details[1].trim());
                    String propertyType = details[2].trim();
                    int noOfFloors = Integer.parseInt(details[3].trim());
                    String address = details[4].trim();
                    String scheme = details[5].trim();
                    double price = Double.parseDouble(details[6].trim());
                    int year = Integer.parseInt(details[7].trim());
                    double pricePerSqft = Double.parseDouble(details[8].trim());
    
                    Property property = new Property(sizeSqM, sqFt, propertyType, noOfFloors, address, scheme, price, year, pricePerSqft);
                    properties.add(property);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing line: " + line + ". Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return properties;
    }
    
    // Write a property to a file
    public void writeProperty(Property property) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String line
                    = property.getSizeSqM() + "\t" + property.getSqFt() + "\t"
                    + property.getPropertyType() + "\t"
                    + property.getNoOfFloors() + "\t"
                    + property.getAddress() + "\t"
                    + property.getScheme() + "\t"
                    + property.getPrice() + "\t"
                    + property.getYear() + "\t"
                    + property.getPricePerSqft();
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Initialize the file with a header if not present
    public void initializeFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                // Writing header
                bw.write("DateOfValuation\tSizeSqM\tSqFt\tPropertyType\tNoOfFloors\tAddress\tScheme\tPrice\tYear\tPricePerSqft");
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Transaction> readTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(TRANSACTION_FILE_PATH))) {
            String line;
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] details = line.split("\t");  // Assuming the data is tab-separated

                // Check if the line has the required number of columns (11 fields expected)
                if (details.length < 11) {

                    continue;
                }

                String transactionDate = details[0].trim();  // DateOfValuation
                double transactionPrice = Double.parseDouble(details[1].trim());  // PRICE
                int transactionSqFt = Integer.parseInt(details[2].trim());  // SqFt
                String projectName = details[3].trim();  // SCHEME

                // Read the rest of the necessary details from the details array
                int sizeSqM = Integer.parseInt(details[4].trim());
                String propertyType = details[5].trim();
                int noOfFloors = Integer.parseInt(details[6].trim());
                String address = details[7].trim();
                String scheme = details[8].trim();
                int year = Integer.parseInt(details[9].trim());
                double pricePerSqft = Double.parseDouble(details[10].trim());

                // Create a new Transaction object
                Transaction transaction = new Transaction(
                        projectName,
                        transactionDate,
                        transactionPrice,
                        transactionSqFt,
                        sizeSqM,
                        propertyType,
                        noOfFloors,
                        address,
                        scheme,
                        year,
                        pricePerSqft
                );
                transactions.add(transaction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number in file: " + e.getMessage());
        }

        return transactions;
    }

    public void updatePropertiesFile(List<Property> properties) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Write the header first
            bw.write("DateOfValuation\tSizeSqM\tSqFt\tPropertyType\tNoOfFloors\tAddress\tScheme\tPrice\tYear\tPricePerSqft");
            bw.newLine();

            // Write each property
            for (Property property : properties) {
                String line
                        = property.getSizeSqM() + "\t" + property.getSqFt() + "\t"
                        + property.getPropertyType() + "\t"
                        + property.getNoOfFloors() + "\t"
                        + property.getAddress() + "\t"
                        + property.getScheme() + "\t"
                        + property.getPrice() + "\t"
                        + property.getYear() + "\t"
                        + property.getPricePerSqft();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFilePath() {
        return FILE_PATH;
    }

    public void writeTransaction(Transaction transaction) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSACTION_FILE_PATH, true))) {
            String line = transaction.getTransactionDate() + "\t"
                    + transaction.getSizeSqM() + "\t"
                    + transaction.getTransactionSqFt() + "\t"
                    + transaction.getPropertyType() + "\t"
                    + transaction.getNoOfFloors() + "\t"
                    + transaction.getAddress() + "\t"
                    + transaction.getScheme() + "\t"
                    + transaction.getTransactionPrice() + "\t"
                    + transaction.getYear() + "\t"
                    + transaction.getProjectName() + "\t"
                    + transaction.getPricePerSqft();
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
