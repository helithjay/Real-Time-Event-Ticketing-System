package com.ticketingsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TicketingGUI extends Application {

    // Fields for managing ticket operations and threads
    private TicketPool ticketPool;
    private Thread vendorThread;
    private Thread customerThread;

    @Override
    public void start(Stage primaryStage) {
        // Root layout with padding and spacing
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(10));

        // Section for configuration inputs
        Label configLabel = new Label("Configuration:");

        // Input fields for configuration values
        TextField totalTicketsField = new TextField();
        totalTicketsField.setPromptText("Total Tickets");

        TextField releaseRateField = new TextField();
        releaseRateField.setPromptText("Release Rate (ms)");

        TextField retrievalRateField = new TextField();
        retrievalRateField.setPromptText("Retrieval Rate (ms)");

        TextField maxCapacityField = new TextField();
        maxCapacityField.setPromptText("Max Ticket Capacity");

        // Button to save configuration
        Button saveConfigButton = new Button("Save Configuration");

        // Section for displaying status
        Label statusLabel = new Label("Status:");
        TextArea statusArea = new TextArea();
        statusArea.setEditable(false); // Prevent user editing

        // Buttons to start and stop the simulation
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        stopButton.setDisable(true); // Initially disabled

        // Adding all components to the layout
        root.getChildren().addAll(
                configLabel,
                totalTicketsField,
                releaseRateField,
                retrievalRateField,
                maxCapacityField,
                saveConfigButton,
                statusLabel,
                statusArea,
                startButton,
                stopButton
        );

        // Action for saving the configuration
        saveConfigButton.setOnAction(e -> {
            try {
                // Reading and validating input values
                int totalTickets = Integer.parseInt(totalTicketsField.getText());
                int releaseRate = Integer.parseInt(releaseRateField.getText());
                int retrievalRate = Integer.parseInt(retrievalRateField.getText());
                int maxCapacity = Integer.parseInt(maxCapacityField.getText());

                // Creating a new Configuration object
                Configuration config = new Configuration();
                config.setTotalTickets(totalTickets);
                config.setTicketReleaseRateMs(releaseRate);
                config.setCustomerRetrievalRateMs(retrievalRate);
                config.setMaxTicketCapacity(maxCapacity);

                // Saving configuration to file
                ConfigurationManager.saveConfiguration(config, "config.txt");
                statusArea.appendText("Configuration saved successfully!\n");

            } catch (NumberFormatException ex) {
                // Handling invalid input
                statusArea.appendText("Invalid input. Please enter numeric values.\n");
            }
        });

        // Action for starting the simulation
        startButton.setOnAction(e -> {
            // Load the configuration from file
            Configuration config = ConfigurationManager.loadConfiguration("config.txt");

            if (config == null) {
                statusArea.appendText("Failed to load configuration.\n");
                return;
            }

            // Initialize ticket pool and threads
            ticketPool = new TicketPool(config.getTotalTickets(), config.getMaxTicketCapacity());
            vendorThread = new Thread(new Vendor(ticketPool, config.getTicketReleaseRateMs()));
            customerThread = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRateMs()));

            // Start the threads
            vendorThread.start();
            customerThread.start();

            // Update status and button states
            statusArea.appendText("Simulation started.\n");
            startButton.setDisable(true);
            stopButton.setDisable(false);
        });

        // Action for stopping the simulation
        stopButton.setOnAction(e -> {
            if (vendorThread != null && customerThread != null) {
                // Interrupt the threads
                vendorThread.interrupt();
                customerThread.interrupt();

                // Update status and button states
                statusArea.appendText("Simulation stopped.\n");
                startButton.setDisable(false);
                stopButton.setDisable(true);
            }
        });

        // Setting up the scene and stage
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Ticketing System GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
