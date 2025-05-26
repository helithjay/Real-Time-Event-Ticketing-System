package com.Mycompany.TicketManagement.entity;



import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "configurations")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_tickets")
    private int totalTickets;

    @Column(name = "ticket_release_rate_ms")
    private int ticketReleaseRateMs;

    @Column(name = "customer_retrieval_rate_ms")
    private int customerRetrievalRateMs;

    @Column(name = "max_ticket_capacity")
    private int maxTicketCapacity;

    @Column(name = "config_name")
    private String configName;

    @Column(name = "is_active")
    private boolean isActive = false;

    public Configuration() {
    }

    public Configuration(Long id, int totalTickets, int ticketReleaseRateMs, int customerRetrievalRateMs, int maxTicketCapacity, String configName, boolean isActive) {
        this.id = id;
        this.totalTickets = totalTickets;
        this.ticketReleaseRateMs = ticketReleaseRateMs;
        this.customerRetrievalRateMs = customerRetrievalRateMs;
        this.maxTicketCapacity = maxTicketCapacity;
        this.configName = configName;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}