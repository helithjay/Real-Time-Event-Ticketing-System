package com.Mycompany.TicketManagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class ConfigurationDto {

    private Long id;

    private Integer totalTickets;

    private Integer ticketReleaseRateMs;

    private Integer customerRetrievalRateMs;

    private Integer maxTicketCapacity;

    private String configName;

    private boolean isActive;

    public ConfigurationDto() {
    }

    public ConfigurationDto(Long id, Integer totalTickets, Integer ticketReleaseRateMs, Integer customerRetrievalRateMs, Integer maxTicketCapacity, String configName, boolean isActive) {
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

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Integer getTicketReleaseRateMs() {
        return ticketReleaseRateMs;
    }

    public void setTicketReleaseRateMs(Integer ticketReleaseRateMs) {
        this.ticketReleaseRateMs = ticketReleaseRateMs;
    }

    public Integer getCustomerRetrievalRateMs() {
        return customerRetrievalRateMs;
    }

    public void setCustomerRetrievalRateMs(Integer customerRetrievalRateMs) {
        this.customerRetrievalRateMs = customerRetrievalRateMs;
    }

    public Integer getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(Integer maxTicketCapacity) {
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
