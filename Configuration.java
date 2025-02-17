package com.ticketingsystem;

import java.io.*;
import java.util.Properties;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRateMs;
    private int customerRetrievalRateMs;
    private int maxTicketCapacity;

    // Getters and Setters
    public int getTotalTickets() {
        return totalTickets;
    }
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRateMs() {
        return ticketReleaseRateMs;
    }
    public void setTicketReleaseRateMs(int ticketReleaseRateMs) {
        this.ticketReleaseRateMs = ticketReleaseRateMs;
    }

    public int getCustomerRetrievalRateMs() {
        return customerRetrievalRateMs;
    }
    public void setCustomerRetrievalRateMs(int customerRetrievalRateMs) {
        this.customerRetrievalRateMs = customerRetrievalRateMs;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }
}
