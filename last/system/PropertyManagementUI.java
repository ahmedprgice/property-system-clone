import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


public class PropertyManagementUI extends JFrame {
    private FileHandler fileHandler;
    private Project project;

    public PropertyManagementUI() {
        fileHandler = new FileHandler();
        fileHandler.initializeFile();
        project = new Project("Desa Residence");

        // Load properties
        List<Property> storedProperties = fileHandler.readProperties();
        for (Property property : storedProperties) {
            project.addProperty(property);
        }

        setTitle("Property Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create UI components
        JButton searchButton = new JButton("Search Properties");
        JButton addButton = new JButton("Add Property");
        JButton displayButton = new JButton("Display Properties");
        JButton transactionsButton = new JButton("Show Last 5 Transactions");
        JButton exitButton = new JButton("Exit");

        // Add action listeners
        searchButton.addActionListener(e -> searchProperties());
        addButton.addActionListener(e -> addNewProperty());
        displayButton.addActionListener(e -> displayProperties());
        transactionsButton.addActionListener(e -> showLast5Transactions());
        exitButton.addActionListener(e -> System.exit(0));

        // Add components to the frame
        add(searchButton);
        add(addButton);
        add(displayButton);
        add(transactionsButton);
        add(exitButton);
    }

    private void searchProperties() {
        JTextField minSqFtField = new JTextField(5);
        JTextField maxSqFtField = new JTextField(5);
        JTextField minPriceField = new JTextField(5);
        JTextField maxPriceField = new JTextField(5);
        JTextField typeField = new JTextField(10);
        JTextField projectField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Min Sq Ft:"));
        panel.add(minSqFtField);
        panel.add(new JLabel("Max Sq Ft:"));
        panel.add(maxSqFtField);
        panel.add(new JLabel("Min Price:"));
        panel.add(minPriceField);
        panel.add(new JLabel("Max Price:"));
        panel.add(maxPriceField);
        panel.add(new JLabel("Type:"));
        panel.add(typeField);
        panel.add(new JLabel("Project Name:"));
        panel.add(projectField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Search Properties", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int minSqFt = Integer.parseInt(minSqFtField.getText());
            int maxSqFt = Integer.parseInt(maxSqFtField.getText());
            double minPrice = Double.parseDouble(minPriceField.getText());
            double maxPrice = Double.parseDouble(maxPriceField.getText());
            String type = typeField.getText();
            String projectName = projectField.getText();

            List<Property> results = project.searchProperties(minSqFt, maxSqFt, minPrice, maxPrice, type, projectName);
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No properties found.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("Matching Properties:\n");
                for (Property property : results) {
                  System.out.println(property.getDetails()); // Use getDetails() instead
            System.out.println("-------------------");
                }
                JOptionPane.showMessageDialog(this, sb.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void addNewProperty() {
        JTextField sizeSqMField = new JTextField(5);
        JTextField sqFtField = new JTextField(5);
        JTextField typeField = new JTextField(10);
        JTextField noOfFloorsField = new JTextField(5);
        JTextField addressField = new JTextField(20);
        JTextField schemeField = new JTextField(20);
        JTextField priceField = new JTextField(10);
        JTextField yearField = new JTextField(5);
        JTextField pricePerSqftField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));
        panel.add(new JLabel("Size SqM:"));
        panel.add(sizeSqMField);
        panel.add(new JLabel("Size Sq Ft:"));
        panel.add(sqFtField);
        panel.add(new JLabel("Property Type:"));
        panel.add(typeField);
        panel.add(new JLabel("No of Floors:"));
        panel.add(noOfFloorsField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Scheme:"));
        panel.add(schemeField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Price/Sq Ft:"));
        panel.add(pricePerSqftField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Property", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int sizeSqM = Integer.parseInt(sizeSqMField.getText());
            int sqFt = Integer.parseInt(sqFtField.getText());
            String propertyType = typeField.getText();
            int noOfFloors = Integer.parseInt(noOfFloorsField.getText());
            String address = addressField.getText();
            String scheme = schemeField.getText();
            double price = Double.parseDouble(priceField.getText());
            int year = Integer.parseInt(yearField.getText());
            double pricePerSqFt = Double.parseDouble(pricePerSqftField.getText());

            Property newProperty = new Property(sizeSqM, sqFt, propertyType, noOfFloors, address, scheme, price, year, pricePerSqFt);
            project.addProperty(newProperty);
            fileHandler.writeProperty(newProperty);
            JOptionPane.showMessageDialog(this, "Property added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void displayProperties() {
        StringBuilder sb = new StringBuilder("Properties:\n");
        for (Property property : project.getProperties()) {
            sb.append(property.displayPropertyDetails()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Properties", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showLast5Transactions() {
        String projectName = JOptionPane.showInputDialog(this, "Enter project name to show last 5 transactions:");
        if (projectName != null && !projectName.trim().isEmpty()) {
            List<Transaction> allTransactions = fileHandler.readTransactions();
            List<Transaction> projectTransactions = new ArrayList<>();
            for (Transaction transaction : allTransactions) {
                if (transaction.getProjectName().equalsIgnoreCase(projectName.trim())) {
                    projectTransactions.add(transaction);
                }
            }

            StringBuilder sb = new StringBuilder("Last 5 Transactions for " + projectName + ":\n");
            int count = Math.min(5, projectTransactions.size());
            for (int i = projectTransactions.size() - count; i < projectTransactions.size(); i++) {
                sb.append(projectTransactions.get(i).getTransactionDate()).append(", ")
                  .append(projectTransactions.get(i).getTransactionPrice()).append(", ")
                  .append(projectTransactions.get(i).getTransactionSqFt()).append(" sq ft\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Transactions", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PropertyManagementUI ui = new PropertyManagementUI();
            ui.setVisible(true);
        });
    }
}
