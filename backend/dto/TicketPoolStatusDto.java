package com.Mycompany.TicketManagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class TicketPoolStatusDto {

    private int availableTickets;
    private int totalTickets;
    private int purchasedTickets;
    private int maxCapacity;
    private boolean isSystemRunning;

    public TicketPoolStatusDto() {
    }

    public TicketPoolStatusDto(int availableTickets, int totalTickets, int purchasedTickets, int maxCapacity, boolean isSystemRunning) {
        this.availableTickets = availableTickets;
        this.totalTickets = totalTickets;
        this.purchasedTickets = purchasedTickets;
        this.maxCapacity = maxCapacity;
        this.isSystemRunning = isSystemRunning;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public int getPurchasedTickets() {
        return purchasedTickets;
    }

    public void setPurchasedTickets(int purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean isSystemRunning() {
        return isSystemRunning;
    }

    public void setSystemRunning(boolean systemRunning) {
        isSystemRunning = systemRunning;
    }
}
