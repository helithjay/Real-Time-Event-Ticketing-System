package com.ticketingsystem;

import java.io.*;
import java.util.Properties;

public class ConfigurationManager {

    // Method to load configuration from a text file
    public static Configuration loadConfiguration(String filePath) {
        Configuration config = new Configuration();
        Properties properties = new Properties();

        try (FileReader reader = new FileReader(filePath)) {
            properties.load(reader);

            // Parse and validate properties
            config.setTotalTickets(Integer.parseInt(properties.getProperty("totalTickets", "0")));
            config.setTicketReleaseRateMs(Integer.parseInt(properties.getProperty("ticketReleaseRateMs", "1000"))); // Default: 1000 ms
            config.setCustomerRetrievalRateMs(Integer.parseInt(properties.getProperty("customerRetrievalRateMs", "1000"))); // Default: 1000 ms
            config.setMaxTicketCapacity(Integer.parseInt(properties.getProperty("maxTicketCapacity", "100"))); // Default: 100 tickets

        } catch (FileNotFoundException e) {
            System.out.println("Configuration file couldn't find: " + filePath+ "?");
        } catch (IOException e) {
            System.out.println("Error reading configuration file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid configuration format. Please check the file for errors.");
        }
        return config;
    }

    // Method to save configuration to a text file
    public static void saveConfiguration(Configuration config, String filePath) {
        Properties properties = new Properties();

        // Set configuration properties
        properties.setProperty("totalTickets", String.valueOf(config.getTotalTickets()));
        properties.setProperty("ticketReleaseRateMs", String.valueOf(config.getTicketReleaseRateMs()));
        properties.setProperty("customerRetrievalRateMs", String.valueOf(config.getCustomerRetrievalRateMs()));
        properties.setProperty("maxTicketCapacity", String.valueOf(config.getMaxTicketCapacity()));

        try (FileWriter writer = new FileWriter(filePath)) {
            properties.store(writer, "Ticket System Configuration");
            System.out.println("Configuration saved to: " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }
}
