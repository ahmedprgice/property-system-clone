import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        fileHandler.initializeFile();

        // Read existing properties from the file
        Project projectA = new Project("Desa Residence");
        List<Property> storedProperties = fileHandler.readProperties();
        for (Property property : storedProperties) {
            projectA.addProperty(property);
        }

        Scanner scanner = new Scanner(System.in);
        boolean continueProgram = true;

        while (continueProgram) {
            System.out.println("Choose an option:");
            System.out.println("1. Search for properties");
            System.out.println("2. Add a new property");
            System.out.println("3. Display all properties");
            System.out.println("4. Show last 5 transactions for a project");
            System.out.println("5. Exit");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (option) {
                case 1:
                    searchProperties(scanner, projectA);
                    break;
                case 2:
                    addNewProperty(scanner, projectA, fileHandler);
                    break;
                case 3:
                    projectA.displayProperties();
                    break;
                case 4:
                    showLast5Transactions(scanner, fileHandler);
                    break;
                case 5:
                    continueProgram = false;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        scanner.close();
    }

    // Method to search for properties
    private static void searchProperties(Scanner scanner, Project project) {
        System.out.println("Enter minimum square footage:");
        int minSqFt = scanner.nextInt();
        System.out.println("Enter maximum square footage:");
        int maxSqFt = scanner.nextInt();
        System.out.println("Enter minimum price:");
        double minPrice = scanner.nextDouble();
        System.out.println("Enter maximum price:");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine(); // consume the leftover newline
        System.out.println("Property type:");
        String facilities = scanner.nextLine();
        System.out.println("Enter project name:");
        String projectName = scanner.nextLine();

        List<Property> results = project.searchProperties(minSqFt, maxSqFt, minPrice, maxPrice, facilities, projectName);

        if (results.isEmpty()) {
            System.out.println("No properties found that match your criteria.");
        } else {
            System.out.println("Matching properties:");
            for (Property prop : results) {
                prop.displayPropertyDetails();
                System.out.println("-------------------");
            }
        }
    }

    // Method to add a new property
    private static void addNewProperty(Scanner scanner, Project project, FileHandler fileHandler) {
        System.out.println("Enter property size in square meters:");
        int sizeSqM = scanner.nextInt();
        System.out.println("Enter property size in square feet:");
        int sqFt = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.println("Enter property type (e.g., Condominium, Apartment):");
        String propertyType = scanner.nextLine();

        System.out.println("Enter the number of floors:");
        int noOfFloors = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.println("Enter property address:");
        String address = scanner.nextLine();

        System.out.println("Enter property scheme/project name:");
        String scheme = scanner.nextLine();

        System.out.println("Enter property price:");
        double price = scanner.nextDouble();

        System.out.println("Enter year of construction:");
        int year = scanner.nextInt();

        System.out.println("Enter price per square foot:");
        double pricePerSqft = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline

        // Create a new Property object
        Property newProperty = new Property(sizeSqM, sqFt, propertyType, noOfFloors, address, scheme, price, year, pricePerSqft);

        // Add the new property to the project
        project.addProperty(newProperty);

        // Save the new property to the file
        fileHandler.writeProperty(newProperty);

        System.out.println("Property added successfully and saved to the file!");
    }

    // Method to show the last 5 transactions for a selected project
    private static void showLast5Transactions(Scanner scanner, FileHandler fileHandler) {
        System.out.println("Enter the project name to show the last 5 transactions:");
        String projectName = scanner.nextLine().trim();  // Ensure trimming any extra spaces
    
        List<Transaction> allTransactions = fileHandler.readTransactions();
    
        System.out.println("Looking for transactions for project named: '" + projectName + "'");
    
        // Filter transactions by project name
        List<Transaction> projectTransactions = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            System.out.println("Transaction Project Name: '" + transaction.getProjectName().trim() + "'");
            if (transaction.getProjectName().trim().equalsIgnoreCase(projectName)) {
                projectTransactions.add(transaction);
            }
        }
    
        int transactionCount = projectTransactions.size();
        if (transactionCount == 0) {
            System.out.println("No transactions found for the project: " + projectName);
        } else {
            System.out.println("Last 5 transactions for project: " + projectName);
            // Show the last 5 transactions (or fewer if there are less than 5)
            for (int i = Math.max(transactionCount - 5, 0); i < transactionCount; i++) {
                projectTransactions.get(i).displayTransactionDetails();
            }
        }
    }
    }
